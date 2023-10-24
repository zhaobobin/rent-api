package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.ShopSplitRuleConstant;
import com.rent.common.converter.order.OrderByStagesConverter;
import com.rent.common.converter.order.SplitBillConverter;
import com.rent.common.converter.order.UserOrderBuyOutConverter;
import com.rent.common.converter.order.UserOrderCashesConverter;
import com.rent.common.dto.backstage.SplitBilRentlDto;
import com.rent.common.dto.backstage.SplitBillDetailBuyOutDto;
import com.rent.common.dto.backstage.SplitBillDetailRentDto;
import com.rent.common.dto.order.AccountPeriodItemReqDto;
import com.rent.common.dto.order.OrderByStagesDto;
import com.rent.common.dto.order.UserOrderBuyOutDto;
import com.rent.common.dto.order.UserOrderCashesDto;
import com.rent.common.dto.order.response.BuyOutOriginalOrderDto;
import com.rent.common.dto.order.response.SplitBillBuyOutDto;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.dao.components.AlipayFreezeDao;
import com.rent.dao.order.*;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.AlipayFreeze;
import com.rent.model.order.*;
import com.rent.service.order.AccountPeriodItemService;
import com.rent.service.order.OpeUserOrdersService;
import com.rent.service.user.UserCertificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class AccountPeriodItemServiceImpl implements AccountPeriodItemService {

    private final SplitBillDao splitBillDao;
    private final UserOrdersDao userOrdersDao;
    private final OrderByStagesDao orderByStagesDao;
    private final UserOrderBuyOutDao userOrderBuyOutDao;
    private final UserOrderCashesDao userOrderCashesDao;
    private final OpeUserOrdersService opeUserOrdersService;
    private final AccountPeriodDao accountPeriodDao;
    private final UserCertificationService userCertificationService;
    private final AlipayFreezeDao alipayFreezeDao;

    @Override
    public Page<SplitBilRentlDto> rent(AccountPeriodItemReqDto request) {
        AccountPeriod accountPeriod = accountPeriodDao.getById(request.getAccountPeriodId());
        if(!accountPeriod.getShopId().equals(request.getShopId()) && !EnumBackstageUserPlatform.OPE.getCode().equals(request.getShopId())){
            throw new HzsxBizException("-1","查询失败");
        }
        Page<SplitBill> page = splitBillDao.queryPage(ShopSplitRuleConstant.TYPE_RENT,request.getPageNumber(),request.getPageSize(),request.getAccountPeriodId());
        return packRentItem(page,request);
    }
    @Override
    public Page<SplitBilRentlDto> listRent(AccountPeriodItemReqDto request) {
        Page<SplitBill> page = splitBillDao.queryPage(ShopSplitRuleConstant.TYPE_RENT,request);
        return packRentItem(page,request);
    }

    private Page<SplitBilRentlDto> packRentItem(Page<SplitBill> page,AccountPeriodItemReqDto request){
        List<SplitBilRentlDto> list = SplitBillConverter.modelList2RentDtoList(page.getRecords());
        if(CollectionUtil.isEmpty(list)){
            Page<SplitBilRentlDto> emptyPage = new Page<>(request.getPageNumber(), request.getPageSize());
            return emptyPage;
        }
        for (SplitBilRentlDto splitBillDto : list) {
            UserOrders userOrders = userOrdersDao.selectOneByOrderId(splitBillDto.getOrderId());
            splitBillDto.setOrderStatus(userOrders.getStatus());
            OrderByStages orderByStages = orderByStagesDao.queryOrderByOrderIdAndPeriod(splitBillDto.getOrderId(),splitBillDto.getPeriod());
            splitBillDto.setStatementDate(orderByStages.getStatementDate());
            splitBillDto.setTotalPeriod(orderByStages.getTotalPeriods());
            splitBillDto.setName(splitBillDto.getName()+"/"+splitBillDto.getIdentity());
        }
        return new Page<SplitBilRentlDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(list);
    }

    @Override
    public Page<SplitBillBuyOutDto> buyOut(AccountPeriodItemReqDto request) {
        AccountPeriod accountPeriod = accountPeriodDao.getById(request.getAccountPeriodId());
        if(!accountPeriod.getShopId().equals(request.getShopId()) && !EnumBackstageUserPlatform.OPE.getCode().equals(request.getShopId())){
            throw new HzsxBizException("-1","查询失败");
        }
        Page<SplitBill> page = splitBillDao.queryPage(ShopSplitRuleConstant.TYPE_BUY_OUT,request.getPageNumber(),request.getPageSize(),request.getAccountPeriodId());
        return packBuyOutItem(page,request);
    }
    @Override
    public Page<SplitBillBuyOutDto> listBuyOut(AccountPeriodItemReqDto request) {
        Page<SplitBill> page = splitBillDao.queryPage(ShopSplitRuleConstant.TYPE_BUY_OUT,request);
        return packBuyOutItem(page,request);
    }

    private Page<SplitBillBuyOutDto> packBuyOutItem(Page<SplitBill> page,AccountPeriodItemReqDto request){
        List<SplitBillBuyOutDto> list = SplitBillConverter.modelList2BuyOutDtoList(page.getRecords());
        if(CollectionUtil.isEmpty(list)){
            Page<SplitBillBuyOutDto> emptyPage = new Page<>(request.getPageNumber(), request.getPageSize());
            return emptyPage;
        }
        List<String> uidList = list.stream().map(SplitBillBuyOutDto::getUid).collect(Collectors.toList());
        Map<String, UserCertificationDto> userCertificationDtoMap =  userCertificationService.queryUserCertificationList(uidList);
        for (SplitBillBuyOutDto splitBillBuyOutDto : list) {
            UserOrderBuyOut userOrderBuyOut = userOrderBuyOutDao.selectOneByBuyOutOrderId(splitBillBuyOutDto.getOrderId());
            splitBillBuyOutDto.setOriginOrderId(userOrderBuyOut.getOrderId());
            splitBillBuyOutDto.setSalePrice(userOrderBuyOut.getSalePrice());
            splitBillBuyOutDto.setEndFund(userOrderBuyOut.getEndFund());
            splitBillBuyOutDto.setUserPayAmount(userOrderBuyOut.getPaidRent());
            if(userCertificationDtoMap.containsKey(splitBillBuyOutDto.getUid())){
                UserCertificationDto userCertificationDto = userCertificationDtoMap.get(splitBillBuyOutDto.getUid());
                splitBillBuyOutDto.setUsername(userCertificationDto.getUserName());
                splitBillBuyOutDto.setPhone(userCertificationDto.getTelephone());
            }
        }
        return new Page<SplitBillBuyOutDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(list);
    }

    private SplitBill getByIdAndShopId(Long id, String shopId){
        SplitBill splitBill = splitBillDao.getById(id);
        if(splitBill==null){
            throw new HzsxBizException("-1","未查询到该分账记录信息");
        }
        if(!splitBill.getShopId().equals(shopId) && !EnumBackstageUserPlatform.OPE.getCode().equals(shopId)){
            throw new HzsxBizException("-1","未查询到该分账记录信息");
        }
        return splitBill;
    }

    @Override
    public SplitBillDetailRentDto rentDetail(Long id, String shopId) {
        SplitBill splitBill = getByIdAndShopId(id,shopId);
        String orderId = splitBill.getOrderId();
        SplitBillDetailRentDto orderDetailDto = new SplitBillDetailRentDto();
        //订单信息
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        orderDetailDto.setOrderInfoDto(opeUserOrdersService.assemblyOrderInfo(userOrders));
        //商家信息
        orderDetailDto.setShopInfoDto(opeUserOrdersService.assemblyOrderShopInfo(userOrders));
        //商品信息
        orderDetailDto.setProductInfo(opeUserOrdersService.assemblyOrderProductInfo(userOrders));
        //账单信息
        UserOrderCashesDto userOrderCashesDto = UserOrderCashesConverter.model2Dto(userOrderCashesDao.selectOneByOrderId(orderId));
        AlipayFreeze alipayFreezeDto = alipayFreezeDao.selectOneByOrderId(orderId,EnumAliPayStatus.SUCCESS);
        if (null != alipayFreezeDto) {
            userOrderCashesDto.setCreditDeposit(alipayFreezeDto.getCreditAmount());
        }
        orderDetailDto.setUserOrderCashesDto(userOrderCashesDto);
        //分期信息
        List<OrderByStagesDto> orderByStagesDtoList = OrderByStagesConverter.modelList2DtoList(orderByStagesDao.queryOrderByOrderId(orderId));
        packDetailStagesSpiltBillInfo(orderByStagesDtoList);
        orderDetailDto.setOrderByStagesDtoList(orderByStagesDtoList);
        //买断信息
        UserOrderBuyOutDto userOrderBuyOutDto = UserOrderBuyOutConverter.model2Dto(userOrderBuyOutDao.selectOneByOrderId(orderId));
        if(userOrderBuyOutDto!=null){
            packDetailBuyOutSpiltBillInfo(userOrderBuyOutDto,splitBill);
            orderDetailDto.setUserOrderBuyOutDto(userOrderBuyOutDto);
        }
        return orderDetailDto;
    }


    @Override
    public SplitBillDetailBuyOutDto buyOutDetail(Long id, String shopId) {
        SplitBill splitBill = getByIdAndShopId(id,shopId);
        UserOrderBuyOut userOrderBuyOut = userOrderBuyOutDao.selectOneByBuyOutOrderId(splitBill.getOrderId());
        if (null == userOrderBuyOut) {
            return null;
        }
        String orderId = splitBill.getOrderId();
        SplitBillDetailBuyOutDto orderDetailDto = new SplitBillDetailBuyOutDto();
        //买断信息
        UserOrderBuyOutDto userOrderBuyOutDto = UserOrderBuyOutConverter.model2Dto(userOrderBuyOutDao.selectOneByBuyOutOrderId(orderId));
        packDetailBuyOutSpiltBillInfo(userOrderBuyOutDto,splitBill);
        orderDetailDto.setUserOrderBuyOutDto(userOrderBuyOutDto);
        //订单信息
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(userOrderBuyOutDto.getOrderId());
        orderDetailDto.setOrderInfoDto(opeUserOrdersService.assemblyOrderInfo(userOrders));
        //商家信息
        orderDetailDto.setShopInfoDto(opeUserOrdersService.assemblyOrderShopInfo(userOrders));
        //商品信息
        orderDetailDto.setProductInfo(opeUserOrdersService.assemblyOrderProductInfo(userOrders));
        //账单信息
        UserOrderCashesDto userOrderCashesDto = UserOrderCashesConverter.model2Dto(userOrderCashesDao.selectOneByOrderId(userOrders.getOrderId()));
        AlipayFreeze alipayFreezeDto = alipayFreezeDao.selectOneByOrderId(userOrders.getOrderId(), EnumAliPayStatus.SUCCESS);
        if (null != alipayFreezeDto) {
            userOrderCashesDto.setCreditDeposit(alipayFreezeDto.getCreditAmount());
        }
        orderDetailDto.setUserOrderCashesDto(userOrderCashesDto);

        //原订单信息
        BuyOutOriginalOrderDto outOriginalOrderDto = opeUserOrdersService.assemblyBuyOutOriginalOrderDto(userOrders);
        outOriginalOrderDto.setOrderId(userOrders.getOrderId());
        orderDetailDto.setOriginalOrderInfo(outOriginalOrderDto);
        return orderDetailDto;
    }





    /**
     * 分账详细信息页面  补充买断信息的分账信息
     * @param userOrderBuyOutDto
     * @param splitBill
     */
    private void packDetailBuyOutSpiltBillInfo(UserOrderBuyOutDto userOrderBuyOutDto,SplitBill splitBill){
        if(splitBill == null){
            splitBill = splitBillDao.getByOrderId(userOrderBuyOutDto.getBuyOutOrderId(),null);
        }
        userOrderBuyOutDto.setUserName(splitBill.getName());
        userOrderBuyOutDto.setToShopAmount(splitBill.getTransAmount());
        userOrderBuyOutDto.setToOpeAmount(splitBill.getUserPayAmount().subtract(splitBill.getTransAmount()));
        userOrderBuyOutDto.setSplitBillStatus(splitBill.getStatus());
    }

    private void packDetailStagesSpiltBillInfo(List<OrderByStagesDto> orderByStagesDtoList){
        for (OrderByStagesDto orderByStagesDto : orderByStagesDtoList) {
            SplitBill splitBill = splitBillDao.getByOrderId(orderByStagesDto.getOrderId(),orderByStagesDto.getCurrentPeriods());
            if(splitBill!=null){
                orderByStagesDto.setUserName(splitBill.getName()+"/"+splitBill.getIdentity());
                orderByStagesDto.setToShopAmount(splitBill.getTransAmount());
                orderByStagesDto.setToOpeAmount(splitBill.getUserPayAmount().subtract(splitBill.getTransAmount()));
                orderByStagesDto.setSplitBillStatus(splitBill.getStatus());
            }
        }
    }
//
//    /**
//     * 封装列表请求参数之 orderIdList
//     * @param request
//     * @return
//     */
//    private List<String> packQueryOrderIdList(SplitBillReqDto request,String type){
//        List<String> orderIdList = null;
//        if(StringUtil.isNotEmpty(request.getOrderId())){
//            orderIdList = new ArrayList<>();
//            orderIdList.add(request.getOrderId());
//        }else{
//            Boolean hasDate = request.getBegin()!=null &&  request.getEnd()!=null;
//            if(hasDate || StringUtil.isNotEmpty(request.getOrderStatus()) ){
//                if(ShopSplitRuleConstant.TYPE_SHOPPING.equals(type)){
//                    orderIdList = userOrdersPurchaseDao.getOrderIdList(request.getBegin(),request.getEnd(),request.getOrderStatus());
//                }
//                if(ShopSplitRuleConstant.TYPE_BUY_OUT.equals(type)){
//                    orderIdList = userOrderBuyOutDao.getOrderIdList(request.getBegin(),request.getEnd(),request.getOrderStatus());
//                }
//                if(ShopSplitRuleConstant.TYPE_RENT.equals(type)){
//                    orderIdList = userOrdersDao.getOrderIdList(request.getBegin(),request.getEnd(),request.getOrderStatus());
//                }
//                orderIdList.add("-1");
//            }
//        }
//        return orderIdList;
//    }

}
