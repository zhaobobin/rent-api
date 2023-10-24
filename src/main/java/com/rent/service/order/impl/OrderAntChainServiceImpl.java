package com.rent.service.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.rent.common.dto.components.dto.*;
import com.rent.common.dto.product.OrderProductDetailDto;
import com.rent.common.dto.product.ProductShopCateReqDto;
import com.rent.common.dto.user.UserThirdInfoDto;
import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.product.AntChainProductClassEnum;
import com.rent.config.outside.OutsideConfig;
import com.rent.config.outside.PlatformChannelDto;
import com.rent.dao.order.*;
import com.rent.dao.product.PlatformExpressDao;
import com.rent.dao.product.ProductSkusDao;
import com.rent.dao.user.UserCertificationDao;
import com.rent.dao.user.UserThirdInfoDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.*;
import com.rent.model.product.PlatformExpress;
import com.rent.model.product.ProductSkus;
import com.rent.model.user.UserCertification;
import com.rent.service.components.AntChainInsuranceService;
import com.rent.service.components.AntChainService;
import com.rent.service.components.AntChainShieldScoreService;
import com.rent.service.order.OrderAntChainService;
import com.rent.service.order.UserOrdersQueryService;
import com.rent.service.product.PlatformChannelService;
import com.rent.service.product.ProductService;
import com.rent.service.user.DistrictService;
import com.rent.util.DateUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderAntChainServiceImpl implements OrderAntChainService {

    private static final BigDecimal tenThousand = new BigDecimal(10000);
    private static final BigDecimal chargePercent = new BigDecimal(0.006);

    private final UserOrdersDao userOrdersDao;
    private final OrderLocationAddressDao orderLocationAddressDao;
    private final AntChainStepDao antChainStepDao;
    private final OrderAddressDao orderAddressDao;
    private final AntChainShieldScoreService antChainShieldScoreApiService;
    private final UserThirdInfoDao userThirdInfoDao;
    private final UserCertificationDao userCertificationDao;
    private final DistrictService districtService;
    private final OrderByStagesDao orderByStagesDao;
    private final OrderContractDao orderContractDao;
    private final ProductService productService;
    private final AntChainService antChainService;
    private final PlatformChannelService platformChannelService;
    private final PlatformExpressDao platformExpressDao;
    private final UserOrderCashesDao userOrderCashesDao;
    private final UserOrdersQueryService userOrdersQueryService;
    private final AntChainInsuranceService antChainInsuranceService;
    private final UserOrderItemsDao userOrderItemsDao;
    private final ProductSkusDao productSkusDao;

    @Override
    public String queryAntChainShieldScore(String orderId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        UserThirdInfoDto userThirdInfoDto = userThirdInfoDao.getByUid(userOrders.getUid());
        UserCertification userCertification = userCertificationDao.getByUid(userOrders.getUid());
        OrderLocationAddress orderLocationAddress = orderLocationAddressDao.getOne(new QueryWrapper<OrderLocationAddress>().eq("order_id",orderId));
        QueryAntChainShieldRequest request = new QueryAntChainShieldRequest();
        request.setOrderId(orderId);
        request.setOrderCreateTime(DateUtil.date2String(userOrders.getCreateTime(),DateUtil.DATETIME_FORMAT_1));
        request.setUid(userOrders.getUid());
        request.setMobile(userCertification.getTelephone());
        request.setIdCard(userCertification.getIdCard());
        request.setUserName(userCertification.getUserName());
        request.setAliPayUserId(userThirdInfoDto.getThirdId());
        OrderAddress address = orderAddressDao.queryByOrderId(orderId);
        if(address!=null){
            List<String> distinctIdList = Lists.newArrayList();
            distinctIdList.add(address.getProvince().toString());
            distinctIdList.add(address.getCity().toString());
            if(address.getArea()!=null){
                distinctIdList.add(address.getArea().toString());
            }
            Map<String, String> distinctNameMap = districtService.getDistinctName(distinctIdList);
            String addressStr = distinctNameMap.get(address.getProvince().toString()) + distinctNameMap.get(address.getCity().toString());
            if(address.getArea()!=null){
                addressStr = addressStr + distinctNameMap.get(address.getArea().toString());
            }
            addressStr = addressStr + address.getStreet();
            request.setDeliverAddress(addressStr);
        }else {
            request.setDeliverAddress("-");
        }
        request.setIp("192.168.1.1");
        if(orderLocationAddress!=null){
            request.setLatitude(orderLocationAddress.getLatitude());
            request.setLongitude(orderLocationAddress.getLongitude());
            if(StringUtil.isNotEmpty(orderLocationAddress.getIpAddr())){
                request.setIp(orderLocationAddress.getIpAddr());
            }
        }
        String score = antChainShieldScoreApiService.queryAntChainShieldScore(request);
        antChainStepDao.updateShieldScore(orderId,score);
        return score;
    }

    @Override
    public Boolean syncToAntChain(String orderId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);

        if(!EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM.equals(userOrders.getStatus())){
            throw new HzsxBizException("-1","只有待确认收货状态才能上链");
        }
        if(StringUtil.isEmpty(userOrders.getExpressNo())){
            throw new HzsxBizException("-1","未查询到物流编号");
        }
        OrderContract contract = orderContractDao.getByOrderId(orderId);
        if(contract==null){
            throw new HzsxBizException("-1","未签署合同，无法上链");
        }
        if(StringUtil.isEmpty(contract.getSignedPdf())){
            throw new HzsxBizException("-1","未签署合同，无法上链");
        }
        ProductShopCateReqDto productShopCateReqDto = productService.selectProductCateByProductId(userOrders.getProductId());
        if(productShopCateReqDto.getAntChainProductClassEnum()==null){
            throw new HzsxBizException("-1","未找到一级分类对应的蚂蚁链类目信息:"+productShopCateReqDto.getCategoryName());
        }

        List<OrderByStages> orderByStages = orderByStagesDao.queryOrderByOrderId(orderId);
        OrderByStages last = new OrderByStages();
        last.setCurrentPeriodsRent(BigDecimal.ZERO);
        last.setStatementDate(DateUtil.string2Date("2099-01-01 00:00:00",DateUtil.DATETIME_FORMAT_1));
        orderByStages.add(last);

        //1.同步订单 商品信息-租赁订单用户信息-租赁订单信息-商品信息
        Boolean syncOrderResult = syncOrder(orderId,orderByStages,productShopCateReqDto.getAntChainProductClassEnum());
        if(!syncOrderResult){
            return Boolean.FALSE;
        }
        //2.同步租赁订单承诺信息
        Boolean syncLeasePromiseResult = syncLeasePromise(orderId,orderByStages);
        if(!syncLeasePromiseResult){
            return Boolean.FALSE;
        }
        //3.同步订单物流信息（已发货）
        Boolean syncLogisticResult = syncLogistic(userOrders,true);
        if(!syncLogisticResult){
            return Boolean.FALSE;
        }
        //到这里就算是已经是上链了
        antChainStepDao.updateSyncToChain(orderId);
        //4.同步订单履约信息
        List<OrderByStages> paidStages = new ArrayList<>();
        for (OrderByStages orderByStage : orderByStages) {
            if(EnumOrderByStagesStatus.PAYED.equals(orderByStage.getStatus())){
                paidStages.add(orderByStage);
            }
        }
        syncPerformance(paidStages);
        return Boolean.TRUE;
    }

    @Override
    public Boolean syncLeasePromise(String orderId,List<OrderByStages> orderByStages) {
        List<String> payDateList = new ArrayList<>(orderByStages.size());
        List<String> payMoneyList = new ArrayList<>(orderByStages.size());
        Long payPeriod = 0L;
        for (OrderByStages orderByStage : orderByStages) {
            payMoneyList.add(tenThousand.multiply(orderByStage.getCurrentPeriodsRent()).longValue()+"");
            payDateList.add((DateUtil.date2String(orderByStage.getStatementDate(), DateUtil.DATETIME_FORMAT_6)));
            payPeriod++;
        }
        AntChainSyncLeasePromise req = new AntChainSyncLeasePromise();
        req.setOrderId(orderId);
        req.setPayDateList(payDateList);
        req.setPayPeriod(payPeriod);
        req.setPayMoneyList(payMoneyList);
        return antChainService.syncLeasePromise(req);
    }

    @Override
    public Boolean syncOrder(String orderId, List<OrderByStages> orderByStages, AntChainProductClassEnum antChainProductClassEnum) {
        OrderContract contract = orderContractDao.getByOrderId(orderId);
        if(contract==null){
            throw new HzsxBizException("-1","未签署合同，无法上链");
        }
        String url = contract.getSignedPdf();
        if(StringUtil.isEmpty(contract.getSignedPdf())){
            throw new HzsxBizException("-1","未签署合同，无法上链");
        }
        AntChainSyncOrder antChainSyncOrder = new AntChainSyncOrder();

        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        antChainSyncOrder.setSerialNumber(userOrders.getSerialNumber());
        antChainSyncOrder.setCostPrice(tenThousand.multiply(userOrders.getCostPrice()).longValue());
        antChainSyncOrder.setAntChainProductClassEnum(antChainProductClassEnum);
        antChainSyncOrder.setOrderId(orderId);
        antChainSyncOrder.setOrderCreateTime(DateUtil.dateStr4(userOrders.getCreateTime()));
        OrderByStages firstStage = null;
        for (OrderByStages orderByStage : orderByStages) {
            if(orderByStage.getCurrentPeriods()==1){
                firstStage = orderByStage;
                break;
            }
        }
        antChainSyncOrder.setOrderPayTime(DateUtil.dateStr4(firstStage.getStatementDate()));
        antChainSyncOrder.setOrderPayId(firstStage.getTradeNo());
        UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
        Long depositFree = tenThousand.multiply(userOrderCashes.getDepositReduction()).longValue();
        antChainSyncOrder.setDepositFree(depositFree);
        Long acutalPreAuthFree = tenThousand.multiply(userOrderCashes.getDeposit()).longValue();
        antChainSyncOrder.setAcutalPreAuthFree(acutalPreAuthFree);
        antChainSyncOrder.setRentTerm(orderByStages.size());
        Long rentPricePerMonth = tenThousand.multiply(firstStage.getCurrentPeriodsRent()).longValue();
        antChainSyncOrder.setRentPricePerMonth(rentPricePerMonth);
        antChainSyncOrder.setLeaseContractUrl(url);
        OrderAddress address = orderAddressDao.queryByOrderId(orderId);
        antChainSyncOrder.setUserAddress(address.getStreet());
        antChainSyncOrder.setProvinceCode(address.getProvince());
        antChainSyncOrder.setCityCode(address.getCity());
        antChainSyncOrder.setDistrictCode(address.getArea());
        Long totalRentMoney = tenThousand.multiply(firstStage.getTotalRent()).longValue();
        antChainSyncOrder.setTotalRentMoney(totalRentMoney);

        PlatformChannelDto platFormChannel = platformChannelService.getPlatFormChannel(userOrders.getChannelId());
        antChainSyncOrder.setLeaseCorpName(platFormChannel.getEnterpriseName());
        antChainSyncOrder.setLeaseCorpId(platFormChannel.getEnterpriseLicenseNo());
        antChainSyncOrder.setLeaseCorpOwnerName(platFormChannel.getEnterpriseLegalName());

        Map<String, OrderProductDetailDto> detailDtoMap = userOrdersQueryService.selectOrderProductDetail(Collections.singletonList(orderId));
        OrderProductDetailDto detailDto = detailDtoMap.get(orderId);

        antChainSyncOrder.setProductId(detailDto.getProductId());
        antChainSyncOrder.setProductName(detailDto.getProductName());
        antChainSyncOrder.setProductModel(detailDto.getSkuTitle());
        Long productPrice = tenThousand.multiply(detailDto.getMarketPrice()).longValue();
        antChainSyncOrder.setProductPrice(productPrice);
        antChainSyncOrder.setDepositFree(productPrice);
        antChainSyncOrder.setDepositPrice(productPrice);

        if(detailDto.getBuyOutSupport().intValue()!=0){
            BigDecimal buyOutPrice = detailDto.getSalePrice().subtract(orderByStages.get(0).getTotalRent());
            buyOutPrice = buyOutPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : buyOutPrice;
            buyOutPrice = tenThousand.multiply(buyOutPrice);
            antChainSyncOrder.setBuyOutPrice(buyOutPrice.longValue());
        }else {
            antChainSyncOrder.setBuyOutPrice(productPrice);
        }

        UserCertification userCertification = userCertificationDao.getByUid(userOrders.getUid());
        antChainSyncOrder.setUid(userCertification.getUid());
        antChainSyncOrder.setLoginTime(DateUtil.date2String(userCertification.getCreateTime(), DateUtil.DATETIME_FORMAT_1));
        antChainSyncOrder.setUserName(userCertification.getUserName());
        antChainSyncOrder.setIdCard(userCertification.getIdCard());
        antChainSyncOrder.setUserIdCardFrontObjectKey(userCertification.getIdCardFrontUrl());
        antChainSyncOrder.setPhone(userCertification.getTelephone());

        UserThirdInfoDto userThirdInfoDto = userThirdInfoDao.getByUid(userOrders.getUid());
        antChainSyncOrder.setAlipayUID(userThirdInfoDto.getThirdId());
        AntChainStep antChainStep = antChainStepDao.getByOrderId(orderId);
        if(antChainStep!=null && StringUtil.isNotEmpty(antChainStep.getShieldScore())){
            antChainSyncOrder.setYidunScore(antChainStep.getShieldScore());
        }
        return antChainService.syncOrder(antChainSyncOrder);

    }

    @Override
    public Boolean syncLogistic(UserOrders userOrders,Boolean first) {
        if(!first){
            AntChainStep antChainStep = antChainStepDao.getByOrderId(userOrders.getOrderId());
            if(antChainStep==null){
                return Boolean.FALSE;
            }
            if(antChainStep.getSyncToChain()!=null && !antChainStep.getSyncToChain()){
                return Boolean.FALSE;
            }
        }
        OrderAddress address = orderAddressDao.queryByOrderId(userOrders.getOrderId());
        String receiverPhone = address.getTelephone();
        AntChainSyncLogistic antChainSyncLogistic = new AntChainSyncLogistic();
        antChainSyncLogistic.setOrderId(userOrders.getOrderId());
        PlatformExpress platformExpress = platformExpressDao.selectExpressById(userOrders.getExpressId());
        if(userOrders.getReceiveTime()!=null){
            antChainSyncLogistic.setArriveConfirmTime(DateUtil.date2String(userOrders.getReceiveTime(), DateUtil.DATETIME_FORMAT_6));
        }
        StringBuilder url = new StringBuilder(OutsideConfig.DOMAIN)
                .append("hzsx/ope/order/queryExpressInfo?expressNo=")
                .append(userOrders.getExpressNo())
                .append("&receiverPhone=")
                .append(receiverPhone)
                .append("&shortName=")
                .append(platformExpress.getShortName());
        antChainSyncLogistic.setArriveConfirmUrl(url.toString());
        antChainSyncLogistic.setLogisticCompanyName(platformExpress.getName());
        antChainSyncLogistic.setLogisticsOrderId(userOrders.getExpressNo());
        antChainSyncLogistic.setDeliverTime(DateUtil.date2String(userOrders.getDeliveryTime(), DateUtil.DATETIME_FORMAT_6));
        PlatformChannelDto platformChannelDto = platformChannelService.getPlatFormChannel(userOrders.getChannelId());
        antChainSyncLogistic.setLeaseCorpName(platformChannelDto.getEnterpriseName());
        antChainSyncLogistic.setLeaseCorpId(platformChannelDto.getEnterpriseLicenseNo());
        antChainSyncLogistic.setLeaseCorpOwnerName(platformChannelDto.getEnterpriseLegalName());
        return antChainService.syncLogistic(antChainSyncLogistic);
    }

    @Override
    public void syncPerformance(List<OrderByStages> orderByStagesList) {
        Integer remainPeriodCount =orderByStagesDao.getRemainPeriodCount(orderByStagesList.get(0).getOrderId());
        for (OrderByStages orderByStages : orderByStagesList) {
            String tradeNo = orderByStages.getTradeNo();
            String outTradeNo = orderByStages.getOutTradeNo();
            AntChainSyncPerformance req = new AntChainSyncPerformance();
            req.setOrderId(orderByStages.getOrderId());
            req.setLeaseTermIndex(orderByStages.getCurrentPeriods());
            //租金归还状态，1足额归还，2部分归还，3未归还，4退租，5该订单整个生命周期已完结
            req.setRentalReturnState(1);
            req.setRentalMoney(tenThousand.multiply(orderByStages.getCurrentPeriodsRent()).longValue());
            req.setReturnTime(DateUtil.getStandardNowTime());
            if(outTradeNo.startsWith("ATP")){
                req.setReturnWay(1);
                req.setReturnVoucherType(1);
                req.setReturnVoucherSerial(tradeNo);
                req.setCharge(chargePercent.multiply(tenThousand).multiply(orderByStages.getCurrentPeriodsRent()).longValue());
            }else if(outTradeNo.startsWith("AAP")){
                req.setReturnWay(3);
                req.setReturnVoucherType(2);
                req.setReturnVoucherSerial(tradeNo);
                req.setCharge(0L);
            }else {
                req.setReturnWay(4);
                req.setReturnVoucherType(3);
                req.setReturnVoucherSerial(System.currentTimeMillis()+"");
                req.setCharge(0L);
            }
            req.setRemainTerm(remainPeriodCount);
            Boolean result = antChainService.syncPerformance(req);
            if(result){
                orderByStages.setSyncToChain(Boolean.TRUE);
                orderByStagesDao.updateById(orderByStages);
            }
        }
    }


    @Override
    public Boolean syncFinish(String orderId) {
        List<OrderByStages> orderByStages = orderByStagesDao.queryOrderByOrderId(orderId);
        AntChainSyncPerformance req = new AntChainSyncPerformance();
        req.setOrderId(orderId);
        req.setLeaseTermIndex(orderByStages.size()+1);
        req.setRentalReturnState(5);
        req.setRentalMoney(0L);
        req.setReturnTime(DateUtil.getStandardNowTime());
        req.setReturnWay(4);
        req.setReturnVoucherType(3);
        req.setReturnVoucherSerial(orderId+"_"+req.getLeaseTermIndex());
        req.setCharge(0L);
        req.setRemainTerm(0);
        return antChainService.syncPerformance(req);
    }

    @Override
    public Boolean antChainInsure(String orderId) {
        AntChainStep antChainStep = antChainStepDao.getByOrderId(orderId);
        if(antChainStep==null){
            throw new HzsxBizException("-1","未查询到蚁盾分");
        }
        if(StringUtils.isEmpty(antChainStep.getShieldScore())){
            throw new HzsxBizException("-1","未查询到蚁盾分");
        }
        String[] yidunScores = antChainStep.getShieldScore().split("\\.");
        String yidunScore = yidunScores[0];
        if(Long.parseLong(yidunScore)>80){
            throw new HzsxBizException("-1","蚁盾分超过80,无法投保");
        }
        if(!antChainStep.getSyncToChain()){
            throw new HzsxBizException("-1","未上链,无法投保");
        }
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if(!userOrders.getStatus().equals(EnumOrderStatus.RENTING)){
            throw new HzsxBizException("-1","订单状态错误,只有租用中的订单才能投保");
        }
        int betweenDays = DateUtil.getBetweenDays(userOrders.getReceiveTime(),DateUtil.getNowDate());
        if(Math.abs(betweenDays)>15){
            throw new HzsxBizException("-1","不在签收时间15天之内");
        }
        ProductShopCateReqDto productShopCateReqDto = productService.selectProductCateByProductId(userOrders.getProductId());
        UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(userOrders.getOrderId());
        ProductSkus productSkus = productSkusDao.getById(userOrderItems.getSkuId());
        if(1 != productSkus.getOldNewDegree().intValue()){
            throw new HzsxBizException("-1","二手商品无法投保");
        }
        AntChainProductClassEnum antChainProductClassEnum = productShopCateReqDto.getAntChainProductClassEnum();
        OrderByStages lastPeriod = orderByStagesDao.getLastPeriod(orderId);
        int month = lastPeriod.getCurrentPeriods() > antChainProductClassEnum.getMaxInsureMonth() ? antChainProductClassEnum.getMaxInsureMonth() : lastPeriod.getCurrentPeriods();
        month = month < 1 ? 1 : month;
        Boolean result =  antChainInsuranceService.antChainInsure(orderId,month,userOrders.getChannelId());
        if(result){
            antChainStepDao.updateInsure(orderId);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean antChainCancelInsurance(String orderId) {
        AntChainStep antChainStep = antChainStepDao.getByOrderId(orderId);
        if(antChainStep==null){
            throw new HzsxBizException("-1","该订单未投保");
        }
        if(StringUtils.isEmpty(antChainStep.getShieldScore())){
            throw new HzsxBizException("-1","该订单未投保");
        }
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        Boolean result =  antChainInsuranceService.cancelInsurance(orderId,userOrders.getChannelId());
        if(result){
            antChainStepDao.updateUnInsure(orderId);
        }
        return result;
    }
}
