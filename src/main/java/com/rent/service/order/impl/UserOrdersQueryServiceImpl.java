package com.rent.service.order.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rent.common.cache.api.OrderContractDataCache;
import com.rent.common.converter.order.*;
import com.rent.common.dto.components.dto.OrderContractDto;
import com.rent.common.dto.components.dto.UserOrderAgreementRequest;
import com.rent.common.dto.marketing.UserCouponDto;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.response.DepositOrderDetailDto;
import com.rent.common.dto.order.response.DepositOrderPageDto;
import com.rent.common.dto.order.response.DepositOrderRecordDto;
import com.rent.common.dto.order.response.OrderBackAddressResponse;
import com.rent.common.dto.product.*;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.enums.order.*;
import com.rent.common.enums.product.ProductFreightType;
import com.rent.common.enums.product.ProductStatus;
import com.rent.common.properties.SuningProperties;
import com.rent.common.util.AmountUtil;
import com.rent.config.outside.PlatformChannelDto;
import com.rent.dao.order.*;
import com.rent.dao.product.ProductEvaluationDao;
import com.rent.dao.product.ProductSnapshotsDao;
import com.rent.dao.user.UserBankCardDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.*;
import com.rent.model.user.UserBankCard;
import com.rent.service.marketing.LiteUserCouponService;
import com.rent.service.order.OrderRepayPlanFactory;
import com.rent.service.order.UserOrdersQueryService;
import com.rent.service.product.*;
import com.rent.service.user.DistrictService;
import com.rent.service.user.UserCertificationService;
import com.rent.util.AppParamUtil;
import com.rent.util.DateUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-8 上午 10:33:04
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserOrdersQueryServiceImpl implements UserOrdersQueryService {

    private final UserOrdersDao userOrdersDao;
    private final PlatformChannelService platformChannelService;
    private final DistrictService districtService;
    private final ProductService productService;
    private final UserCertificationService userCertificationService;
    private final ShopEnterpriseInfosService shopEnterpriseInfosService;
    private final OrderAddressDao orderAddressDao;
    private final OrderByStagesDao orderByStagesDao;
    private final ShopService shopService;
    private final UserOrderCashesDao userOrderCashesDao;
    private final LiteUserCouponService liteUserCouponService;
    private final UserOrderBuyOutDao userOrderBuyOutDao;
    private final UserOrderItemsDao userOrderItemsDao;
    private final ProductSnapshotsDao productSnapshotsDao;
    private final OrderRepayPlanFactory orderRepayPlanFactory;
    private final ProductEvaluationDao productEvaluationDao;
    private final ProductGiveBackAddressesService productGiveBackAddressesService;
    private final OrderPayDepositDao orderPayDepositDao;
    private final OrderContractDao orderContractDao;
    private final UserBankCardDao userBankCardDao;
    private final SuningProperties suningProperties;
    private final OrderAuditUserDao orderAuditUserDao;

    @Override
    public OrderStatusCountDto liteStatusCount(String uid) {
        List<UserOrders> list = userOrdersDao.list(new QueryWrapper<UserOrders>()
                .select("status,is_violation")
                .eq("uid", uid)
                .eq("channel_id", AppParamUtil.getChannelId()));
        OrderStatusCountDto dto = new OrderStatusCountDto();
        for (UserOrders userOrders : list) {
            switch (userOrders.getStatus()) {
                case WAITING_PAYMENT:
                    dto.incrWaitPay();
                    break;
                case TO_AUDIT:
                case PENDING_DEAL:
                    dto.incrWaitDelivery();
                    break;
                case WAITING_USER_RECEIVE_CONFIRM:
                    dto.incrWaitReceive();
                    break;
                case RENTING:
                case TO_GIVE_BACK:
                    dto.incrRenting();
                    break;
                case WAITING_SETTLEMENT:
                case WAITING_SETTLEMENT_PAYMENT:
                    dto.incrWaitSettle();
                    break;
            }
            if (!EnumViolationStatus.NORMAL.equals(userOrders.getIsViolation()) && !EnumViolationStatus.ADVANCE.equals(userOrders.getIsViolation())) {
                dto.incrOverdue();
            }
        }
        return dto;
    }

    @Override
    public List<HotRentDto> hotRentOrder() {
        Date begin = DateUtil.getBeforeDay(-30);
        Date end = DateUtil.getBeforeDay(0);
        List<UserOrders> userOrders = userOrdersDao.list(new QueryWrapper<UserOrders>()
                .select("order_id", "product_id", "uid", "shop_id")
                .between("create_time", begin, end));

        return UserOrderConverter.modelList2HotRentDtoList(userOrders);
    }

    @Override
    public OrderContractDto agreementV2(UserOrderAgreementRequest request) {
        ShopProductAddReqDto shopProductAddReqDto = productService.selectProductDetailByProductId(request.getProductId());
        ShopEnterpriseInfosDto enterpriseInfosDto = shopEnterpriseInfosService.queryShopEnterpriseInfosDetailByshopId(shopProductAddReqDto.getShopId());
        ShopDto shopDto = shopService.queryByShopId(shopProductAddReqDto.getShopId());

        OrderContractDto orderContractDto = new OrderContractDto();
        orderContractDto.setShopName(enterpriseInfosDto.getName());
        orderContractDto.setShopAddress(shopDto.getShopAddress());

        String uid = request.getUid();
        UserCertificationDto certificationDto = userCertificationService.getByUid(uid);
        if (certificationDto != null) {
            orderContractDto.setUserName(certificationDto.getUserName());
            orderContractDto.setIdNo(certificationDto.getIdCard());
            orderContractDto.setTelephone(certificationDto.getTelephone());
        } else {
            orderContractDto.setUserName("");
            orderContractDto.setIdNo("");
            orderContractDto.setTelephone("");
        }
        orderContractDto.setProductName(shopProductAddReqDto.getName());
        PlatformChannelDto platformChannelDto = platformChannelService.getPlatFormChannel(AppParamUtil.getChannelId());
        if (platformChannelDto != null) {
            orderContractDto.setPlatformName(platformChannelDto.getEnterpriseName());
            orderContractDto.setPlatformAddress(platformChannelDto.getEnterpriseAddress());
            orderContractDto.setPlatform(platformChannelDto.getPlatformName());
            orderContractDto.setCompany(platformChannelDto.getEnterpriseName());
            orderContractDto.setPlatformContactTelephone(platformChannelDto.getEnterpriseLegalPhone());
        } else {
            throw new IllegalStateException("Unexpected value: " + AppParamUtil.getChannelId());
        }
        orderContractDto.setCreateTime(DateUtil.date2String(new Date(), DateUtil.DATE_FORMAT_3));
        OrderContractDto cache = OrderContractDataCache.getOrderContractDataCache(request.getTempOrderId());
        if (cache != null) {
            orderContractDto.setOrderId(cache.getOrderId());
            orderContractDto.setReciveCity(cache.getReciveCity());
            orderContractDto.setReciveAddress(cache.getReciveAddress());
            orderContractDto.setReciveName(cache.getReciveName());
            orderContractDto.setSkuTitle(cache.getSkuTitle());
            orderContractDto.setTotalPeriod(cache.getOrderByStagesDtoList().size() + "");
            orderContractDto.setTotalRent(cache.getTotalRent());
            orderContractDto.setSalePrice(cache.getSalePrice());
            orderContractDto.setBuyOutPrice(cache.getBuyOutPrice());
            orderContractDto.setOrderByStagesDtoList(cache.getOrderByStagesDtoList());
            orderContractDto.setCreateTime(DateUtil.date2String(new Date(), DateUtil.DATE_FORMAT_3));
        }
        return orderContractDto;
    }

    @Override
    public Boolean checkUserHasPlaceOrder(String uid) {
        return userOrdersDao.existsWithUid(uid);
    }

    @Override
    public OrderDetailResponse selectUserOrderDetail(String orderId) {
        OrderDetailResponse response = new OrderDetailResponse();
        UserOrders userOrders = userOrdersDao.getOne(new QueryWrapper<>(UserOrders.builder()
                .orderId(orderId)
                .build()).select("id", "create_time", "order_id", "delivery_time", "status", "rent_duration",
                "express_no", "express_id", "rent_start", "unrent_time", "unrent_express_no", "unrent_express_id",
                "give_back_address_id", "type", "shop_id", "return_time", "examine_status", "payment_time", "is_violation",
                "face_auth_status", "uid", "remark", "channel_id", "user_name", "rent_credit"));

        if (userOrders == null) {
            log.error("user order not exist");
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(), EnumOrderError.ORDER_NOT_EXISTS.getMsg(), this.getClass());
        }
        UserOrdersDto userOrdersDto = UserOrdersConverter.model2Dto(userOrders);
        OrderContract orderContract = orderContractDao.getByOrderId(orderId);
        if (orderContract != null) {
            response.setOrderContractUrl(StringUtil.isNotEmpty(orderContract.getSignedPdf()) ? orderContract.getSignedPdf() : orderContract.getOriginPdf());
        }
        //用户收货地址消息

        OrderAddress orderAddress = orderAddressDao.queryByOrderId(orderId);
        Map<String, String> distinctNameMap = districtService.getDistinctName(Arrays.asList(orderAddress.getProvince()
                .toString(), orderAddress.getCity()
                .toString(), orderAddress.getArea()
                .toString()));
        response.setOrderAddressDto(OrderAddressConverter.model2Dto(orderAddress, distinctNameMap));
        //账单信息
        List<OrderByStages> orderByStages = orderByStagesDao.queryOrderByOrderId(orderId);
        response.setOrderByStagesDtoList(OrderByStagesConverter.modelList2DtoList(orderByStages));
        //商品信息
        Map<String, OrderProductDetailDto> detailDtoMap = this.selectOrderProductDetail(Collections.singletonList(orderId));
        OrderProductDetailDto detailDto = detailDtoMap.get(orderId);
        //店铺信息
        ShopDto shopDto = shopService.queryByShopId(userOrders.getShopId());
        response.setShopDto(shopDto);
        //评论信息
        Boolean evaluationStatus = productEvaluationDao.checkOrderEvaluation(orderId);
        userOrdersDto.setEvaluationStatus(evaluationStatus);
        //订单支付信息
        UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
        UserOrderCashesDto userOrderCashesDto = UserOrderCashesConverter.model2Dto(userOrderCashes);
        if (EnumOrderStatus.WAITING_PAYMENT.equals(userOrders.getStatus()) && null != userOrderCashesDto) {
            //返回待支付订单需要的优惠信息
            UserCouponDto userCouponDto = liteUserCouponService.getUserCouponByOrderId(userOrders.getOrderId());
            if (null != userCouponDto && null != userCouponDto.getTemplateBindId()) {
                if (DateUtil.isInRange(new Date(), userCouponDto.getStartTime(), userCouponDto.getEndTime())) {
                    response.setUserCouponDto(userCouponDto);
                }
            }
        }
        response.setUserOrderCashesDto(userOrderCashesDto);
        BigDecimal totalRent = userOrderCashes.getTotalRent();
        //是否展示续租按钮-原订单+3个续租订单以上不支持显示续租按钮
        long payedPeriods = orderByStages.stream()
                .filter(a -> EnumOrderByStagesStatus.PAYED.equals(a.getStatus()) || EnumOrderByStagesStatus.OVERDUE_PAYED.equals(a.getStatus()))
                .count();
        Boolean isMoreReletLimit = Boolean.TRUE;
        if (orderId.contains(EnumSerialModalName.RELET_ORDER_ID.getCode())) {
            totalRent = userOrdersDao.getOrderTotolRent(orderId);
            List<String> relets = userOrdersDao.selectAllOrderId(orderId);
            if (relets.size() >= 31) {
                isMoreReletLimit = Boolean.FALSE;
            }
        }
        userOrdersDto.setShowReletButton(userOrders.getStatus().equals(EnumOrderStatus.RENTING) && orderByStages.size() == payedPeriods && isMoreReletLimit);
        UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(userOrdersDto.getOrderId());
        BigDecimal salePrice = userOrderItems.getSalePrice();
        detailDto.setSalePrice(salePrice);
//        if (CollectionUtil.isNotEmpty(detailDto.getCyclePrices())) {
//            ProductCyclePricesDto productCyclePricesDto = detailDto.getCyclePrices()
//                    .stream()
//                    .filter(a -> a.getDays() == userOrders.getRentDuration().intValue())
//                    .findFirst()
//                    .orElse(null);
//            if (null != productCyclePricesDto) {
//                salePrice = productCyclePricesDto.getSalePrice();
//                detailDto.setSalePrice(salePrice);
//            }
//        }
        response.setOrderProductDetailDto(detailDto);
        Integer buyOutSupport = detailDto.getBuyOutSupport();
        Integer returnRule = detailDto.getReturnRule();
        userOrdersDto.setReturnRule(detailDto.getReturnRule());
        userOrdersDto.setOrderBuyOutSupport(buyOutSupport);
        Integer isShowReturnButton = NumberUtils.INTEGER_ZERO;
        Integer isShowBuyOutButton = NumberUtils.INTEGER_ZERO;
        //商品信息
        if (null != detailDto) {
            if (null != userOrders.getRentStart() && null != userOrders.getUnrentTime()) {
                Boolean isBefore = DateUtil.isBefore(DateUtil.dateStr4(userOrders.getUnrentTime()));
                if (ProductStatus.RETURN_RULE_MATURE.getCode().equals(returnRule)) {
                    if (isBefore) {
                        isShowReturnButton = NumberUtils.INTEGER_ONE;
                    }
                }
                if (ProductStatus.RETURN_RULE_ADVANCE.getCode().equals(returnRule)) {
                    isShowReturnButton = NumberUtils.INTEGER_ONE;
                }
                if (ProductStatus.IS_BUY_OUT_MATURE.getCode().equals(buyOutSupport)) {
                    if (isBefore) {
                        isShowBuyOutButton = NumberUtils.INTEGER_ONE;
                    } else {
                        //续租0元买断订单解除“仅到期买断的限制”
                        EnumOrderType type = Optional.ofNullable(userOrders.getType()).orElse(EnumOrderType.GENERAL_ORDER);
                        if (EnumOrderType.RELET_ORDER.equals(type) && EnumOrderStatus.RENTING.equals(userOrders.getStatus())) {
                            //判断当前买断价是否是0原买断-放开限制
                            OrderProductDetailDto productDetailDto = this.selectOrderProductDetail(
                                            Collections.singletonList(userOrders.getOrderId()))
                                    .get(userOrders.getOrderId());
                            List<OrderByStages> reletOrderByStages = orderByStagesDao.queryAllOrderByOrderId(userOrders.getOrderId());
                            //买断尾款
                            BigDecimal endFund = orderRepayPlanFactory.getBuyOutAmount(userOrders, reletOrderByStages, productDetailDto.getSalePrice());
                            if (endFund.compareTo(BigDecimal.ZERO) <= 0) {
                                isShowBuyOutButton = NumberUtils.INTEGER_ONE;
                            }
                        }
                    }
                }
                if (ProductStatus.IS_BUY_OUT.getCode().equals(buyOutSupport)) {
                    isShowBuyOutButton = NumberUtils.INTEGER_ONE;
                }
            }
        }
        userOrdersDto.setIsShowBuyOutButton(isShowBuyOutButton);
        userOrdersDto.setIsShowReturnButton(isShowReturnButton);
        userOrdersDto.setRentCredit(null == userOrders.getRentCredit() ? Boolean.TRUE : userOrders.getRentCredit());
        if (null != buyOutSupport && !buyOutSupport.equals(0) && null != totalRent && null != salePrice) {
            BigDecimal expireBuyOutPrice = AmountUtil.safeSubtract(true, salePrice, totalRent);
            if (ProductStatus.IS_BUY_OUT.getCode().equals(buyOutSupport) && null != userOrders.getRentStart() && null != userOrders.getUnrentTime()) {
                //当前买断尾款
                BigDecimal endFund = BigDecimal.ZERO;
                if (orderId.contains(EnumSerialModalName.RELET_ORDER_ID.getCode())) {
                    List<OrderByStages> allOrderByStages = orderByStagesDao.queryAllOrderByOrderId(orderId);
                    if (CollectionUtils.isNotEmpty(allOrderByStages) && allOrderByStages.size() == orderByStages.size()) {
                        endFund = expireBuyOutPrice;
                    } else {
                        endFund = orderRepayPlanFactory.calculateBuyOutAmount(userOrders, allOrderByStages, detailDto.getSalePrice());
                    }
                } else {
                    endFund = orderRepayPlanFactory.calculateBuyOutAmount(userOrders, orderByStages, detailDto.getSalePrice());
                }
                endFund = endFund.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : endFund;
                userOrdersDto.setEndFund(endFund);
            }
            userOrdersDto.setExpireBuyOutPrice(expireBuyOutPrice);
        }
        //买断信息
        UserOrderBuyOut userOrderBuyOut = userOrderBuyOutDao.selectOneByOrderId(orderId);
        if (null != userOrderBuyOut && userOrderBuyOut.getState().equals(EnumOrderBuyOutStatus.FINISH)) {
            UserOrderBuyOutDto userOrderBuyOutDto = UserOrderBuyOutConverter.model2Dto(userOrderBuyOut);
            userOrderBuyOutDto.setCouponReduction(AmountUtil.safeAdd(userOrderBuyOut.getPlatformCouponReduction(), userOrderBuyOut.getShopCouponReduction()));
            userOrderBuyOutDto.setProductName(detailDto.getProductName());
            response.setUserOrderBuyOutDto(userOrderBuyOutDto);
        }
        //身份证认证信息
        UserCertificationDto userCertification = userCertificationService.getByUid(userOrders.getUid());
        userOrdersDto.setUserIdCardPhotoCertStatus(null != userCertification && StringUtil.isNotEmpty(userCertification.getIdCardFrontUrl()) && StringUtil.isNotEmpty(userCertification.getIdCardBackUrl()));
        userOrdersDto.setUserFaceCertStatus(userOrdersDto.getFaceAuthStatus().equals("03"));
        response.setUserCertification(userCertification);
        //订单信息
        response.setUserOrdersDto(userOrdersDto);
        // 信审人员
        OrderAuditUser orderAuditUser = orderAuditUserDao.getByOrderId(orderId);
        if (orderAuditUser != null) {
            response.setAuditUserQrcodeUrl(orderAuditUser.getQrcodeUrl());
        }

        return response;
    }

    @Override
    public Map<String, OrderProductDetailDto> selectOrderProductDetail(List<String> orderIdList) {
        Map<String, OrderProductDetailDto> orderProductDetailDtoMap = Maps.newHashMap();
        try {
            //商品
            List<UserOrderItems> userOrderItems = userOrderItemsDao.queryListByOrderIds(orderIdList);
            Set<Long> snapShotIdSet = userOrderItems.stream().map(UserOrderItems::getSnapShotId).collect(Collectors.toSet());
            Map<String, ProductSnapshotsDto> snapshotsDtoMap = productSnapshotsDao.queryProductSnapshotsList(new ArrayList<>(snapShotIdSet)).stream().collect(Collectors.toMap(a -> a.getId().toString(), v -> v, (key1, key2) -> key1));
            //组装数据
            userOrderItems.forEach(userOrderItem -> {
                OrderProductDetailDto orderProductDetailDto = new OrderProductDetailDto();
                ProductSnapshotsDto snapshotsDto = snapshotsDtoMap.get(userOrderItem.getSnapShotId().toString());
                orderProductDetailDto.setSkuId(userOrderItem.getSkuId());
                if (null != snapshotsDto && StringUtil.isNotEmpty((String) snapshotsDto.getData())) {
                    ShopProductSnapResponse productSnapshot = JSONObject.parseObject((String) snapshotsDto.getData(), ShopProductSnapResponse.class);
                    ProductDto productDto = productSnapshot.getProduct();
                    if (productDto != null) {
                        orderProductDetailDto.setId(productDto.getId());
                        orderProductDetailDto.setProductName(userOrderItem.getProductName());
                        orderProductDetailDto.setProductId(userOrderItem.getProductId());
                        orderProductDetailDto.setMainImageUrl(userOrderItem.getProductImage());
                        orderProductDetailDto.setShopId(productDto.getShopId());
                        //归还规则上线前默认支持提前归还
                        orderProductDetailDto.setReturnRule(null == productDto.getReturnRule() ? ProductStatus.RETURN_RULE_ADVANCE.getCode() : productDto.getReturnRule());
                        //设置运费
                        ProductFreightType freightType = ProductFreightType.find(productDto.getFreightType());
                        orderProductDetailDto.setFreightStr(null != freightType ? freightType.getMsg() : ProductFreightType.IS_PAY_TYPE.getMsg());
                        orderProductDetailDto.setFreightType(StringUtil.isEmpty(productDto.getFreightType()) ? productDto.getFreightType() : ProductFreightType.IS_PAY_TYPE.getCode());
                        orderProductDetailDto.setReturnfreightType(StringUtil.isNotEmpty(productDto.getReturnfreightType()) ? productDto.getReturnfreightType() : ProductFreightType.IS_FREE_TYPE.getCode());
                        orderProductDetailDto.setSalePrice(userOrderItem.getSalePrice());
                        orderProductDetailDto.setSkuTitle(userOrderItem.getSpecJoinName());
                        orderProductDetailDto.setMarketPrice(userOrderItem.getMarketPrice());
                        orderProductDetailDto.setBuyOutSupport(userOrderItem.getBuyOutSupport());
                        //快照sku
                        List<ShopProductSnapSkusResponse> shopProductSnapSkuses = productSnapshot.getShopProductSnapSkus();
                        if (CollectionUtil.isNotEmpty(shopProductSnapSkuses)) {
                            for (ShopProductSnapSkusResponse shopProductSnapSkus : shopProductSnapSkuses) {
                                ProductSkusDto productSkusDto = shopProductSnapSkus.getProductSkus();
                                if (userOrderItem.getSkuId().intValue() == productSkusDto.getId()) {
                                    orderProductDetailDto.setCyclePrices(shopProductSnapSkus.getCyclePrices());
                                }
                            }
                        }
                    }
                }
                orderProductDetailDtoMap.put(userOrderItem.getOrderId(), orderProductDetailDto);
            });
        } catch (Exception e) {
            log.error("业务出现异常", e);
            throw new HzsxBizException(EnumOrderError.BIZ_FAILED.getCode(), EnumOrderError.BIZ_FAILED.getMsg(), this.getClass());
        }
        return orderProductDetailDtoMap;
    }

    @Override
    public List<OrderBackAddressResponse> queryOrderGiveBackAddress(String orderId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        List<OrderBackAddressResponse> orderBackAddressResponseList = Lists.newArrayList();
        List<ShopGiveBackAddressesDto> giveBackAddressesDtoList = productGiveBackAddressesService.queryProductGiveBackList(userOrders.getProductId());
        if (CollectionUtil.isNotEmpty(giveBackAddressesDtoList)) {
            for (ShopGiveBackAddressesDto shopGiveBackAddresses : giveBackAddressesDtoList) {
                OrderBackAddressResponse bean = new OrderBackAddressResponse();
                BeanUtils.copyProperties(shopGiveBackAddresses, bean);
                Map<String, String> distinctMap = districtService.getDistinctName(Arrays.asList(
                        shopGiveBackAddresses.getAreaId().toString(),
                        shopGiveBackAddresses.getCityId().toString(),
                        shopGiveBackAddresses.getProvinceId().toString()));
                bean.setAreaStr(distinctMap.get(shopGiveBackAddresses.getAreaId().toString()));
                bean.setCityStr(distinctMap.get(shopGiveBackAddresses.getCityId().toString()));
                bean.setProvinceStr(distinctMap.get(shopGiveBackAddresses.getProvinceId().toString()));
                orderBackAddressResponseList.add(bean);
            }
        }

        return orderBackAddressResponseList;
    }

    @Override
    public DepositOrderPageDto depositOrderList(String uid) {
        DepositOrderPageDto pageDto = new DepositOrderPageDto();
        List<OrderPayDeposit> deposits = orderPayDepositDao.getListByUid(uid);
        if (CollectionUtils.isEmpty(deposits)) {
            pageDto.setTotalDeposit(BigDecimal.ZERO);
            pageDto.setWithdrawAble(BigDecimal.ZERO);
            pageDto.setOrderList(Collections.EMPTY_LIST);
            return pageDto;
        }
        Map<String, OrderPayDeposit> orderPayDepositMap = deposits.stream().collect(Collectors.toMap(OrderPayDeposit::getOrderId, Function.identity()));
        List<String> orderIdList = deposits.stream().map(OrderPayDeposit::getOrderId).collect(Collectors.toList());
        List<UserOrders> userOrdersList = userOrdersDao.list(new QueryWrapper<UserOrders>().in("order_id", orderIdList));
        Map<String, OrderProductDetailDto> detailDtoMap = this.selectOrderProductDetail(orderIdList);

        List<DepositOrderDto> depositOrderList = new ArrayList<>();
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal withdrawAble = new BigDecimal(0);
        for (UserOrders userOrders : userOrdersList) {
            DepositOrderDto depositOrderDto = new DepositOrderDto();
            OrderProductDetailDto detailDTO = detailDtoMap.get(userOrders.getOrderId());
            depositOrderDto.setOrderId(userOrders.getOrderId());
            depositOrderDto.setProductImgSrc(detailDTO.getMainImageUrl());
            depositOrderDto.setProductName(detailDTO.getProductName());
            depositOrderDto.setCreateTime(userOrders.getCreateTime());
            depositOrderDto.setOrderStatus(userOrders.getStatus());
            OrderPayDeposit orderPayDeposit = orderPayDepositMap.get(userOrders.getOrderId());
            if (EnumOrderPayDepositStatus.PAID.equals(orderPayDeposit.getStatus())) {
                depositOrderDto.setPaidDeposit(orderPayDeposit.getAmount());
                depositOrderDto.setUnPaidDeposit(BigDecimal.ZERO);
                totalAmount = totalAmount.add(orderPayDeposit.getAmount());
                if (OrderDepositWithdrawStatusSet.withDrawOrderStatusSet.contains(userOrders.getStatus())) {
                    withdrawAble = withdrawAble.add(orderPayDeposit.getAmount());
                }
            } else if (EnumOrderPayDepositStatus.WITHDRAW.equals(orderPayDeposit.getStatus())) {
                continue;
            } else {
                if (userOrders.getStatus().equals(EnumOrderStatus.CLOSED)) {
                    continue;
                }
                depositOrderDto.setUnPaidDeposit(orderPayDeposit.getAmount());
                depositOrderDto.setPaidDeposit(BigDecimal.ZERO);
            }
            depositOrderDto.setStatus(orderPayDeposit.getStatus());
            depositOrderList.add(depositOrderDto);
        }
        pageDto.setTotalDeposit(totalAmount);
        pageDto.setWithdrawAble(withdrawAble);
        pageDto.setOrderList(depositOrderList);
        return pageDto;
    }

    @Override
    public DepositOrderRecordDto queryDepositOrderList(String uid) {
        DepositOrderRecordDto pageDto = new DepositOrderRecordDto();
        List<DepositOrderDetailDto> depositOrderDetailDtoList = Lists.newLinkedList();
        List<OrderPayDeposit> deposits = orderPayDepositDao.getListByUid(uid);
        if (CollectionUtil.isEmpty(deposits)) {
            return pageDto;
        }
        for (OrderPayDeposit deposit : deposits) {
            if (BigDecimal.ZERO.compareTo(deposit.getAmount()) == 0 ||
                    EnumOrderPayDepositStatus.WAITING_PAYMENT.equals(deposit.getStatus())) continue;
            DepositOrderDetailDto depositOrderDetailDto = new DepositOrderDetailDto();
            BeanUtil.copyProperties(deposit, depositOrderDetailDto);
            depositOrderDetailDtoList.add(depositOrderDetailDto);
        }
        pageDto.setDepositOrderDetailDtoList(depositOrderDetailDtoList);
        return pageDto;
    }


    @Override
    public UserOrderPaySignDto queryOrderPaySignInfo(String uid) {
        Boolean showDepositIcon = false;
        Boolean showCyclePayIcon = false; // 用户可主动解绑
        Boolean showBankCardIcon = false; // 用户可主动解绑
        //修复调风控失败数据
        /**
         * 有些情况支付成功后调用风控系统会返回系统异常或者没给结果
         * 这时候先不处理风控结果，不计算押金
         * 用户查询订单的时候修复
         */
        //查询押金订单
        List<OrderPayDeposit> deposits = orderPayDepositDao.getListByUid(uid);
        if (!CollectionUtils.isEmpty(deposits)) {
            for (OrderPayDeposit orderPayDeposit : deposits) {
                if (EnumOrderPayDepositStatus.WAITING_PAYMENT.equals(orderPayDeposit.getStatus())) {
                    showDepositIcon = true;
                    break;
                }
            }
        }
        if (suningProperties.getOpenSign().equals("true")) {
            // 查询是否需要签约银行卡
            List<UserBankCard> userBankCards = userBankCardDao.getUserBankCardByUid(uid);
            if (CollectionUtils.isEmpty(userBankCards)) {
                showBankCardIcon = true;
            }
        }


        return new UserOrderPaySignDto(showDepositIcon, showCyclePayIcon, showBankCardIcon);
    }

}
