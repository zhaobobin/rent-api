package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.rent.common.constant.RedisKey;
import com.rent.common.converter.order.*;
import com.rent.common.dto.backstage.*;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.components.response.AliPayTradePayResponse;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.response.BackstageBuyOutOrderDetailDto;
import com.rent.common.dto.order.response.BuyOutOriginalOrderDto;
import com.rent.common.dto.order.resquest.CloseOrderByConditionRequest;
import com.rent.common.dto.order.resquest.OrderRentChangeReqDto;
import com.rent.common.dto.order.resquest.QueryBuyOutOrdersRequest;
import com.rent.common.dto.order.resquest.TelephoneOrderAuditRequest;
import com.rent.common.dto.product.*;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumTradeResult;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.components.OrderCenterStatus;
import com.rent.common.enums.order.*;
import com.rent.common.enums.product.ProductStatus;
import com.rent.common.util.AmountUtil;
import com.rent.common.util.AsyncUtil;
import com.rent.common.util.BeanUtils;
import com.rent.common.util.OrderCheckUtil;
import com.rent.dao.components.AlipayFreezeDao;
import com.rent.dao.order.*;
import com.rent.dao.user.UserBankCardDao;
import com.rent.dao.user.UserDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.AlipayFreeze;
import com.rent.model.order.*;
import com.rent.model.product.PlatformExpress;
import com.rent.model.user.UserBankCard;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.components.OrderCenterService;
import com.rent.service.components.SuningOpenApiService;
import com.rent.service.marketing.LiteUserCouponService;
import com.rent.service.order.*;
import com.rent.service.product.*;
import com.rent.service.user.DistrictService;
import com.rent.service.user.UserCertificationService;
import com.rent.service.user.UserEmergencyContactService;
import com.rent.util.DateUtil;
import com.rent.util.LoginUserUtil;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-9 下午 2:25:23
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OpeUserOrdersServiceImpl implements OpeUserOrdersService {

    private final UserOrdersDao userOrdersDao;
    private final UserOrderCashesDao userOrderCashesDao;
    private final OrderByStagesDao orderByStagesDao;
    private final UserOrdersQueryService userOrdersQueryService;
    private final OrderAddressDao orderAddressDao;
    private final UserOrderBuyOutDao userOrderBuyOutDao;
    private final OrderRemarkDao orderRemarkDao;
    private final OrderReportDao orderReportDao;
    private final OrderOperateCore orderOperateCore;
    private final OrderAdditionalServicesDao orderAdditionalServicesDao;
    private final OrderSettlementDao orderSettlementDao;
    private final OrderContractDao orderContractDao;
    private final OrderRepayPlanFactory orderRepayPlanFactory;
    private final OrderLocationAddressService orderLocationAddressService;
    private final OrderPayDepositDao orderPayDepositDao;
    private final PlatformChannelService platformChannelService;
    private final ShopService shopService;
    private final UserCertificationService userCertificationService;
    private final ProductService productService;
    private final UserDao userDao;
    private final ProductAdditionalServicesService productAdditionalServicesService;
    private final AlipayFreezeDao alipayFreezeDao;
    private final DistrictService districtService;
    private final PlatformExpressService platformExpressService;
    private final ShopGiveBackAddressesService shopGiveBackAddressesService;
    private final LiteUserCouponService liteUserCouponService;
    private final UserOrdersService userOrdersService;
    private final AliPayCapitalService aliPayCapitalService;
    private final UserOrderItemsDao userOrderItemsDao;
    private final OrderCenterService orderCenterService;
    private final AntChainStepDao antChainStepDao;
    private final OrderAuditService orderAuditService;
    private final UserBankCardDao userBankCardDao;
    private final SuningOpenApiService suningOpenApiService;
    private final UserOrdersLiteService userOrdersLiteService;
    private final UserEmergencyContactService userEmergencyContactService;
    private final SplitBillDao splitBillDao;

    //处理中集合
    private static final List<String> PROCESSING_STATUS_LIST = Arrays.asList(EnumOrderStatus.TO_AUDIT.getCode(),
            EnumOrderStatus.PENDING_DEAL.getCode(), EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM.getCode(),
            EnumOrderStatus.RENTING.getCode(), EnumOrderStatus.WAITING_SETTLEMENT.getCode(),
            EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT.getCode());
    //已付款集合
    private static final List<EnumOrderStatus> PAYED_STATUS_LIST = Arrays.asList(EnumOrderStatus.TO_AUDIT, EnumOrderStatus.PENDING_DEAL,
            EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM, EnumOrderStatus.WAITING_SETTLEMENT,
            EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT);

    @Override
    public Page<BackstageUserOrderDto> queryOpeOrderByCondition(OrderByConditionRequest model) {
        OrderCheckUtil.checkQueryDate(model.getCreateTimeStart(), model.getCreateTimeEnd(), EnumOrderError.ORDER_CREATE_START_END_ALL);
        Page<BackstageUserOrderDto> emptyPage = new Page<>(model.getPageNumber(), model.getPageSize());
        Page<BackstageUserOrderDto> userOrdersPage = this.userOrdersDao.queryOpeOrderByCondition(model);
        if (CollectionUtil.isEmpty(userOrdersPage.getRecords())) {
            return emptyPage;
        }
        List<String> orderIdList = userOrdersPage.getRecords().stream().map(BackstageUserOrderDto::getOrderId).collect(toList());
        //账单信息
        Map<String, List<OrderByStages>> stagesByOrderMap = orderByStagesDao.queryOrderByStagesByOrders(new ArrayList<>(orderIdList));
        for (BackstageUserOrderDto backstageUserOrderDto : userOrdersPage.getRecords()) {
            String orderId = backstageUserOrderDto.getOrderId();
            backstageUserOrderDto.setChannelName(platformChannelService.getPlatFormChannel(backstageUserOrderDto.getChannelId()).getChannelName());
            List<OrderByStages> stages = stagesByOrderMap.get(orderId);
            BigDecimal payedRent = BigDecimal.ZERO;
            int payedPeriods = 0;
            if (CollectionUtil.isNotEmpty(stages)) {
                for (OrderByStages stage : stages) {
                    backstageUserOrderDto.setTotalPeriods(stage.getTotalPeriods());
                    backstageUserOrderDto.setTotalRentAmount(stage.getTotalRent());
                    if (EnumOrderByStagesStatus.OVERDUE_PAYED.equals(stage.getStatus())
                            || EnumOrderByStagesStatus.PAYED.equals(stage.getStatus())
                            || EnumOrderByStagesStatus.REFUNDED.equals(stage.getStatus())) {
                        payedRent = AmountUtil.safeAdd(payedRent, stage.getCurrentPeriodsRent());
                        payedPeriods++;
                    }
                }
            }
            backstageUserOrderDto.setPayedPeriods(payedPeriods);
            backstageUserOrderDto.setPayedRentAmount(payedRent);


            // 已结租金
            BigDecimal settlementAmount = BigDecimal.ZERO;
            List<SplitBill> splitBillList = splitBillDao.getByOrderIdList(orderId);
            if (CollectionUtil.isNotEmpty(splitBillList)) {
                for (SplitBill splitBill : splitBillList) {
                    if (splitBill.getStatus().equals("SETTLED")) {
                        settlementAmount = settlementAmount.add(splitBill.getTransAmount());
                    }
                }
            }
            backstageUserOrderDto.setSettlementAmount(settlementAmount);
        }
        Page<BackstageUserOrderDto> page = new Page<>(userOrdersPage.getCurrent(), userOrdersPage.getSize(), userOrdersPage.getTotal());
        page.setRecords(userOrdersPage.getRecords());
        return page;
    }

    @Override
    public BackstageUserOrderDetailDto queryOpeUserOrderDetail(String orderId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(), EnumOrderError.ORDER_NOT_EXISTS.getMsg(), this.getClass());
        }
        BackstageUserOrderDetailDto orderDetailDto = new BackstageUserOrderDetailDto();
        orderDetailDto.setOrderId(userOrders.getOrderId());
        orderDetailDto.setRentDuration(userOrders.getRentDuration());
        orderDetailDto.setRentStart(userOrders.getRentStart());
        orderDetailDto.setUnrentTime(userOrders.getUnrentTime());
        orderDetailDto.setNsfLevel(userOrders.getNsfLevel());

        //查询订单地址信息
        OrderAddressDto orderAddressDto = OrderAddressConverter.model2Dto(orderAddressDao.queryByOrderId(orderId));
        if (null != orderAddressDto) {
            List<String> distinctIdList = Lists.newArrayList();
            if (null != orderAddressDto.getProvince()) {
                distinctIdList.add(orderAddressDto.getProvince().toString());
            }
            if (null != orderAddressDto.getCity()) {
                distinctIdList.add(orderAddressDto.getCity().toString());
            }
            if (null != orderAddressDto.getArea()) {
                distinctIdList.add(orderAddressDto.getArea().toString());
            }
            Map<String, String> distinctNameMap = districtService.getDistinctName(distinctIdList);
            orderAddressDto.setProvinceStr(distinctNameMap.get(orderAddressDto.getProvince().toString()));
            orderAddressDto.setCityStr(distinctNameMap.get(orderAddressDto.getCity().toString()));
            if (null != orderAddressDto.getArea()) {
                orderAddressDto.setAreaStr(distinctNameMap.get(orderAddressDto.getArea().toString()));
            }
            //组装收货地址信息
            orderDetailDto.setOrderAddressDto(orderAddressDto);
        }
        //查询订单金额信息
        UserOrderCashesDto userOrderCashesDto = UserOrderCashesConverter.model2Dto(userOrderCashesDao.selectOneByOrderId(orderId));

        //查询订单买断信息
        UserOrderBuyOutDto userOrderBuyOutDto = UserOrderBuyOutConverter.model2Dto(userOrderBuyOutDao.selectOneByOrderIdAndStatus(orderId, EnumOrderBuyOutStatus.FINISH));
        //查询信用减免金额
        AlipayFreeze alipayFreeze = alipayFreezeDao.selectOneByOrderId(orderId, EnumAliPayStatus.SUCCESS);
        if (null != alipayFreeze) {
            userOrderCashesDto.setFreezePrice(alipayFreeze.getAmount());
            userOrderCashesDto.setCreditDeposit(alipayFreeze.getCreditAmount());
        } else {
            userOrderCashesDto.setFreezePrice(BigDecimal.ZERO);
            userOrderCashesDto.setCreditDeposit(BigDecimal.ZERO);
        }
        BigDecimal platformCouponReduction = null != userOrderCashesDto.getPlatformCouponReduction() ? userOrderCashesDto.getPlatformCouponReduction() : BigDecimal.ZERO;
        List<UserOrderCouponDto> userOrderCouponDtos = Lists.newArrayList();
        if (null != userOrderCashesDto) {
            Boolean isRecall = Boolean.FALSE;
            if (null != userOrderCashesDto.getCouponRecallReduction()) {
                //加了订单唤回的叠加优惠券，后台管理显示时都算在平台优惠券里面
                isRecall = Boolean.TRUE;
                userOrderCashesDto.setPlatformCouponReduction(platformCouponReduction.add(userOrderCashesDto.getCouponRecallReduction()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            if (null != userOrderCashesDto.getRetainDeductionAmount()) {
                userOrderCouponDtos.add(UserOrderCouponDto.builder()
                        .discountAmount(userOrderCashesDto.getRetainDeductionAmount())
                        .couponName(EnumPlatformType.WELFARE_OPE.getDescription())
                        .platform(EnumPlatformType.WELFARE_OPE)
                        .uid(userOrders.getUid()).build());
                userOrderCashesDto.setPlatformCouponReduction(platformCouponReduction.add(userOrderCashesDto.getRetainDeductionAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            userOrderCouponDtos.addAll(liteUserCouponService.getUserOrderCouponByOrderId(orderId, isRecall));
        }
        userOrderCashesDto.setUserOrderCouponDtos(userOrderCouponDtos);
        //组装发货物流，归还物流信息
        this.assemblyOrderExpressInfo(orderDetailDto, userOrders, null != orderAddressDto ? orderAddressDto.getTelephone() : null);
        //组装结算信息
        orderDetailDto.setSettlementInfoDto(this.assemblyOrderSettlementInfo(userOrders.getOrderId()));
        //组装增值服务信息
        orderDetailDto.setOrderAdditionalServicesList(this.assemblyAdditionalService(userOrders));
        //组装商品信息
        orderDetailDto.setProductInfo(assemblyOrderProductInfo(userOrders));
        //组装订单信息
        orderDetailDto.setUserOrderInfoDto(assemblyOrderInfo(userOrders));
        //组装商家信息
        orderDetailDto.setShopInfoDto(assemblyOrderShopInfo(userOrders));
        orderDetailDto.setUserOrderCashesDto(userOrderCashesDto);
        //买断信息
        orderDetailDto.setOrderBuyOutDto(userOrderBuyOutDto);
        //用户下单当前位置
        orderDetailDto.setOrderLocationAddress(orderLocationAddressService.getOrderLocationAddress(orderId));
        //封装协议地址
        OrderContract orderContract = orderContractDao.getByOrderId(orderId);
        orderDetailDto.setContractUrl(orderContract == null ? "" : orderContract.getSignedPdf());
        AntChainStep step = antChainStepDao.getByOrderId(orderId);
        orderDetailDto.setAntChainInfo(AntChainConverter.model2Dto(step));
        orderDetailDto.setUserEmergencyContactList(userEmergencyContactService.getUserEmergencyContacts(userOrders.getUid(), null));
        return orderDetailDto;
    }

    /**
     * 组装买断信息
     *
     * @param userOrders
     * @param totalRent
     * @param payedRent
     * @param orderByStages
     * @return
     */
    private BackstageOrderBuyOutDto assemblyOrderBuyOutInfo(UserOrders userOrders, BigDecimal totalRent,
                                                            BigDecimal payedRent, List<OrderByStages> orderByStages) {
        BackstageOrderBuyOutDto backstageOrderBuyOutDto = new BackstageOrderBuyOutDto();
        UserOrderBuyOut userOrderBuyOut = userOrderBuyOutDao.selectOneByOrderIdAndStatus(userOrders.getOrderId(), EnumOrderBuyOutStatus.FINISH);
        if (null != userOrderBuyOut) {
            backstageOrderBuyOutDto.setOrderId(userOrderBuyOut.getOrderId());
            backstageOrderBuyOutDto.setPaidRent(payedRent);
            backstageOrderBuyOutDto.setPayFlag(true);
            backstageOrderBuyOutDto.setBuyOutAmount(userOrderBuyOut.getEndFund());
        } else if (EnumOrderStatus.RENTING.equals(userOrders.getStatus()) || EnumOrderStatus.TO_GIVE_BACK.equals(
                userOrders.getStatus())) {
            //待归还或租用中状态可以进行买断操作
            OrderProductDetailDto detailDto = userOrdersQueryService.selectOrderProductDetail(Collections.singletonList(userOrders.getOrderId())).get(userOrders.getOrderId());
            if (ProductStatus.IS_BUY_OUT.getCode().equals(detailDto.getBuyOutSupport()) || ProductStatus.IS_BUY_OUT_MATURE.getCode().equals(detailDto.getBuyOutSupport())) {
                //买断尾款
                BigDecimal endFund = orderRepayPlanFactory.calculateBuyOutAmount(userOrders, orderByStages, detailDto.getSalePrice());
                backstageOrderBuyOutDto.setOrderId(userOrders.getOrderId());
                backstageOrderBuyOutDto.setPaidRent(payedRent);
                backstageOrderBuyOutDto.setPayFlag(false);
                BigDecimal dueBuyOutAmount = detailDto.getSalePrice().subtract(totalRent);
                backstageOrderBuyOutDto.setDueBuyOutAmount(dueBuyOutAmount.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : dueBuyOutAmount);
                backstageOrderBuyOutDto.setCurrentBuyOutAmount(endFund);
            }
        }
        return backstageOrderBuyOutDto;
    }

    /**
     * 组装结算信息
     *
     * @param orderId
     */
    private OrderSettlementInfoDto assemblyOrderSettlementInfo(String orderId) {
        OrderSettlement orderSettlement = orderSettlementDao.selectOneByOrderId(orderId);
        if (null != orderSettlement) {
            OrderSettlementInfoDto settlementInfoDto = new OrderSettlementInfoDto();
            settlementInfoDto.setOrderId(orderSettlement.getOrderId());
            settlementInfoDto.setSettlementStatus(orderSettlement.getSettlementStatus());
            settlementInfoDto.setSettlementType(orderSettlement.getSettlementType());
            settlementInfoDto.setAmount(
                    AmountUtil.safeAdd(orderSettlement.getDamageAmount(), orderSettlement.getLoseAmount(),
                            orderSettlement.getPenaltyAmount()));
            settlementInfoDto.setPaymentTime(orderSettlement.getPaymentTime());
            return settlementInfoDto;
        }
        return null;
    }

    public List<OrderAdditionalServicesDto> assemblyAdditionalService(UserOrders userOrders) {
        //增值服务
        List<OrderAdditionalServices> list = orderAdditionalServicesDao.queryRecordByOrderId(
                userOrders.getOrderId());
        List<OrderAdditionalServicesDto> additionalServicesDtos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            //暂时使用第一个需要前端配合修改  todo
            list.forEach(item -> {
                OrderAdditionalServicesDto additionalServicesDto = new OrderAdditionalServicesDto();
                additionalServicesDto.setOrderId(item.getOrderId());
                additionalServicesDto.setShopAdditionalServicesId(item.getShopAdditionalServicesId());
                additionalServicesDto.setPrice(item.getPrice());
                if (item.getShopAdditionalServicesId() == -1) {
                    additionalServicesDto.setShopAdditionalServicesName("基础服务费");
                } else {
                    ProductAdditionalServicesDto productAdditionalServicesDto = productAdditionalServicesService.queryProductAdditionalServicesByProductId(userOrders.getProductId())
                            .stream()
                            .filter(a -> a.getShopAdditionalServicesId().equals(item.getShopAdditionalServicesId()))
                            .findFirst()
                            .orElse(null);
                    additionalServicesDto.setShopAdditionalServicesName(null != productAdditionalServicesDto ?
                            productAdditionalServicesDto.getShopAdditionalServices()
                                    .getName() : "");
                }
                additionalServicesDtos.add(additionalServicesDto);
            });

        }
        return additionalServicesDtos;
    }

    @Override
    public Page<OpeUserOrderClosingDto> queryPendingOrderClosureList(CloseOrderByConditionRequest request) {
        OrderCheckUtil.checkQueryDate(request.getCreateTimeStart(), request.getCreateTimeEnd(),
                EnumOrderError.ORDER_CREATE_START_END_ALL);
        if (Objects.isNull(request.getStatus())) {
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_NOT_ALLOW_NULL.getCode(),
                    EnumOrderError.ORDER_STATUS_NOT_ALLOW_NULL.getMsg(), this.getClass());
        }

        String orderId = request.getOrderId();
        Page<OpeUserOrderClosingDto> emptyPage = new Page<>(request.getPageNumber(), request.getPageSize());
        final String shopName = request.getShopName();
        List<String> shopNameShopIds = null;
        if (StringUtils.isNotEmpty(shopName)) {
            shopNameShopIds = shopService.getShopIdListLikeName(shopName);
            if (CollectionUtil.isEmpty(shopNameShopIds)) {
                return emptyPage;
            }
        }
        // 下单人手机号搜索
        final String telephone = request.getTelephone();
        List<String> telephoneUidList = null;
        if (StringUtils.isNotBlank(telephone) && CollectionUtil.isEmpty(
                telephoneUidList = userDao.queryUidListByTelephone(telephone))) {
            return emptyPage;
        }

        // 产品名称搜索
        List<String> productIdList = null;
        if (StringUtils.isNotBlank(request.getProductName())) {
            List<ProductDto> productDtoList = productService.selectProductByName(request.getProductName());
            if (CollectionUtil.isEmpty(productDtoList)) {
                return emptyPage;
            }
            productIdList = productDtoList.stream().map(ProductDto::getProductId).collect(toList());
        }

        boolean createTimeSear = Objects.nonNull(request.getCreateTimeStart()) && Objects.nonNull(
                request.getCreateTimeEnd());
        Page<UserOrders> userOrdersPage = this.userOrdersDao.page(
                new Page<>(request.getPageNumber(), request.getPageSize()), new QueryWrapper<>(new UserOrders()).select(
                                "id", "create_time", "channel_id", "order_id", "status", "rent_start", "uid", "shop_id",
                                "original_order_id", "product_id", "user_name", "examine_status")
                        .eq(StringUtils.isNotEmpty(orderId), "order_id", orderId)
                        .like(StringUtil.isNotEmpty(request.getUserName()), "user_name", request.getUserName())
                        .in(CollectionUtil.isNotEmpty(telephoneUidList), "uid", telephoneUidList)
                        .in(CollectionUtil.isNotEmpty(productIdList), "product_id", productIdList)
                        .eq(Objects.nonNull(request.getStatus()), "`status`", request.getStatus())
                        .eq(Objects.nonNull(request.getStatus()) && EnumOrderStatus.CLOSED.equals(request.getStatus()),
                                "close_type", EnumOrderCloseType.PLATFORM_CLOSE)
                        .in(CollectionUtil.isNotEmpty(shopNameShopIds), "shop_id", shopNameShopIds)
                        .between(createTimeSear, "create_time", DateUtil.getDayBegin(request.getCreateTimeStart()),
                                DateUtil.getDayEnd(request.getCreateTimeEnd()))
                        .eq("type", EnumOrderType.GENERAL_ORDER)
                        .eq(StringUtils.isNotEmpty(request.getChannelId()), "channel_id", request.getChannelId())
                        .isNull("delete_time")
                        .orderBy(true, false, "id"));
        if (CollectionUtil.isEmpty(userOrdersPage.getRecords())) {
            return emptyPage;
        }
        List<String> orderIdList = Lists.newArrayList();
        List<String> uidList = Lists.newArrayList();
        List<String> orderProductIdList = Lists.newArrayList();
        List<String> shopIdList = Lists.newArrayList();
        userOrdersPage.getRecords()
                .forEach(userOrders -> {
                    orderIdList.add(userOrders.getOrderId());
                    uidList.add(userOrders.getUid());
                    orderProductIdList.add(userOrders.getProductId());
                    shopIdList.add(userOrders.getShopId());
                });
        //查询外部信息
        //账单信息
        Map<String, List<OrderByStages>> stagesByOrderMap = orderByStagesDao.queryOrderByStagesByOrders(orderIdList);
        //店铺信息列表
        Map<String, ShopDto> shopDtoMap = shopService.selectShopInfoByShopId(new ArrayList<>(shopIdList));
        //商品信息
        Map<String, String> productNameMap = productService.getProductName(new ArrayList<>(orderProductIdList));
        Map<String, UserOrderCashes> userOrderCashesMap = userOrderCashesDao.queryListByOrderIds(orderIdList);
        List<OpeUserOrderClosingDto> opeUserOrderClosingDtoList = Lists.newArrayList();
        for (UserOrders userOrders : userOrdersPage.getRecords()) {
            OpeUserOrderClosingDto opeUserOrderClosingDto = new OpeUserOrderClosingDto();
            opeUserOrderClosingDto.setOrderId(userOrders.getOrderId());
            opeUserOrderClosingDto.setShopName(shopDtoMap.get(userOrders.getShopId()).getName());
            opeUserOrderClosingDto.setChannelName(platformChannelService.getPlatFormChannel(userOrders.getChannelId()).getChannelName());
            opeUserOrderClosingDto.setProductName(productNameMap.get(userOrders.getProductId()));
            List<OrderByStages> stages = stagesByOrderMap.get(userOrders.getOrderId());
            BigDecimal payedRent = BigDecimal.ZERO;
            int payedPeriods = 0;
            if (CollectionUtil.isNotEmpty(stages)) {
                for (OrderByStages stage : stages) {
                    opeUserOrderClosingDto.setTotalPeriods(stage.getTotalPeriods());
                    opeUserOrderClosingDto.setTotalRentAmount(stage.getTotalRent());
                    if (EnumOrderByStagesStatus.OVERDUE_PAYED.equals(stage.getStatus())
                            || EnumOrderByStagesStatus.PAYED.equals(stage.getStatus())) {
                        payedRent = AmountUtil.safeAdd(payedRent, stage.getCurrentPeriodsRent());
                        payedPeriods++;
                    }
                }
            }
            opeUserOrderClosingDto.setPayedPeriods(payedPeriods);
            opeUserOrderClosingDto.setPayedRentAmount(payedRent);
            opeUserOrderClosingDto.setDeposit(userOrderCashesMap.get(userOrders.getOrderId()).getDeposit());
            opeUserOrderClosingDto.setRefundAmount(userOrderCashesMap.get(userOrders.getOrderId()).getFreezePrice());
            opeUserOrderClosingDto.setRefundStatus(EnumOrderStatus.CLOSED.equals(userOrders.getStatus()));
            opeUserOrderClosingDto.setPlaceOrderTime(userOrders.getCreateTime());
            opeUserOrderClosingDto.setStatus(userOrders.getStatus());
            opeUserOrderClosingDto.setCloseType(userOrders.getCloseType());
            opeUserOrderClosingDto.setCancelReason(userOrders.getCancelReason());
            opeUserOrderClosingDtoList.add(opeUserOrderClosingDto);
        }
        Page<OpeUserOrderClosingDto> page = new Page<>(userOrdersPage.getCurrent(), userOrdersPage.getSize(),
                userOrdersPage.getTotal());
        page.setRecords(opeUserOrderClosingDtoList);
        return page;
    }

    @Override
    public OpeOrderProductInfo assemblyOrderProductInfo(UserOrders userOrders) {
        //查询产品信息
        OrderProductDetailDto productDetailDto = userOrdersQueryService.selectOrderProductDetail(
                        Collections.singletonList(userOrders.getOrderId()))
                .get(userOrders.getOrderId());

        OpeOrderProductInfo productInfo = new OpeOrderProductInfo();
        productInfo.setProductId(productDetailDto.getProductId());
        productInfo.setProductName(productDetailDto.getProductName());
        productInfo.setImageUrl(productDetailDto.getMainImageUrl());
        productInfo.setSpec(productDetailDto.getSkuTitle());
        productInfo.setNum(1);
        productInfo.setId(productDetailDto.getId());
        Integer buyOutSupport = productDetailDto.getBuyOutSupport();
        productInfo.setBuyOutSupportV1(null == buyOutSupport ? ProductStatus.IS_NOT_BUY_OUT.getCode() : buyOutSupport);
        if (null == buyOutSupport || ProductStatus.IS_NOT_BUY_OUT.getCode().equals(buyOutSupport)) {
            productInfo.setBuyOutSupport(Boolean.FALSE);
        } else {
            productInfo.setBuyOutSupport(Boolean.TRUE);
        }
        productInfo.setMarketPrice(productDetailDto.getMarketPrice());
        productInfo.setSalePrice(productDetailDto.getSalePrice());
        return productInfo;
    }

    /**
     * 组装物流信息
     *
     * @param orderDetailDto
     * @param userOrders
     * @param receiptTelephone
     */
    private void assemblyOrderExpressInfo(BackstageUserOrderDetailDto orderDetailDto, UserOrders userOrders,
                                          String receiptTelephone) {
        //收货物流信息
        if (StringUtil.isNotEmpty(userOrders.getExpressNo()) && null != userOrders.getExpressId()) {
            OpeExpressInfo receiptExpressInfo = new OpeExpressInfo();
            PlatformExpress platformExpress = platformExpressService.queryPlatformExpressDetailById(userOrders.getExpressId());
            receiptExpressInfo.setExpressCompany(platformExpress.getName());
            receiptExpressInfo.setExpressId(Long.valueOf(userOrders.getExpressId()));
            receiptExpressInfo.setExpressNo(userOrders.getExpressNo());
            receiptExpressInfo.setShortName(platformExpress.getShortName());
            receiptExpressInfo.setDeliveryTime(userOrders.getDeliveryTime());
            receiptExpressInfo.setReceiverPhone(receiptTelephone);
            orderDetailDto.setReceiptExpressInfo(receiptExpressInfo);
        }
        //归还物流信息
        if (null != userOrders.getUnrentExpressId() && StringUtil.isNotEmpty(userOrders.getUnrentExpressNo())) {
            OpeExpressInfo giveBackExpressInfo = new OpeExpressInfo();
            if (null != userOrders.getGiveBackAddressId()) {
                ShopGiveBackAddressesReqDto request = new ShopGiveBackAddressesReqDto();
                request.setId(userOrders.getGiveBackAddressId().intValue());
                ShopGiveBackAddressesDto addressesDto = shopGiveBackAddressesService.queryShopGiveBackAddressesDetail(request);
                if (null != addressesDto) {
                    giveBackExpressInfo.setReceiverPhone(addressesDto.getTelephone());
                }
            }
            PlatformExpress platformExpress = platformExpressService.queryPlatformExpressDetailById(userOrders.getUnrentExpressId());
            if (null != platformExpress) {
                giveBackExpressInfo.setExpressCompany(platformExpress.getName());
                giveBackExpressInfo.setShortName(platformExpress.getShortName());
            }
            giveBackExpressInfo.setExpressId(userOrders.getUnrentExpressId());
            giveBackExpressInfo.setExpressNo(userOrders.getUnrentExpressNo());
            giveBackExpressInfo.setDeliveryTime(userOrders.getReturnTime());
            orderDetailDto.setGiveBackExpressInfo(giveBackExpressInfo);
        }
    }

    @Override
    public OpeShopInfoDto assemblyOrderShopInfo(UserOrders userOrders) {
        ShopDto shopDto = shopService.queryByShopId(userOrders.getShopId());
        OpeShopInfoDto shopInfoDto = new OpeShopInfoDto();
        shopInfoDto.setShopId(shopDto.getShopId());
        shopInfoDto.setShopName(shopDto.getName());
        shopInfoDto.setTelephone(shopDto.getServiceTel());
        return shopInfoDto;
    }

    @Override
    public OpeUserOrderInfoDto assemblyOrderInfo(UserOrders userOrders) {
        UserCertificationDto userCertificationDto = userCertificationService.getByUid(userOrders.getUid());
        //查询用户在途订单，完结订单
        int finishOrderCount = 0;
        int processingOrderCount = 0;
        List<UserOrders> userOrdersList = userOrdersDao.queryUserOrdersByUid(userOrders.getUid(), null);
        if (CollectionUtil.isNotEmpty(userOrdersList)) {
            finishOrderCount = (int) userOrdersList.stream()
                    .filter(a -> EnumOrderStatus.FINISH.equals(a.getStatus()))
                    .count();
            processingOrderCount = (int) userOrdersList.stream()
                    .filter(a -> PROCESSING_STATUS_LIST.contains(a.getStatus()
                            .getCode()))
                    .count();
        }

        OrderPayDeposit orderPayDeposit = orderPayDepositDao.getByOrderId(userOrders.getOrderId());
        BigDecimal paidDeposit = null;
        if (orderPayDeposit != null && orderPayDeposit.getStatus().equals(EnumOrderPayDepositStatus.PAID)) {
            paidDeposit = orderPayDeposit.getAmount();
        }
        OrderDetailResponse orderDetailResponse = userOrdersQueryService.selectUserOrderDetail(userOrders.getOrderId());
        UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(userOrders.getOrderId());

        OpeUserOrderInfoDto userOrderInfoDto = new OpeUserOrderInfoDto();
        userOrderInfoDto.setMarketPrice(orderDetailResponse.getOrderProductDetailDto().getMarketPrice());
        userOrderInfoDto.setSalePrice(userOrderItems.getSalePrice());
        userOrderInfoDto.setExpireBuyOutPrice(orderDetailResponse.getUserOrdersDto().getExpireBuyOutPrice());

        userOrderInfoDto.setPaidDeposit(paidDeposit);
        userOrderInfoDto.setUid(userOrders.getUid());
        userOrderInfoDto.setCreateTime(userOrders.getCreateTime());
        userOrderInfoDto.setPaymentTime(userOrders.getPaymentTime());
        userOrderInfoDto.setOrderId(userOrders.getOrderId());
        userOrderInfoDto.setStatus(userOrders.getStatus());
        userOrderInfoDto.setCloseType(userOrders.getCloseType());
        userOrderInfoDto.setRentDuration(userOrders.getRentDuration());
        userOrderInfoDto.setRentStart(userOrders.getRentStart());
        userOrderInfoDto.setUnrentTime(userOrders.getUnrentTime());
        userOrderInfoDto.setIsViolation(userOrders.getIsViolation());
        userOrderInfoDto.setRemark(userOrders.getRemark());
        userOrderInfoDto.setOrderRemark(userOrders.getOrderRemark());
        userOrderInfoDto.setType(userOrders.getType());
        userOrderInfoDto.setShopId(userOrders.getShopId());
        userOrderInfoDto.setChannelId(userOrders.getChannelId());
        userOrderInfoDto.setProductId(userOrders.getProductId());
        userOrderInfoDto.setRealName(userOrders.getUserName());
        userOrderInfoDto.setCyclePaySigned(Boolean.FALSE);
        userOrderInfoDto.setNums(1);
        userOrderInfoDto.setUserFinishCount(finishOrderCount);
        userOrderInfoDto.setUserPayCount(processingOrderCount);
        if (userCertificationDto == null) {
            userOrderInfoDto.setUserIdCardPhotoCertStatus(Boolean.FALSE);
        } else {
            userOrderInfoDto.setUserName(userCertificationDto.getUserName());
            userOrderInfoDto.setTelephone(userCertificationDto.getTelephone());
            userOrderInfoDto.setIdCard(userCertificationDto.getIdCard());
            userOrderInfoDto.setIdCardBackUrl(userCertificationDto.getIdCardBackUrl());
            userOrderInfoDto.setIdCardFrontUrl(userCertificationDto.getIdCardFrontUrl());
            userOrderInfoDto.setUserIdCardPhotoCertStatus(
                    StringUtil.isNotEmpty(userCertificationDto.getIdCardBackUrl()) && StringUtil.isNotEmpty(
                            userCertificationDto.getIdCardFrontUrl()));
            userOrderInfoDto.setAge(IdcardUtil.getAgeByIdCard(userCertificationDto.getIdCard()));
            userOrderInfoDto.setGender(IdcardUtil.getGenderByIdCard(userCertificationDto.getIdCard()) == 1 ? "男" : "女");
        }
        userOrderInfoDto.setUserFaceCertStatus(EnumOrderFaceStatus.AUTHED.getCode().equals(userOrders.getFaceAuthStatus()));
        userOrderInfoDto.setChannelName(platformChannelService.getPlatFormChannel(userOrders.getChannelId()).getChannelName());
        userOrderInfoDto.setCancelReason(userOrders.getCancelReason());
        userOrderInfoDto.setExamineStatus(userOrders.getExamineStatus());
        userOrderInfoDto.setAuditLabel(userOrders.getAuditLabel());
        return userOrderInfoDto;
    }

    @Override
    public Page<OpeBuyOutOrdersDto> queryOpeBuyOutOrderList(QueryBuyOutOrdersRequest model) {
        Integer pageSize = model.getPageSize();
        Page<OpeBuyOutOrdersDto> emptyPage = new Page<>(1, pageSize);

        final Date createTimeStart = model.getCreateTimeStart();
        final Date createTimeEnd = model.getCreateTimeEnd();
        final String productName = model.getProductName();
        final String orderId = model.getOrderId();
        final String buyOutOrderId = model.getBuyOutOrderId();
        final String state = model.getStatus();

        // 下单人姓名
        List<String> userNameUidList = null;
        final String userName = model.getUserName();
        if (StringUtils.isNotBlank(userName) && CollectionUtil.isEmpty(
                userNameUidList = userOrdersDao.queryUidByUserName(userName))) {
            return emptyPage;
        }

        // 下单人手机号搜索
        final String telephone = model.getTelephone();
        List<String> telephoneUidList = null;
        if (StringUtils.isNotBlank(telephone) && CollectionUtil.isEmpty(
                telephoneUidList = userDao.queryUidListByTelephone(telephone))) {
            return emptyPage;
        }

        // 商品名称搜索
        List<String> productNameOrderIds = null;
        if (StringUtils.isNotBlank(model.getProductName())) {
            List<ProductDto> productDtoList = productService.selectProductByName(productName);
            if (CollectionUtil.isEmpty(productDtoList)) {
                return emptyPage;
            }
            productNameOrderIds = productDtoList.stream().map(ProductDto::getProductId).collect(toList());
        }
        boolean createTimeSear = Objects.nonNull(createTimeStart) && Objects.nonNull(createTimeEnd);
        Page<UserOrderBuyOut> orderBuyOutPage = userOrderBuyOutDao.page(
                new Page<>(model.getPageNumber(), model.getPageSize()), new QueryWrapper<>(new UserOrderBuyOut()).eq(
                                StringUtil.isNotEmpty(buyOutOrderId), "buy_out_order_id", buyOutOrderId)
                        .eq(StringUtil.isNotEmpty(orderId), "order_id", orderId)
                        .eq(StringUtil.isNotEmpty(model.getShopId()), "shop_id", model.getShopId())
                        .eq(StringUtil.isNotEmpty(state), "state", state)
                        .eq(StringUtil.isNotEmpty(model.getChannelId()), "channel_id", model.getChannelId())
                        .in(CollectionUtil.isNotEmpty(userNameUidList), "uid", userNameUidList)
                        .in(CollectionUtil.isNotEmpty(telephoneUidList), "uid", telephoneUidList)
                        .in(CollectionUtil.isNotEmpty(productNameOrderIds), "uid", productNameOrderIds)
                        .between(createTimeSear, "create_time", DateUtil.getDayBegin(createTimeStart), DateUtil.getDayEnd(createTimeEnd))
                        .orderBy(true, false, "id"));
        if (CollectionUtil.isEmpty(orderBuyOutPage.getRecords())) {
            return emptyPage;
        }
        List<String> orderIdList = orderBuyOutPage.getRecords().stream().map(UserOrderBuyOut::getOrderId).collect(toList());
        List<String> uidList = orderBuyOutPage.getRecords().stream().map(UserOrderBuyOut::getUid).collect(toList());
        Map<String, UserCertificationDto> userCertificationDtoMap = userCertificationService.queryUserCertificationList(uidList);
        Map<String, UserOrderItems> userOrderItemsMap = userOrderItemsDao.queryListByOrderIds(orderIdList).stream()
                .collect(Collectors.toMap(UserOrderItems::getOrderId, Function.identity()));

        List<OpeBuyOutOrdersDto> buyOutOrdersDtos = Lists.newArrayList();
        orderBuyOutPage.getRecords()
                .forEach(userOrderBuyOut -> {
                    if (null != userOrderBuyOut.getOrderId()) {
                        OpeBuyOutOrdersDto buyOutOrdersDto = new OpeBuyOutOrdersDto();
                        buyOutOrdersDto.setBuyOutOrderId(userOrderBuyOut.getBuyOutOrderId());
                        buyOutOrdersDto.setProductName(userOrderItemsMap.get(userOrderBuyOut.getOrderId()).getProductName());
                        buyOutOrdersDto.setOrderId(userOrderBuyOut.getOrderId());
                        buyOutOrdersDto.setCreateTime(userOrderBuyOut.getCreateTime());
                        UserCertificationDto userCert;
                        if (null != (userCert = userCertificationDtoMap.get(userOrderBuyOut.getUid()))) {
                            buyOutOrdersDto.setUserName(userCert.getUserName());
                            buyOutOrdersDto.setTelephone(userCert.getTelephone());
                        }
                        buyOutOrdersDto.setState(userOrderBuyOut.getState()
                                .getCode());
                        buyOutOrdersDto.setUid(userOrderBuyOut.getUid());
                        buyOutOrdersDto.setUserIdCardPhotoCertStatus(true);
                        buyOutOrdersDto.setUserFaceCertStatus(true);
                        buyOutOrdersDto.setChannelId(userOrderBuyOut.getChannelId());
                        buyOutOrdersDto.setChannelName(platformChannelService.getPlatFormChannel(userOrderBuyOut.getChannelId()).getChannelName());
                        buyOutOrdersDtos.add(buyOutOrdersDto);
                    }
                });
        Page<OpeBuyOutOrdersDto> result = new Page<>(orderBuyOutPage.getCurrent(), orderBuyOutPage.getSize(),
                orderBuyOutPage.getTotal());
        result.setRecords(buyOutOrdersDtos);
        return result;
    }

    @Override
    public BuyOutOriginalOrderDto assemblyBuyOutOriginalOrderDto(UserOrders userOrders) {
        //商品信息
        OrderProductDetailDto productDetailDto = userOrdersQueryService.selectOrderProductDetail(
                        Collections.singletonList(userOrders.getOrderId()))
                .get(userOrders.getOrderId());
        BuyOutOriginalOrderDto originalOrderDto = new BuyOutOriginalOrderDto();
        originalOrderDto.setImageUrl(productDetailDto.getMainImageUrl());
        originalOrderDto.setProductId(productDetailDto.getProductId());
        originalOrderDto.setProductName(productDetailDto.getProductName());
        originalOrderDto.setSpec(productDetailDto.getSkuTitle());
        originalOrderDto.setStatus(userOrders.getStatus());
        //查询订单账单信息
        List<OrderByStagesDto> orderByStagesDtoList = OrderByStagesConverter.modelList2DtoList(
                orderByStagesDao.queryOrderByOrderId(userOrders.getOrderId()));
        if (CollectionUtil.isNotEmpty(orderByStagesDtoList)) {
            BigDecimal payedRent = orderByStagesDtoList.stream()
                    .filter(a -> EnumOrderByStagesStatus.PAYED.equals(a.getStatus())
                            || EnumOrderByStagesStatus.OVERDUE_PAYED.equals(a.getStatus()))
                    .map(OrderByStagesDto::getCurrentPeriodsRent)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            originalOrderDto.setTotalRent(orderByStagesDtoList.get(0)
                    .getTotalRent());
            originalOrderDto.setPayedRent(payedRent);
        }
        return originalOrderDto;
    }

    @Override
    public BackstageOrderStagesDto queryOrderStagesDetail(String orderId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        //查询订单金额信息
        UserOrderCashesDto userOrderCashesDto = UserOrderCashesConverter.model2Dto(userOrderCashesDao.selectOneByOrderId(orderId));
        //查询订单账单信息
        List<OrderByStages> orderByStages = orderByStagesDao.queryOrderByOrderId(orderId);
        //查询信用减免金额
        AlipayFreeze alipayFreeze = alipayFreezeDao.selectOneByOrderId(orderId, EnumAliPayStatus.SUCCESS);
        if (null != alipayFreeze) {
            userOrderCashesDto.setCreditDeposit(alipayFreeze.getCreditAmount());
        }
        BigDecimal platformCouponReduction = null != userOrderCashesDto.getPlatformCouponReduction() ? userOrderCashesDto.getPlatformCouponReduction() : BigDecimal.ZERO;
        if (null != userOrderCashesDto && null != userOrderCashesDto.getCouponRecallReduction()) {
            //加了订单唤回的叠加优惠券，后台管理显示时都算在平台优惠券里面
            userOrderCashesDto.setPlatformCouponReduction(platformCouponReduction.add(userOrderCashesDto.getCouponRecallReduction()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (null != userOrderCashesDto && null != userOrderCashesDto.getRetainDeductionAmount()) {
            userOrderCashesDto.setPlatformCouponReduction(platformCouponReduction.add(userOrderCashesDto.getRetainDeductionAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        BackstageOrderStagesDto orderStagesDto = new BackstageOrderStagesDto();
        orderStagesDto.setOrderId(orderId);
        orderStagesDto.setUserOrderCashesDto(userOrderCashesDto);
        orderStagesDto.setOrderByStagesDtoList(OrderByStagesConverter.modelList2DtoList(orderByStages));
        //组装结算信息
        orderStagesDto.setSettlementInfoDto(this.assemblyOrderSettlementInfo(userOrders.getOrderId()));
        //组装增值服务信息
        orderStagesDto.setOrderAdditionalServicesList(this.assemblyAdditionalService(userOrders));
        //组装买断信息
        BigDecimal totolRent = userOrderCashesDto.getTotalRent();
        if (orderId.contains(EnumSerialModalName.RELET_ORDER_ID.getCode())) {
            orderByStages = orderByStagesDao.queryAllOrderByOrderId(orderId);
            totolRent = userOrdersDao.getOrderTotolRent(orderId);
        }

        //已付租金
        BigDecimal repayRentAmount = orderByStages.stream()
                .filter(a -> a.getStatus().getPaid())
                .map(b -> ObjectUtils.defaultIfNull(b.getCurrentPeriodsRent(), BigDecimal.ZERO))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        orderStagesDto.setOrderBuyOutDto(assemblyOrderBuyOutInfo(userOrders, totolRent, repayRentAmount, orderByStages));
        return orderStagesDto;
    }

    @Override
    public StageOrderWithholdResponse stageOrderWithhold(StageOrderWithholdRequest request) {
        if (request.getDeductionMethodType().equals(DeductionMethodType.AUTH)) {
            return this.stageOrderWithholdByAlipay(request);
        } else if (request.getDeductionMethodType().equals(DeductionMethodType.SUNING_BANK)) {
            return this.stageOrderWithholdByBank(request);
        } else {
            StageOrderWithholdResponse result = new StageOrderWithholdResponse();
            result.setRespMsg("未实现的代扣方式");
            result.setOrderId(false);
            return result;
        }
    }

    @Override
    public StageOrderWithholdResponse stageOrderWithholdByAlipay(StageOrderWithholdRequest request) {
        Date now = new Date();
        StageOrderWithholdResponse result = new StageOrderWithholdResponse();
        String orderId = request.getOrderId();
        Integer currentPeriods = request.getCurrentPeriods();
        Boolean isFirstPeriod = null != currentPeriods ? currentPeriods.equals(1) : Boolean.FALSE;


        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if (null == userOrders) {
            throw new HzsxBizException("-1", "未查询到主订单" + orderId);
        }
        if (!EnumOrderStatus.WITHHOLD_SET.contains(userOrders.getStatus())) {
            throw new HzsxBizException("-1", "当前订单状态不允许代扣" + orderId);
        }
        if (StringUtil.isEmpty(userOrders.getRequestNo())) {
            throw new HzsxBizException("-1", "当前订单状态不允许代扣" + orderId);
        }
        OrderByStages orderByStages = orderByStagesDao.queryOrderByOrderIdAndPeriod(orderId, currentPeriods);
        if (null == orderByStages) {
            throw new HzsxBizException("-1", "未查询到当前分期订单" + orderId + "-" + currentPeriods);
        }
        if (!isFirstPeriod) {
            //判断第一期是否扣款成功
            OrderByStages firstOrderByStages = orderByStagesDao.queryOrderByOrderIdAndPeriod(orderId, 1);
            if (!EnumOrderByStagesStatus.PAY_SET.contains(firstOrderByStages.getStatus())) {
                throw new HzsxBizException("-1", "先完成第一期账单的代扣" + orderId);
            }
        }
        List<String> statusList = Arrays.asList(EnumOrderByStagesStatus.UN_PAY.getCode(), EnumOrderByStagesStatus.OVERDUE_NOT_PAY.getCode());
        if (!statusList.contains(orderByStages.getStatus().getCode())) {
            throw new HzsxBizException("-1", "当前分期订单不允许代扣" + orderId + "-" + currentPeriods);
        }
        BigDecimal currentPeriodsRent = orderByStages.getCurrentPeriodsRent();
        if (currentPeriodsRent.compareTo(BigDecimal.ZERO) < 1) {
            throw new HzsxBizException("-1", "代扣金额需要大于0");
        }
        //防止订单重复提交的判断
        String jedisOrderIdKey = "stageOrderWithhold:" + orderId + currentPeriods;
        Object jedisOrderIdRslt = RedisUtil.get(jedisOrderIdKey);
        if (jedisOrderIdRslt != null) {
            throw new HzsxBizException("-1", "代扣操作正在进行，请勿重复操作丫~");
        }
        RedisUtil.set(jedisOrderIdKey, orderId, 30);
        try {
            AliPayTradePayResponse response = aliPayCapitalService.orderByStageAliPayTradePay(orderByStages.getOrderId(),
                    "分期扣款订单:" + orderByStages.getOrderId() + "|租期:" + orderByStages.getCurrentPeriods(),
                    orderByStages.getCurrentPeriodsRent(), Collections.singletonList(orderByStages.getCurrentPeriods().toString()), EnumTradeType.BILL_WITHHOLD);
            if (response.getTradeResult().equals(EnumTradeResult.SUCCESS)) {
                result.setOrderId(true);
                result.setRespMsg("代扣成功");
            } else {
                result.setOrderId(false);
                result.setRespMsg(response.getResultMessage());
            }
        } catch (Exception e) {
            throw new HzsxBizException("-1", "扣款失败请联系相关人员查看丫~");
        } finally {
            Integer deductionTimes = Optional.ofNullable(orderByStages.getDeductionTimes()).orElse(0) + 1;
            orderByStages.setDeductionTimes(deductionTimes);
            orderByStagesDao.updateById(orderByStages);
        }
        return result;
    }

    @Override
    public StageOrderWithholdResponse stageOrderWithholdByBank(StageOrderWithholdRequest request) {
        String orderId = request.getOrderId();
        Integer currentPeriods = request.getCurrentPeriods();
        Boolean isFirstPeriod = null != currentPeriods ? currentPeriods.equals(1) : Boolean.FALSE;

        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if (null == userOrders) {
            throw new HzsxBizException("-1", "未查询到主订单" + orderId);
        }
        // 查询用户是否签约苏宁代扣
        List<UserBankCard> userBankCards = userBankCardDao.getUserBankCardByUid(userOrders.getUid());
        if (CollectionUtil.isEmpty(userBankCards)) {
            throw new HzsxBizException("-1", "用户未签约易付宝代扣");
        }
        if (!EnumOrderStatus.WITHHOLD_SET.contains(userOrders.getStatus())) {
            throw new HzsxBizException("-1", "当前订单状态不允许代扣" + orderId);
        }
        if (StringUtil.isEmpty(userOrders.getRequestNo())) {
            throw new HzsxBizException("-1", "当前订单状态不允许代扣" + orderId);
        }
        OrderByStages orderByStages = orderByStagesDao.queryOrderByOrderIdAndPeriod(orderId, currentPeriods);
        if (null == orderByStages) {
            throw new HzsxBizException("-1", "未查询到当前分期订单" + orderId + "-" + currentPeriods);
        }
        if (!isFirstPeriod) {
            //判断第一期是否扣款成功
            OrderByStages firstOrderByStages = orderByStagesDao.queryOrderByOrderIdAndPeriod(orderId, 1);
            if (!EnumOrderByStagesStatus.PAY_SET.contains(firstOrderByStages.getStatus())) {
                throw new HzsxBizException("-1", "先完成第一期账单的代扣" + orderId);
            }
        }
        List<String> statusList = Arrays.asList(EnumOrderByStagesStatus.UN_PAY.getCode(), EnumOrderByStagesStatus.OVERDUE_NOT_PAY.getCode());
        if (!statusList.contains(orderByStages.getStatus().getCode())) {
            throw new HzsxBizException("-1", "当前分期订单不允许代扣" + orderId + "-" + currentPeriods);
        }
        BigDecimal currentPeriodsRent = orderByStages.getCurrentPeriodsRent();
        if (currentPeriodsRent.compareTo(BigDecimal.ZERO) < 1) {
            throw new HzsxBizException("-1", "代扣金额需要大于0");
        }
        //防止订单重复提交的判断
        String jedisOrderIdKey = "stageOrderWithhold:" + orderId + currentPeriods;
        Object jedisOrderIdRslt = RedisUtil.get(jedisOrderIdKey);
        if (jedisOrderIdRslt != null) {
            throw new HzsxBizException("-1", "代扣操作正在进行，请勿重复操作丫~");
        }
        RedisUtil.set(jedisOrderIdKey, orderId, 30);
        StageOrderWithholdResponse result = new StageOrderWithholdResponse();
        try {
            AliPayTradePayResponse response = suningOpenApiService.payWithYfb(orderByStages.getOrderId(),
                    "分期扣款订单:" + orderByStages.getOrderId() + "|租期:" + orderByStages.getCurrentPeriods(),
                    orderByStages.getCurrentPeriodsRent(), Collections.singletonList(orderByStages.getCurrentPeriods().toString()), EnumTradeType.BILL_WITHHOLD);
            if (response.getTradeResult().equals(EnumTradeResult.SUCCESS)) {
                result.setOrderId(true);
                result.setRespMsg("代扣成功");
            } else {
                result.setOrderId(false);
                result.setRespMsg(response.getResultMessage());
            }
        } catch (Exception e) {
            throw new HzsxBizException("-1", "扣款失败请联系相关人员查看丫~");
        } finally {
            Integer deductionTimes = Optional.ofNullable(orderByStages.getDeductionTimes()).orElse(0) + 1;
            orderByStages.setDeductionTimes(deductionTimes);
            orderByStagesDao.updateById(orderByStages);
        }
        return result;
    }

    @Override
    public BackstageBuyOutOrderDetailDto queryOpeBuyOutOrderDetail(String buyOutOrderId) {
        UserOrderBuyOut userOrderBuyOut = userOrderBuyOutDao.selectOneByBuyOutOrderId(buyOutOrderId);
        if (null == userOrderBuyOut) {
            return null;
        }
        BackstageBuyOutOrderDetailDto buyOutOrderDetailDto = new BackstageBuyOutOrderDetailDto();
        buyOutOrderDetailDto.setUserOrderBuyOutDto(UserOrderBuyOutConverter.model2Dto(userOrderBuyOut));
        String orderId = userOrderBuyOut.getOrderId();
        //订单信息
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        buyOutOrderDetailDto.setOriginalOrderDto(assemblyBuyOutOriginalOrderDto(userOrders));

        //查询订单地址信息
        OrderAddressDto orderAddressDto = OrderAddressConverter.model2Dto(orderAddressDao.queryByOrderId(userOrders.getOrderId()));
        Map<String, String> distinctNameMap = districtService.getDistinctName(Arrays.asList(
                orderAddressDto.getProvince().toString(),
                orderAddressDto.getCity().toString(),
                orderAddressDto.getArea().toString()));
        orderAddressDto.setProvinceStr(distinctNameMap.get(orderAddressDto.getProvince().toString()));
        orderAddressDto.setCityStr(distinctNameMap.get(orderAddressDto.getCity().toString()));
        orderAddressDto.setAreaStr(distinctNameMap.get(orderAddressDto.getArea().toString()));
        buyOutOrderDetailDto.setOrderAddressDto(orderAddressDto);

        //查询订单备注信息
        Page<OrderRemark> orderRemarkPage = orderRemarkDao.pageByOrderId(new Page<>(1, 3), buyOutOrderId,
                EnumOrderRemarkSource.OPE);
        List<OrderRemarkDto> opeRemarkList = OrderRemarkConverter.modelList2DtoList(orderRemarkPage.getRecords());
        Page<OrderRemarkDto> opeRemarkDtoPage = new Page<>(orderRemarkPage.getCurrent(), orderRemarkPage.getSize(),
                orderRemarkPage.getTotal());
        opeRemarkDtoPage.setRecords(opeRemarkList);
        buyOutOrderDetailDto.setOpeRemarkDtoPage(opeRemarkDtoPage);
        Page<OrderRemark> orderRemarkPage1 = orderRemarkDao.pageByOrderId(new Page<>(1, 3), buyOutOrderId,
                EnumOrderRemarkSource.BUSINESS);
        List<OrderRemarkDto> businessRemarkList = OrderRemarkConverter.modelList2DtoList(orderRemarkPage1.getRecords());
        Page<OrderRemarkDto> businessRemarkDtoPage = new Page<>(orderRemarkPage1.getCurrent(),
                orderRemarkPage1.getSize(), orderRemarkPage1.getTotal());
        businessRemarkDtoPage.setRecords(businessRemarkList);
        buyOutOrderDetailDto.setBusinessRemarkDtoPage(businessRemarkDtoPage);
        return buyOutOrderDetailDto;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean orderRentChange(OrderRentChangeReqDto request) {

        // 修改订单租金
        //待支付状态,调整价格:
        //  1.修改价格前上锁,用户支付前判断订单是否正在修改价格，若正在修改价格提示稍后再试；
        //  2.修改订单表总租金、押金；
        //  修改user_order_item 中的销售价格
        //修改订单缓存表总租金押金；rent,totalRent,settlementRent,originalRent,originalTotalRent,freezePrice
        //3.修改分期表每期租金

        try {
            LoginUserBo loginUser = LoginUserUtil.getLoginUser();

            String orderId = request.getOrderId();
            BigDecimal salePrice = request.getSalePrice();
            BigDecimal cyclePrice = request.getCyclePrice();
            BigDecimal totalRent = request.getTotalRent();
            BigDecimal expireBuyOutPrice = request.getExpireBuyOutPrice();

            log.info("开始修改订单租金,操作者:{},姓名:{},订单编号:{},修改后销售价:{},总租金:{},到期买断价:{}",
                    loginUser.getId(), loginUser.getName(), orderId, salePrice, totalRent, expireBuyOutPrice
            );

            UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
            if (!userOrders.getStatus().equals(EnumOrderStatus.WAITING_PAYMENT)) {
                throw new HzsxBizException("-1", "订单状态不支持修改租金");
            }
            if (!RedisUtil.tryLock(RedisKey.ORDER_CHANGE_PRICE_LOCK_KEY + request.getOrderId(), 60)) {
                throw new HzsxBizException("-1", "订单正在改价,请稍后重试");
            }
            UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(orderId);
            UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);


            log.info("正在修改订单租金,操作者:{},姓名:{},订单编号:{},修改后销售价:{},总租金:{},到期买断价:{}," +
                            "修改前销售价:{},总租金:{}",
                    loginUser.getId(), loginUser.getName(), orderId, salePrice, totalRent, expireBuyOutPrice,
                    userOrderItems.getSalePrice(), userOrderCashes.getTotalRent()
            );
            //查询产品详情
            ConfirmOrderProductDto productDto = productService.getConfirmData(userOrderItems.getSkuId(), userOrders.getRentDuration());
            List<OrderAdditionalServices> orderAdditionalServicesList = orderAdditionalServicesDao.queryRecordByOrderId(orderId);
            Set<Long> additionalServicesIdSet = orderAdditionalServicesList.stream().map(OrderAdditionalServices::getId).collect(Collectors.toSet());
            List<ShopAdditionalServicesDto> selectedAdditionalService = new ArrayList<>();
            for (ProductAdditionalServicesDto dto : productDto.getAdditionalServices()) {
                if (additionalServicesIdSet.contains(Long.valueOf(dto.getShopAdditionalServices().getId()))) {
                    selectedAdditionalService.add(dto.getShopAdditionalServices());
                }
            }
            Boolean isBuyOutSupported = productDto.getBuyOutSupport() > 0;

            // cyclePrice 日租金
            // cycleSalePrice 销售价

            //计算账单信息
            OrderPricesDto orderPricesDto = userOrdersLiteService.createOrderRepayPlan(userOrders.getRentDuration(), selectedAdditionalService, cyclePrice,
                    salePrice, isBuyOutSupported, null, productDto.getProductSkus().getMarketPrice(),
                    productDto.getProductSkus().getDepositPrice());

            userOrderCashes.setTotalDeposit(orderPricesDto.getDepositAmount());
            userOrderCashes.setDepositReduction(orderPricesDto.getDepositReduce());
            userOrderCashes.setDeposit(orderPricesDto.getDeposit());
            userOrderCashes.setRent(orderPricesDto.getSkuPrice());

            userOrderCashes.setTotalRent(orderPricesDto.getTotalRent());
            userOrderCashes.setSettlementRent(orderPricesDto.getTotalRent());
            userOrderCashes.setTotal(BigDecimal.ZERO);
            userOrderCashes.setAdditionalServicesPrice(orderPricesDto.getAdditionalServicesPrice());

            userOrderCashes.setOriginalRent(orderPricesDto.getOriginalMonthRentPrice());
            userOrderCashes.setOriginalTotalRent(orderPricesDto.getRentPrice());
            userOrderCashes.setFreezePrice(orderPricesDto.getFreezePrice());
            userOrderItems.setSalePrice(salePrice);

            OrderByStages firstOrderByStages = orderByStagesDao.queryOrderByOrderIdAndPeriod(orderId, 1);
            firstOrderByStages.setTotalRent(orderPricesDto.getTotalRent());
            firstOrderByStages.setCurrentPeriodsRent(orderPricesDto.getFirstPeriodsPrice());
            orderByStagesDao.updateById(firstOrderByStages);
            List<OrderByStages> orderByStagesList = orderByStagesDao.getOtherPeriodList(orderId);
            orderByStagesList.forEach(orderByStages -> {
                orderByStages.setTotalRent(orderPricesDto.getTotalRent());
                orderByStages.setCurrentPeriodsRent(orderPricesDto.getOtherPeriodsPrice());
                orderByStagesDao.updateById(orderByStages);
            });

            userOrderCashesDao.updateById(userOrderCashes);
            userOrderItemsDao.updateById(userOrderItems);

            return Boolean.TRUE;
        } finally {
            RedisUtil.unLock(RedisKey.ORDER_CHANGE_PRICE_LOCK_KEY + request.getOrderId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void telephoneAuditOrder(TelephoneOrderAuditRequest request) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(request.getOrderId());
        if (Objects.isNull(userOrders) || !EnumOrderStatus.TO_AUDIT.equals(userOrders.getStatus())) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(), "该订单不存在或无需审核", this.getClass());
        }
        if (userOrders.getAuditLabel().equals(EnumOrderAuditLabel.PLATFORM_AUDIT) && !EnumOrderOperatorRole.OPE.equals(request.getOperatorRole())) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(), "该订单需要平台审核", this.getClass());
        }
        if (userOrders.getAuditLabel().equals(EnumOrderAuditLabel.BUSINESS_AUDIT) && !EnumOrderOperatorRole.BUSINESS.equals(request.getOperatorRole())) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(), "该订单需要商家审核", this.getClass());
        }
        Date now = new Date();
        userOrders.setExamineStatus(EnumOrderOperatorRole.OPE.equals(request.getOperatorRole()) ? EnumOrderExamineStatus.PLATFORM_EXAMINED : EnumOrderExamineStatus.BUSINESS_EXAMINED);
        userOrders.setStatus(EnumOrderStatus.PENDING_DEAL);
        userOrders.setUpdateTime(now);
        userOrdersDao.updateById(userOrders);
        //添加订单操作记录 add at 2020年10月27日13:58:41
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        String backStageUserId = loginUser.getId().toString();
        String operatorName = loginUser.getName();
        AsyncUtil.runAsync(() -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), EnumOrderStatus.TO_AUDIT, EnumOrderStatus.PENDING_DEAL, backStageUserId, operatorName,
                EnumOrderOperatorRole.OPE.equals(request.getOperatorRole()) ? "平台审核" : "商家审核"));

        orderAuditService.updateAuditRecord(request.getOrderId(), request.getOrderAuditStatus(), request.getRefuseType(), request.getRemark());
        if (EnumOrderOperatorRole.OPE.equals(request.getOperatorRole()) && EnumOrderAuditStatus.REFUSE.equals(request.getOrderAuditStatus())) {
            userOrdersService.payedCloseOrder(request.getOrderId(), EnumOrderCloseType.OPE_RISK_CLOSE, EnumOrderCloseType.OPE_RISK_CLOSE.getDescription());
            AsyncUtil.runAsync(() -> orderCenterService.merchantOrderSync(request.getOrderId(), OrderCenterStatus.REJECT));
        } else if (EnumOrderOperatorRole.BUSINESS.equals(request.getOperatorRole()) && EnumOrderAuditStatus.REFUSE.equals(request.getOrderAuditStatus())) {
            userOrdersService.payedCloseOrder(request.getOrderId(), EnumOrderCloseType.BUSINESS_RISK_CLOSE, EnumOrderCloseType.BUSINESS_RISK_CLOSE.getDescription());
            AsyncUtil.runAsync(() -> orderCenterService.merchantOrderSync(request.getOrderId(), OrderCenterStatus.REJECT));
        }
        if (EnumOrderAuditStatus.PASS.equals(request.getOrderAuditStatus())) {
            AsyncUtil.runAsync(() -> orderCenterService.merchantOrderSync(request.getOrderId(), OrderCenterStatus.TO_SEND_GOODS));
        }
    }

    @Override
    public OpeOrderStatisticsDto opeOrderStatistics(OpeOrderStatisticsRequest request) {
        OpeOrderStatisticsDto statisticsDto = new OpeOrderStatisticsDto();
        //查询当日订单总量
        List<UserOrders> userOrdersTotal = userOrdersDao.selectCountByDate(null, null, null, null);

        //待支付订单数量
        statisticsDto.setUnPayOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus().equals(EnumOrderStatus.WAITING_PAYMENT))
                .count());
        //租用中
        statisticsDto.setRentingOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus().equals(EnumOrderStatus.RENTING))
                .count());
        statisticsDto.setPendingOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus().equals(EnumOrderStatus.PENDING_DEAL))
                .count());
        statisticsDto.setWaitingConfirmOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus().equals(EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM))
                .count());
        statisticsDto.setWaitingSettlementOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus().equals(EnumOrderStatus.WAITING_SETTLEMENT))
                .count());
        statisticsDto.setOverDueOrderCount((int) userOrdersTotal.stream()
                .filter(a -> null != a.getIsViolation() && (a.getIsViolation()
                        .equals(EnumViolationStatus.STAGE_OVERDUE) || a.getIsViolation()
                        .equals(EnumViolationStatus.SETTLEMENT_OVERDUE)))
                .count());
        statisticsDto.setCloseOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus().equals(EnumOrderStatus.CLOSED))
                .count());
        statisticsDto.setFinishOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus()
                        .equals(EnumOrderStatus.FINISH))
                .count());
        statisticsDto.setTelephoneAuditOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus().equals(EnumOrderStatus.TO_AUDIT))
                .count());
        //查询当日订单总量
        Date now = new Date();
        List<UserOrders> userOrdersList = userOrdersDao.selectCountByDate(DateUtil.getDayBegin(now),
                DateUtil.getDayEnd(now), null, null);
        //今日总租金
        BigDecimal todayTotalOrderRent = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(userOrdersList)) {
            List<String> orderIdList = userOrdersList.stream()
                    .filter(a -> PAYED_STATUS_LIST.contains(a.getStatus()))
                    .map(UserOrders::getOrderId)
                    .collect(toList());
            statisticsDto.setTodayTotalOrderCount(orderIdList.size());
            if (CollectionUtil.isNotEmpty(orderIdList)) {
                List<UserOrderCashes> userOrderCashesList = userOrderCashesDao.queryByOrderIds(orderIdList);
                todayTotalOrderRent = userOrderCashesList.stream()
                        .map(UserOrderCashes::getTotalRent)
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO);
            }
        }
        //查询昨天统计数据
        OrderReport orderReport = orderReportDao.queryByStatisticsDate(
                Long.valueOf(DateUtil.date2String(DateUtil.addDate(now, -1), DateUtil.DATE_FORMAT_2)));
        if (null != orderReport) {
            statisticsDto.setYesterdayTotalRentAmount(orderReport.getSuccessOrderRent());
        }
        //查询近七日统计数据
        List<OrderReport> orderReportList = orderReportDao.queryByStatisticsDate(
                Long.valueOf(DateUtil.date2String(DateUtil.addDate(now, -7), DateUtil.DATE_FORMAT_2)),
                Long.valueOf(DateUtil.date2String(DateUtil.addDate(now, -1), DateUtil.DATE_FORMAT_2)));
        statisticsDto.setOrderReportDtoWeekList(OrderReportConverter.modelList2DtoList(orderReportList));
        if (CollectionUtil.isNotEmpty(orderReportList)) {
            BigDecimal historyRent = orderReportList.stream()
                    .map(OrderReport::getSuccessOrderRent)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            long historyCount = orderReportList.stream()
                    .mapToLong(OrderReport::getSuccessOrderCount)
                    .sum();
            statisticsDto.setSevenTotalOrderRentAmount(historyRent);
            statisticsDto.setWeekOrderRent(historyRent);
            statisticsDto.setWeekOrderCount((int) historyCount);
        }
        statisticsDto.setTodayTotalOrderRentAmount(todayTotalOrderRent);

        //查询上周统计数据
        List<OrderReport> lastWeekOrderReportList = orderReportDao.queryByStatisticsDate(
                Long.valueOf(DateUtil.date2String(DateUtil.addDate(now, -14), DateUtil.DATE_FORMAT_2)),
                Long.valueOf(DateUtil.date2String(DateUtil.addDate(now, -8), DateUtil.DATE_FORMAT_2)));
        if (CollectionUtil.isNotEmpty(lastWeekOrderReportList)) {
            BigDecimal lastWeekRent = lastWeekOrderReportList.stream()
                    .map(OrderReport::getSuccessOrderRent)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            long lastWeekCount = lastWeekOrderReportList.stream()
                    .mapToLong(OrderReport::getSuccessOrderCount)
                    .sum();
            statisticsDto.setLastWeekOrderRent(lastWeekRent);
            statisticsDto.setLastWeekOrderCount((int) lastWeekCount);
        }
        //查询近一月订单统计
        List<OrderReport> orderReportMonthList = orderReportDao.queryByStatisticsDate(
                Long.valueOf(DateUtil.date2String(DateUtil.addDate(now, -30), DateUtil.DATE_FORMAT_2)),
                Long.valueOf(DateUtil.date2String(DateUtil.addDate(now, -1), DateUtil.DATE_FORMAT_2)));
        statisticsDto.setOrderReportDtoMonthList(OrderReportConverter.modelList2DtoList(orderReportMonthList));
        if (CollectionUtil.isNotEmpty(orderReportMonthList)) {
            BigDecimal historyRent = orderReportMonthList.stream()
                    .map(OrderReport::getSuccessOrderRent)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            long historyCount = orderReportMonthList.stream()
                    .mapToLong(OrderReport::getSuccessOrderCount)
                    .sum();
            statisticsDto.setMonthOrderRent(historyRent);
            statisticsDto.setMonthOrderCount((int) historyCount);
        }
        //查询上月订单统计
        //查询近一月订单统计
        List<OrderReport> lastMonthOrderReportList = orderReportDao.queryByStatisticsDate(
                Long.valueOf(DateUtil.date2String(DateUtil.addDate(now, -60), DateUtil.DATE_FORMAT_2)),
                Long.valueOf(DateUtil.date2String(DateUtil.addDate(now, -31), DateUtil.DATE_FORMAT_2)));
        if (CollectionUtil.isNotEmpty(lastMonthOrderReportList)) {
            BigDecimal historyRent = lastMonthOrderReportList.stream()
                    .map(OrderReport::getSuccessOrderRent)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            long historyCount = lastMonthOrderReportList.stream()
                    .mapToLong(OrderReport::getSuccessOrderCount)
                    .sum();
            statisticsDto.setLastWeekOrderRent(historyRent);
            statisticsDto.setLastWeekOrderCount((int) historyCount);
        }
        BeanUtils.moneyNull2Zreo(statisticsDto);
        //计算比率
        float weekOrderCountRate = 1;
        if (statisticsDto.getLastWeekOrderCount() != 0) {
            weekOrderCountRate = (float) (statisticsDto.getWeekOrderCount() - statisticsDto.getLastWeekOrderCount())
                    / statisticsDto.getLastWeekOrderCount();
        }

        statisticsDto.setWeekOrderCountRate(new BigDecimal(weekOrderCountRate).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        BigDecimal weekOrderRentRate = BigDecimal.ONE;
        if (statisticsDto.getLastWeekOrderRent()
                .compareTo(BigDecimal.ZERO) != 0) {
            weekOrderRentRate = statisticsDto.getWeekOrderRent()
                    .subtract(statisticsDto.getLastWeekOrderRent())
                    .divide(statisticsDto.getLastWeekOrderRent(), 2, BigDecimal.ROUND_HALF_DOWN);
        }
        statisticsDto.setWeekOrderCountRate(weekOrderRentRate);
        if (statisticsDto.getLastMonthOrderCount() != 0) {
            float monthOrderCountRate =
                    (float) (statisticsDto.getMonthOrderCount() - statisticsDto.getLastMonthOrderCount())
                            / statisticsDto.getLastMonthOrderCount();
            statisticsDto.setMonthOrderCountRate(
                    new BigDecimal(monthOrderCountRate).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        } else {
            statisticsDto.setMonthOrderCountRate(BigDecimal.ONE);
        }
        BigDecimal monthOrderRentRate = BigDecimal.ONE;
        if (statisticsDto.getLastMonthOrderRent()
                .compareTo(BigDecimal.ZERO) != 0) {
            monthOrderRentRate = statisticsDto.getMonthOrderRent()
                    .subtract(statisticsDto.getLastMonthOrderRent())
                    .divide(statisticsDto.getLastMonthOrderRent(), 2, BigDecimal.ROUND_HALF_DOWN);
        }
        statisticsDto.setMonthOrderCountRate(monthOrderRentRate);
        return statisticsDto;
    }
}
