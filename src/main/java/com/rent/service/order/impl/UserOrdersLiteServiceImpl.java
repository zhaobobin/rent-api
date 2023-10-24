package com.rent.service.order.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.rent.common.cache.product.ProductSalesCache;
import com.rent.common.cache.user.UserCertificationCache;
import com.rent.common.constant.CouponTemplateConstant;
import com.rent.common.constant.RedisKey;
import com.rent.common.converter.user.UserAddressConverter;
import com.rent.common.dto.api.request.LiteConfirmOrderReq;
import com.rent.common.dto.api.request.TrailLiteRequest;
import com.rent.common.dto.api.request.UserOrderSubmitReq;
import com.rent.common.dto.api.resp.LiteConfirmOrderResp;
import com.rent.common.dto.api.resp.OrderSubmitResponse;
import com.rent.common.dto.components.response.AliPayFreezeResponse;
import com.rent.common.dto.marketing.OrderCouponDto;
import com.rent.common.dto.mq.OrderDeadMessage;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.resquest.OrderListQueryRequest;
import com.rent.common.dto.product.*;
import com.rent.common.dto.user.UserAddressDto;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.enums.mq.EnumOrderDeadOperate;
import com.rent.common.enums.mq.OrderMsgEnum;
import com.rent.common.enums.order.*;
import com.rent.common.enums.product.ProductFreightType;
import com.rent.common.enums.product.ProductStatus;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.common.util.AmountUtil;
import com.rent.common.util.AsyncUtil;
import com.rent.common.util.SequenceUtil;
import com.rent.common.util.StringUtil;
import com.rent.dao.order.*;
import com.rent.dao.user.UserAddressDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.*;
import com.rent.model.product.ChannelStore;
import com.rent.model.user.UserAddress;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.components.OrderAuditUserService;
import com.rent.service.marketing.LiteCouponCenterService;
import com.rent.service.order.OrderLocationAddressService;
import com.rent.service.order.OrderSubmitCore;
import com.rent.service.order.UserOrdersLiteService;
import com.rent.service.product.ChannelSplitBillService;
import com.rent.service.product.ProductService;
import com.rent.service.product.ProductSnapshotsService;
import com.rent.service.product.ShopService;
import com.rent.service.user.DistrictService;
import com.rent.service.user.UserCertificationService;
import com.rent.util.DateUtil;
import com.rent.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserOrdersLiteServiceImpl implements UserOrdersLiteService {

    private final ProductService productService;
    private final UserOrdersDao userOrdersDao;
    private final OrderSubmitCore orderSubmitCore;
    private final UserOrderCashesDao userOrderCashesDao;
    private final UserOrderItemsDao userOrderItemsDao;
    private final OrderByStagesDao orderByStagesDao;
    private final UserAddressDao userAddressDao;
    private final ProductSnapshotsService productSnapshotsService;
    private final AliPayCapitalService aliPayCapitalService;
    private final LiteCouponCenterService liteCouponCenterService;
    private final UserCertificationService userCertificationService;
    private final ShopService shopService;
    private final RabbitTemplate rabbitTemplate;
    private final DistrictService districtService;
    private final ChannelSplitBillService channelSplitBillService;
    private final ChannelUserOrdersDao channelUserOrdersDao;
    private final OrderLocationAddressService orderLocationAddressService;
    private final OrderAuditUserService orderAuditUserService;

    @Value("${hzsx.deposit.ratio:0.7}")
    private String depositRatio;
    @Value("${hzsx.deposit.rent.ratio:1.0}")
    private String depositRentRatio;

    private CouponRentPriceInfo calculateRentAmount(int duration, BigDecimal skuPrice) {
        /* 纯租金 */
        BigDecimal rentPrice = skuPrice.multiply(new BigDecimal(duration)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //计算总期数
        int totalPeriods = (duration % 31 == 0) ? duration / 31 : duration / 31 + 1;
        /* 剩余期每期租金 */
        BigDecimal periodsPrice = rentPrice.divide(new BigDecimal(totalPeriods), 2, BigDecimal.ROUND_HALF_UP);
        return new CouponRentPriceInfo(rentPrice, periodsPrice);
    }

    @Override
    public LiteConfirmOrderResp confirm(LiteConfirmOrderReq request) {
        //获取商品信息
        ConfirmOrderProductDto productDto = productService.getConfirmData(request.getSkuId(), request.getDuration());
        //获取价格
        BigDecimal cyclePrice = productDto.getSkuCyclePrice();
        BigDecimal cycleSalePrice = productDto.getSkuCycleSalePrice();

        String orderId = SequenceUtil.getTypeSerialNo(EnumSerialModalName.ORDER_ID);
        LiteConfirmOrderResp resp = new LiteConfirmOrderResp();

        //增值服务
        List<ShopAdditionalServicesDto> shopAdditionalServices = productDto.getAdditionalServices()
                .stream()
                .map(ProductAdditionalServicesDto::getShopAdditionalServices)
                .collect(Collectors.toList());
        //获取用户收货地址
        List<UserAddress> userAddressList = userAddressDao.getUserAddress(request.getUid());
        UserAddressDto userAddressDto = null;
        if (CollectionUtils.isNotEmpty(userAddressList)) {
            for (UserAddress userAddress : userAddressList) {
                if (userAddress.getIsDefault()) {
                    userAddressDto = UserAddressConverter.model2Dto(userAddress);
                    break;
                }
            }
            if (userAddressDto == null) {
                userAddressDto = UserAddressConverter.model2Dto(userAddressList.get(0));
            }
            if (userAddressDto != null) {
                userAddressDto.setProvinceStr(districtService.getNameByDistrictId(userAddressDto.getProvince().toString()));
                userAddressDto.setCityStr(districtService.getNameByDistrictId(userAddressDto.getCity().toString()));
                userAddressDto.setAreaStr(districtService.getNameByDistrictId(userAddressDto.getArea().toString()));
            }
        }
        //计算优惠券
        CouponRentPriceInfo couponRentPriceInfo = calculateRentAmount(request.getDuration(), cyclePrice);
        ProductShopCateReqDto productShopCateReqDto = productService.selectProductCateByProductId(
                productDto.getProductSkus()
                        .getItemId());
        List<OrderCouponDto> orderCouponDtoList = liteCouponCenterService.getOrderCoupon(
                productShopCateReqDto.getCategoryId()
                        .toString(), productDto.getProductSkus()
                        .getItemId(), productShopCateReqDto.getShopId(), request.getUid(), couponRentPriceInfo.getRentPrice(),
                couponRentPriceInfo.getPeriodsPrice(), EnumOrderType.GENERAL_ORDER);
        //计算延期券
        Boolean isBuyOutSupported = productDto.getBuyOutSupport() > 0;
        OrderPricesDto orderRepayPlan = createOrderRepayPlan(request.getDuration(), shopAdditionalServices, cyclePrice,
                cycleSalePrice, isBuyOutSupported, null, productDto.getProductSkus().getMarketPrice(),
                productDto.getProductSkus().getDepositPrice());
        resp.setOrderCouponList(orderCouponDtoList);
        resp.setDiscountAmount(BigDecimal.ZERO);
        resp.setOrderId(orderId);
        resp.setAddress(userAddressDto);
        resp.setProductName(productShopCateReqDto.getName());
        resp.setProductImage(productShopCateReqDto.getImages().get(0).getSrc());
        resp.setSpecName(productDto.getSpecName());
        resp.setAdditionalServices(productDto.getAdditionalServices());
        resp.setOrderPrices(orderRepayPlan);
        resp.setProductFreightType(productDto.getProductFreightType());
        resp.setHasCertification(UserCertificationCache.hasCertification(request.getUid()));
        resp.setIsBuyOutSupported(isBuyOutSupported);
        resp.setSalePrice(cycleSalePrice);
        if (isBuyOutSupported) {
            resp.setExpireBuyOutPrice(orderRepayPlan.getExpireBuyOutPrice());
        }
        resp.setYx(Boolean.FALSE);
        resp.setIsOnLine(productDto.getIsOnLine());
        return resp;
    }

    @Override
    public OrderPricesDto trail(TrailLiteRequest request) {
        ConfirmOrderProductDto productDto = productService.getConfirmData(request.getSkuId(), request.getDuration());
        List<ProductAdditionalServicesDto> productAdditionalServices = productDto.getAdditionalServices();
        Set<String> additionalServicesIdSet = request.getAdditionalServicesIds()
                .stream()
                .collect(Collectors.toSet());
        List<ShopAdditionalServicesDto> selectedAdditionalService = new ArrayList<>();
        for (ProductAdditionalServicesDto dto : productAdditionalServices) {
            if (additionalServicesIdSet.contains(dto.getShopAdditionalServices().getId().toString())) {
                selectedAdditionalService.add(dto.getShopAdditionalServices());
            }
        }

        BigDecimal cyclePrice = productDto.getSkuCyclePrice();
        BigDecimal cycleSalePrice = productDto.getSkuCycleSalePrice();
        //计算优惠券价格
        OrderCouponDto orderCoupon = null;
        if (StringUtils.isNotEmpty(request.getCouponId())) {
            CouponRentPriceInfo couponRentPriceInfo = calculateRentAmount(request.getDuration(), cyclePrice);
            ProductShopCateReqDto productShopCateReqDto = productService.selectProductCateByProductId(
                    productDto.getProductSkus()
                            .getItemId());
            orderCoupon = liteCouponCenterService.getCouponInfoByCode(productShopCateReqDto.getCategoryId()
                            .toString(), productDto.getProductSkus()
                            .getItemId(), productShopCateReqDto.getShopId(), request.getUid(), couponRentPriceInfo.getRentPrice(),
                    couponRentPriceInfo.getPeriodsPrice(), EnumOrderType.GENERAL_ORDER,
                    request.getCouponId());
        }

        //计算账单信息
        Boolean isBuyOutSupported = productDto.getBuyOutSupport() > 0;
        OrderPricesDto orderPricesDto = createOrderRepayPlan(request.getDuration(), selectedAdditionalService,
                cyclePrice, cycleSalePrice, isBuyOutSupported, orderCoupon,
                productDto.getProductSkus().getMarketPrice(), productDto.getProductSkus().getDepositPrice());
        return orderPricesDto;
    }

    @Override
    public OrderSubmitResponse submit(UserOrderSubmitReq request) {
        //防止订单重复提交的判断
        String key = "orderSubmitLock:" + request.getOrderId();
        if (!RedisUtil.tryLock(key, 1800)) {
            throw new HzsxBizException(EnumOrderError.ORDER_IDEMPOTENT_EXCEPTION.getCode(), EnumOrderError.ORDER_IDEMPOTENT_EXCEPTION.getMsg(), this.getClass());
        }
        //校验订单是否已经存在
        if (userOrdersDao.existsWithOrderId(request.getOrderId())) {
            throw new HzsxBizException(EnumOrderError.ORDER_EXISTS.getCode(), EnumOrderError.ORDER_EXISTS.getMsg(), this.getClass());
        }
        //校验收货地址
        if (request.getAddressId() == null) {
            throw new HzsxBizException(EnumOrderError.ORDER_IDEMPOTENT_EXCEPTION.getCode(), EnumOrderError.ORDER_IDEMPOTENT_EXCEPTION.getMsg(), this.getClass());
        }
        // 用户风险
        if (StringUtil.isNotEmpty(request.getNsfLevel()) && null != EnumOrderNsfLevel.findValue(request.getNsfLevel())) {
            RedisUtil.set(RedisKey.ORDER_NSF + request.getOrderId(), EnumOrderNsfLevel.findValue(request.getNsfLevel()).getCode(), 60 * 60 * 2);
        }
        // 营销反作弊
        if (StringUtil.isNotEmpty(request.getAntiCheatingLevel())) {
            RedisUtil.set(RedisKey.ORDER_ANTI_CHEATING + request.getOrderId(), request.getAntiCheatingLevel(), 60 * 60 * 2);
        }

        //查询产品详情
        ConfirmOrderProductDto productDto = productService.getConfirmData(request.getSkuId(), request.getDuration());
        String productId = productDto.getProductSkus().getItemId();
        String shopId = productDto.getShopId();

        OrderCouponDto orderCouponDto = null;
        BigDecimal cyclePrice = productDto.getSkuCyclePrice();
        BigDecimal cycleSalePrice = productDto.getSkuCycleSalePrice();
        if (StringUtils.isNotEmpty(request.getCouponId())) {
            CouponRentPriceInfo couponRentPriceInfo = calculateRentAmount(request.getDuration(), cyclePrice);
            ProductShopCateReqDto productShopCateReqDto = productService.selectProductCateByProductId(productId);
            orderCouponDto = liteCouponCenterService.getCouponInfoByCode(productShopCateReqDto.getCategoryId().toString(), productId, shopId, request.getUid(), couponRentPriceInfo.getRentPrice(),
                    couponRentPriceInfo.getPeriodsPrice(), EnumOrderType.GENERAL_ORDER, request.getCouponId());
        }

        // 查询增值服务列表
        Set<String> additionalServicesIdSet = request.getAdditionalServicesIds().stream().collect(Collectors.toSet());
        List<ShopAdditionalServicesDto> selectedAdditionalService = new ArrayList<>();
        for (ProductAdditionalServicesDto dto : productDto.getAdditionalServices()) {
            if (additionalServicesIdSet.contains(dto.getShopAdditionalServices().getId().toString())) {
                selectedAdditionalService.add(dto.getShopAdditionalServices());
            }
        }
        //计算账单信息
        Boolean isBuyOutSupported = productDto.getBuyOutSupport() > 0;
        OrderPricesDto orderPricesDto = createOrderRepayPlan(request.getDuration(), selectedAdditionalService,
                cyclePrice, cycleSalePrice, isBuyOutSupported, orderCouponDto,
                productDto.getProductSkus().getMarketPrice(), productDto.getProductSkus().getDepositPrice());
        orderPricesDto.setBaseServiceFee(BigDecimal.ZERO);
        //查询用户收货地址
        UserAddressDto userAddressDto = UserAddressConverter.model2Dto(userAddressDao.getById(request.getAddressId()));
        //快照信息
        Integer snapshotsId = productSnapshotsService.queryProductSnapshotsId(productId);
        // 保存订单数据
        UserCertificationDto userCertificationDto = userCertificationService.getByUid(request.getUid());
        ShopDto shopDto = shopService.queryByShopId(productDto.getShopId());
        // 提交订单保持订单数据
        orderSubmitCore.saveOrderData(shopDto, snapshotsId, selectedAdditionalService, userAddressDto,
                orderPricesDto, userCertificationDto, request.getOrderId(), request.getDuration(),
                productId, request.getSkuId(), request.getRemark(), request.getUid(), request.getNsfLevel(), productDto);

        OrderSubmitResponse orderSubmitResponse = new OrderSubmitResponse();
        // TODO 没有免押 判断是否设置订单押金，若设置了则

        BigDecimal realDeposit = orderPricesDto.getDeposit();
        BigDecimal depositPrice = productDto.getProductSkus().getDepositPrice();
        if (depositPrice != null && depositPrice.longValue() > 0.0) {
            realDeposit = depositPrice;
        }

        // 调用资金预授权冻结接口
        AliPayFreezeResponse aliPayFreezeResponse = aliPayCapitalService.aliPayFreeze(request.getOrderId(),
                request.getUid(), realDeposit, request.getSkuId(), productId, 0,
                EnumOrderType.GENERAL_ORDER, orderPricesDto.getTotalRent(), request.getDuration());
        orderSubmitResponse.setFreezeUrl(aliPayFreezeResponse.getFreezeUrl());
        orderSubmitResponse.setSerialNo(aliPayFreezeResponse.getSerialNo());
        // 更新优惠使用，商品库存信息，订单超时支付任务 使用异步任务
        if (null != orderCouponDto) {
            final String couponId = orderCouponDto.getCode();
            AsyncUtil.runAsync(() -> liteCouponCenterService.updateCouponUsed(couponId, request.getOrderId()),
                    "订单:" + request.getOrderId() + "使用优惠券");
        }

        //过期时间修改为180s
        rabbitTemplate.convertAndSend(OrderMsgEnum.EXPIRATION.getExchange(), OrderMsgEnum.EXPIRATION.getRoutingKey(), new OrderDeadMessage(request.getOrderId(), EnumOrderDeadOperate.EXPIRATION));
        rabbitTemplate.convertAndSend(OrderMsgEnum.SUBMIT_ORDER.getExchange(), OrderMsgEnum.SUBMIT_ORDER.getRoutingKey(), request.getOrderId());
        //录入用户当前位置
        OrderLocationAddressDto orderLocationAddress = request.getOrderLocationAddress();
        if (null != orderLocationAddress) {
            orderLocationAddress.setOrderId(request.getOrderId());
            AsyncUtil.runAsync(() -> orderLocationAddressService.addOrderLocationAddress(orderLocationAddress));
        }
        Object cacheObject = RedisUtil.get(RedisKey.CHANNEL_USER_IN_7_DAYS + request.getUid());
        if (null != cacheObject) {
            //如果缓存里有数据判断为渠道订单
            try {
                log.info("判断为渠道订单");
                ChannelStore channelStore = JSON.parseObject((String) cacheObject, ChannelStore.class);
                ChannelUserOrders channelUserOrders = new ChannelUserOrders();
                ChannelSplitBillDto channelSplitBillDto = channelSplitBillService.getOne(channelStore.getMarketingChannelId());
                channelUserOrders.setMarketingChannelId(channelSplitBillDto.getUid());
                channelUserOrders.setMarketingId(channelStore.getMarketingId());

                channelUserOrders.setUserName(userCertificationDto.getUserName());
                channelUserOrders.setPhone(userCertificationDto.getTelephone());
                channelUserOrders.setProductName(productDto.getProductName());
                channelUserOrders.setOrderId(request.getOrderId());
                channelUserOrders.setTotalAmount(orderPricesDto.getTotalRent());
                channelUserOrders.setTotalPeriods(orderPricesDto.getTotalPeriods());
                channelUserOrders.setScale(channelSplitBillDto.getScale());
                channelUserOrders.setShopName(shopDto.getName());
                channelUserOrders.setCreateTime(new Date());
                channelUserOrdersDao.save(channelUserOrders);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("渠道相关订单保存失败,{}", e.getMessage());
            }
        }


        // 自动分配审核人员
        if (shopDto.getIsAutoAuditUser() > 0) {
            orderAuditUserService.selectAuditUser(shopId, request.getOrderId());
        }


        if (ProductSalesCache.hasInit(productId)) {
            ProductSalesCache.incrProductSales(productId);
        } else {
            Integer sales = userOrdersDao.getProductSales(productId);
            ProductSalesCache.setProductSales(productId, sales);
        }
        return orderSubmitResponse;
    }

    @Override
    public Page<UserOrderListDto> userOrderList(OrderListQueryRequest request) {
        Page<UserOrders> userOrdersPage = new Page<>();
        Page page = new Page(request.getPageNumber(), request.getPageSize());
        if (request.getOverDueQueryFlag()) {
            List<String> violationStatusList = Arrays.asList(EnumViolationStatus.SETTLEMENT_OVERDUE.getCode(),
                    EnumViolationStatus.OVERDUE_RETURN.getCode(), EnumViolationStatus.STAGE_OVERDUE.getCode());
            userOrdersPage = userOrdersDao.pageUserOrdersLite(page, request.getUid(), null, violationStatusList);
        } else {
            userOrdersPage = userOrdersDao.pageUserOrdersLite(page, request.getUid(), request.getStatusList(), null);
        }
        if (CollectionUtil.isEmpty(userOrdersPage.getRecords())) {
            return null;
        }
        //订单id列表
        List<String> orderIdList = userOrdersPage.getRecords()
                .stream()
                .map(UserOrders::getOrderId)
                .collect(Collectors.toList());
        List<String> shopIdList = userOrdersPage.getRecords()
                .stream()
                .map(UserOrders::getShopId)
                .collect(Collectors.toList());

        //订单金额信息
        Map<String, UserOrderCashes> cashesMap = userOrderCashesDao.queryListByOrderIds(orderIdList);
        //店铺信息列表
        Map<String, ShopDto> shopDtoMap = this.shopService.selectShopInfoByShopId(shopIdList);
        //查询对应产品信息列表
        Map<String, OrderProductDetailDto> detailDtoMap = this.selectOrderProductDetail(orderIdList);
        //组装数据
        List<UserOrderListDto> userOrderList = Lists.newArrayList();
        userOrdersPage.getRecords()
                .forEach(userOrders -> {
                    UserOrderListDto userOrderListDto = new UserOrderListDto();
                    userOrderListDto.setShowReletButton(Boolean.FALSE);
                    if (userOrders.getStatus()
                            .equals(EnumOrderStatus.RENTING) && null != userOrders.getUnrentTime()) {
                        Date now = new Date();
                        if (DateUtil.compare(now, userOrders.getUnrentTime()) != 1 || DateUtil.compare(now,
                                DateUtil.addDate(userOrders.getUnrentTime(), 3)) != 1) {
                            List<String> relets = userOrdersDao.selectAllOrderId(userOrders.getOrderId());
                            if (relets.size() < 31) {
                                List<OrderByStages> orderByStages = orderByStagesDao.queryOrderByOrderId(
                                        userOrders.getOrderId());
                                long payedPeriods = orderByStages.stream()
                                        .filter(a -> EnumOrderByStagesStatus.PAYED.equals(a.getStatus())
                                                || EnumOrderByStagesStatus.OVERDUE_PAYED.equals(a.getStatus()))
                                        .count();
                                if (payedPeriods == orderByStages.size()) {
                                    userOrderListDto.setShowReletButton(Boolean.TRUE);
                                }
                            }
                        }
                    }
                    userOrderListDto.setOrderId(userOrders.getOrderId());
                    userOrderListDto.setCreateTime(userOrders.getCreateTime());
                    userOrderListDto.setProductId(userOrders.getProductId());
                    userOrderListDto.setFaceAuthStatus(userOrders.getFaceAuthStatus());
                    if (EnumOrderStatus.TO_AUDIT.equals(userOrders.getStatus())) {
                        userOrderListDto.setStatus(EnumOrderStatus.PENDING_DEAL.getCode());
                    } else {
                        userOrderListDto.setStatus(userOrders.getStatus()
                                .getCode());
                    }
                    userOrderListDto.setCancelReason(userOrders.getCancelReason());
                    userOrderListDto.setOrderType(userOrders.getType()
                            .getCode());
                    userOrderListDto.setUnrentExpressId(userOrders.getUnrentExpressId());
                    userOrderListDto.setUnrentExpressNo(userOrders.getUnrentExpressNo());
                    userOrderListDto.setCloseType(null != userOrders.getCloseType() ? userOrders.getCloseType()
                            .getDescription() : null);
                    userOrderListDto.setViolationStatus(userOrders.getIsViolation()
                            .getCode());

                    OrderProductDetailDto detailDTO = detailDtoMap.get(userOrders.getOrderId());
                    UserOrderCashes userOrderCashes = cashesMap.get(userOrders.getOrderId());
                    Integer isShowReturnButton = NumberUtils.INTEGER_ZERO;
                    Integer isShowBuyOutButton = NumberUtils.INTEGER_ZERO;
                    //商品信息
                    if (null != detailDTO) {
                        userOrderListDto.setMainImageUrl(detailDTO.getMainImageUrl());
                        userOrderListDto.setProductName(detailDTO.getProductName());
                        userOrderListDto.setSkuTitle(detailDTO.getSkuTitle());
                        userOrderListDto.setSkuId(detailDTO.getSkuId()
                                .toString());
                        userOrderListDto.setBuyOutSupport(detailDTO.getBuyOutSupport());
                        Integer buyOutSupport = detailDTO.getBuyOutSupport();
                        Integer returnRule = detailDTO.getReturnRule();
                        if (null != userOrders.getRentStart() && null != userOrders.getUnrentTime()) {
                            Boolean isBefore = DateUtil.isBefore(DateUtil.dateStr4(userOrders.getUnrentTime()));
                            if (ProductStatus.RETURN_RULE_MATURE.getCode()
                                    .equals(returnRule)) {
                                if (isBefore) {
                                    isShowReturnButton = NumberUtils.INTEGER_ONE;
                                }
                            }
                            if (ProductStatus.RETURN_RULE_ADVANCE.getCode()
                                    .equals(returnRule)) {
                                isShowReturnButton = NumberUtils.INTEGER_ONE;
                            }
                            if (ProductStatus.IS_BUY_OUT_MATURE.getCode()
                                    .equals(buyOutSupport)) {
                                if (isBefore) {
                                    isShowBuyOutButton = NumberUtils.INTEGER_ONE;
                                }
                            }
                            if (ProductStatus.IS_BUY_OUT.getCode()
                                    .equals(buyOutSupport)) {
                                isShowBuyOutButton = NumberUtils.INTEGER_ONE;
                            }
                        }
                    }
                    userOrderListDto.setIsShowBuyOutButton(isShowBuyOutButton);
                    userOrderListDto.setIsShowReturnButton(isShowReturnButton);
                    //租金
                    if (null != userOrderCashes) {
                        userOrderListDto.setTotalRent(userOrderCashes.getTotalRent()
                                .subtract(userOrderCashes.getFullReduction()));
                        userOrderListDto.setSettlementRent(userOrderCashes.getSettlementRent());
                    }
                    //商家服务电话
                    ShopDto shopDto = shopDtoMap.get(userOrders.getShopId());
                    if (null != shopDto) {
                        userOrderListDto.setServiceTel(shopDto.getServiceTel());
                        userOrderListDto.setShopName(shopDto.getName());
                    }
                    userOrderList.add(userOrderListDto);
                });
        Page<UserOrderListDto> userOrdersDtoPage = new Page<>();
        BeanUtil.copyProperties(userOrdersPage, userOrdersDtoPage);
        userOrdersDtoPage.setRecords(userOrderList);
        return userOrdersDtoPage;
    }

    @Override
    public Map<String, OrderProductDetailDto> selectOrderProductDetail(List<String> orderIdList) {
        Map<String, OrderProductDetailDto> orderProductDetailDtoMap = new HashedMap();
        try {
            //商品
            List<UserOrderItems> userOrderItems = userOrderItemsDao.queryListByOrderIds(orderIdList);
            Set<Integer> snapShotIdSet = userOrderItems.stream()
                    .map(UserOrderItems::getSnapShotId)
                    .map(Long::intValue)
                    .collect(Collectors.toSet());
            //快照信息
            Map<String, ProductSnapshotsDto> snapshotsDtoMap = productSnapshotsService.queryProductSnapshotsList(
                            new ArrayList<Integer>(snapShotIdSet))
                    .stream()
                    .collect(Collectors.toMap(a -> a.getId()
                            .toString(), v -> v, (key1, key2) -> key1));
            //组装数据
            userOrderItems.forEach(userOrderItem -> {
                UserOrders userOrders = userOrdersDao.selectOneByOrderId(userOrderItem.getOrderId());
                OrderProductDetailDto orderProductDetailDto = new OrderProductDetailDto();
                ProductSnapshotsDto snapshotsDto = snapshotsDtoMap.get(userOrderItem.getSnapShotId()
                        .toString());
                orderProductDetailDto.setSkuId(userOrderItem.getSkuId());
                if (null != snapshotsDto && StringUtil.isNotEmpty((String) snapshotsDto.getData())) {
                    ShopProductSnapResponse productSnapshot = JSONObject.parseObject((String) snapshotsDto.getData(),
                            ShopProductSnapResponse.class);
                    ProductDto productDto = productSnapshot.getProduct();
                    if (productDto != null) {
                        List<ProductImageDto> images = productSnapshot.getProductImage();
                        orderProductDetailDto.setId(productDto.getId());
                        orderProductDetailDto.setProductName(productDto.getName());
                        orderProductDetailDto.setProductId(productDto.getProductId());
                        if (CollectionUtil.isNotEmpty(images)) {
                            orderProductDetailDto.setMainImageUrl(images.get(0)
                                    .getSrc());
                        }
                        orderProductDetailDto.setShopId(productDto.getShopId());
                        //归还规则上线前默认支持提前归还
                        orderProductDetailDto.setReturnRule(
                                null == productDto.getReturnRule() ? ProductStatus.RETURN_RULE_ADVANCE.getCode() :
                                        productDto.getReturnRule());
                        //设置运费
                        ProductFreightType freightType = ProductFreightType.find(productDto.getFreightType());
                        orderProductDetailDto.setFreightStr(
                                null != freightType ? freightType.getMsg() : ProductFreightType.IS_PAY_TYPE.getMsg());
                        //快照sku
                        List<ShopProductSnapSkusResponse> shopProductSnapSkuses
                                = productSnapshot.getShopProductSnapSkus();
                        if (CollectionUtil.isNotEmpty(shopProductSnapSkuses)) {
                            for (ShopProductSnapSkusResponse shopProductSnapSkus : shopProductSnapSkuses) {
                                ProductSkusDto productSkusDto = shopProductSnapSkus.getProductSkus();
                                if (userOrderItem.getSkuId()
                                        .intValue() == productSkusDto.getId()) {
                                    //获取销售价 有问题不同租期的销售价不同这里取第一个不正确 增加租期判断
                                    if (CollectionUtil.isNotEmpty(shopProductSnapSkus.getCyclePrices())) {
                                        shopProductSnapSkus.getCyclePrices()
                                                .stream()
                                                .filter(a -> userOrderItem.getSkuId()
                                                        .equals(a.getSkuId()) && a.getDays()
                                                        .equals(userOrders.getRentDuration()))
                                                .findFirst()
                                                .ifPresent(productCyclePricesDto -> orderProductDetailDto.setSalePrice(
                                                        productCyclePricesDto.getSalePrice()));
                                        //老系统中支持自定义租期，有些订单租期在系统中找不到，买断时候会有问题
                                        //修复方法是判断用户的订单租期是否在快照中可以找到，找到取找到的；
                                        //找不到的话，取订单租期最接近的下一个快照租期时长的销售价
                                        //若订单租期是最大，在快照租期中找不到，取快照租期租期最大的销售价
                                        if (null == orderProductDetailDto.getSalePrice()) {
                                            List<ProductCyclePricesDto> cyclePrices
                                                    = shopProductSnapSkus.getCyclePrices()
                                                    .stream()
                                                    .filter(a -> userOrderItem.getSkuId()
                                                            .equals(a.getSkuId()))
                                                    .collect(Collectors.toList());
                                            if (CollectionUtils.isNotEmpty(cyclePrices)) {
                                                ProductCyclePricesDto maxCyclePricesDto = cyclePrices.stream()
                                                        .max(Comparator.comparing(ProductCyclePricesDto::getDays))
                                                        .get();
                                                //订单租期大于快照租期，取快照租期最大的销售价;否则取最接近订单租期的下一个快照租期的销售价
                                                Boolean isMoreMax = userOrders.getRentDuration()
                                                        .compareTo(maxCyclePricesDto.getDays()) == 1;
                                                if (isMoreMax) {
                                                    orderProductDetailDto.setSalePrice(
                                                            maxCyclePricesDto.getSalePrice());
                                                } else {
                                                    cyclePrices.stream()
                                                            .filter(a -> userOrders.getRentDuration()
                                                                    .compareTo(a.getDays()) == -1)
                                                            .findFirst()
                                                            .ifPresent(
                                                                    productCyclePricesDto -> orderProductDetailDto.setSalePrice(
                                                                            productCyclePricesDto.getSalePrice()));
                                                }

                                            }

                                        }

                                    }

                                    orderProductDetailDto.setCyclePrices(shopProductSnapSkus.getCyclePrices());
                                    orderProductDetailDto.setMarketPrice(shopProductSnapSkus.getProductSkus()
                                            .getMarketPrice());

                                    //买断功能上线之前不存在买断字段，默认设置为不支持买断
                                    orderProductDetailDto.setBuyOutSupport(
                                            null == productSkusDto.getBuyOutSupport() ? 0 :
                                                    productSkusDto.getBuyOutSupport());
                                    List<ProductSkuValuesDto> skuValues = shopProductSnapSkus.getSkuValues();
                                    if (CollectionUtil.isNotEmpty(skuValues)) {
                                        String name = "";
                                        for (ProductSkuValuesDto productSkuValues : skuValues) {
                                            Long specValueId = productSkuValues.getSpecValueId();
                                            if (specValueId != null) {
                                                List<ShopProductSnapSpecResponse> shopProductSnapSpecs
                                                        = productSnapshot.getSnapSpecs();
                                                for (ShopProductSnapSpecResponse shopProductSnapSpec : shopProductSnapSpecs) {
                                                    if (shopProductSnapSpec != null) {
                                                        List<ShopProductSnapSpecResponse.ShopProductSnapSpecValue>
                                                                shopProductSnapSpecValues
                                                                = shopProductSnapSpec.getSpecValues();
                                                        if (CollectionUtil.isNotEmpty(shopProductSnapSpecValues)) {
                                                            for (ShopProductSnapSpecResponse.ShopProductSnapSpecValue specValue : shopProductSnapSpecValues) {
                                                                if (specValue != null) {
                                                                    ProductSpecValueDto productSpecValue
                                                                            = specValue.getProductSpecValue();
                                                                    if (productSpecValue != null &&
                                                                            productSpecValue.getId()
                                                                                    .intValue() == specValueId) {
                                                                        String specName = productSpecValue.getName();
                                                                        name += "/" + specName;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        try {
                                            orderProductDetailDto.setSkuTitle(
                                                    name.length() > 0 ? name.substring(1) : "");
                                        } catch (Exception e) {
                                            log.error(skuValues + "1069  ***********  ", e);
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                orderProductDetailDtoMap.put(userOrderItem.getOrderId(), orderProductDetailDto);
            });

        } catch (Exception e) {
            log.error("业务出现异常", e);
            throw new HzsxBizException(EnumOrderError.BIZ_FAILED.getCode(), EnumOrderError.BIZ_FAILED.getMsg(),
                    this.getClass());
        }
        return orderProductDetailDtoMap;
    }

    /**
     * 计算支付信息
     *
     * @param duration
     * @param additionalServicesList
     * @param skuPrice
     * @param orderCouponDto
     * @param marketPrice
     * @return
     */
    @Override
    public OrderPricesDto createOrderRepayPlan(int duration, List<ShopAdditionalServicesDto> additionalServicesList,
                                               BigDecimal skuPrice, BigDecimal salePrice, Boolean isBuyOutSupported,
                                               OrderCouponDto orderCouponDto, BigDecimal marketPrice, BigDecimal depositPrice) {

        List<OrderByStagesDto> orderByStagesDtoList = this.calculateRepayDay(duration);
        //计算增值服务费金额
        BigDecimal additionalMoney = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(additionalServicesList)) {
            additionalMoney = additionalServicesList.stream()
                    .filter(a -> a.getStatus()
                            .equals(1) && null != a.getPrice())
                    .map(ShopAdditionalServicesDto::getPrice)
                    .reduce(additionalMoney, BigDecimal::add);
        }
        //填充订单金额信息
        OrderPricesDto orderPricesDto = this.fillOrderPriceInfo(duration, skuPrice, additionalMoney, orderCouponDto,
                orderByStagesDtoList, marketPrice, depositPrice);

        if (isBuyOutSupported) {
            //买断价格计算
            BigDecimal buyOutAmount = salePrice.subtract(orderPricesDto.getTotalRent())
                    .compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : salePrice.subtract(orderPricesDto.getTotalRent());
            orderPricesDto.setExpireBuyOutPrice(buyOutAmount);
        }
        return orderPricesDto;
    }

    /**
     * 计算还款计划
     *
     * @param duration 租期
     * @return
     */
    private List<OrderByStagesDto> calculateRepayDay(int duration) {
        Date now = new Date();
        String startDay = DateUtil.getDate(now, "dd");
        //计算总期数
        int totalPeriods = (duration % 31 == 0) ? duration / 31 : duration / 31 + 1;
        //计算第一期
        List<OrderByStagesDto> orderByStagesDtoList = new ArrayList<>(totalPeriods);
        OrderByStagesDto firstOrderByStages = new OrderByStagesDto();
        firstOrderByStages.setStatementDate(now);
        firstOrderByStages.setTotalPeriods(totalPeriods);
        firstOrderByStages.setCurrentPeriods(1);
        orderByStagesDtoList.add(firstOrderByStages);
        if (totalPeriods == 1) {
            return orderByStagesDtoList;
        }
        //后续期次计算
        for (int i = 1; i < totalPeriods; i++) {
            OrderByStagesDto orderByStagesDto = new OrderByStagesDto();
            orderByStagesDto.setStatementDate(DateUtil.getAfterMonthCurDay(i));
            orderByStagesDto.setTotalPeriods(totalPeriods);
            orderByStagesDto.setCurrentPeriods(i + 1);
            orderByStagesDtoList.add(orderByStagesDto);
        }
        return orderByStagesDtoList;
    }

    private OrderPricesDto fillOrderPriceInfo(int duration, BigDecimal skuPrice, BigDecimal additionalServicesAmount,
                                              OrderCouponDto orderCouponDto, List<OrderByStagesDto> orderByStagesDtoList,
                                              BigDecimal marketPrice, BigDecimal depositPrice) {

        OrderPricesDto orderPricesDto = new OrderPricesDto();
        /* 纯租金 */
        BigDecimal rentPrice = skuPrice.multiply(new BigDecimal(duration)).setScale(2, BigDecimal.ROUND_HALF_UP);
        /* 总租金 = 纯租金 - 优惠金额*/
        BigDecimal totalRent;
        //计算优惠券金额
        if (null != orderCouponDto && orderCouponDto.getScene()
                .equals(CouponTemplateConstant.SCENE_RENT)) {
            totalRent = rentPrice.subtract(orderCouponDto.getDiscountAmount());
            totalRent = totalRent.compareTo(BigDecimal.ZERO) <= 0 ? new BigDecimal("0.01").multiply(
                            new BigDecimal(orderByStagesDtoList.size()))
                    .setScale(2, BigDecimal.ROUND_HALF_UP) : totalRent;
            orderPricesDto.setCouponPrice(orderCouponDto.getDiscountAmount());
            if (StringUtil.isNotEmpty(orderCouponDto.getShopId()) && !EnumBackstageUserPlatform.OPE.getCode().equals(orderCouponDto.getShopId())) {
                orderPricesDto.setShopCouponPrice(orderCouponDto.getDiscountAmount());
            } else {
                orderPricesDto.setPlatformCouponPrice(orderCouponDto.getDiscountAmount());
            }
        } else {
            totalRent = rentPrice;
        }

        //先按照只有一期的计算。如果只有一期，第一期纯租金就是总租金，首期支付租金=总租金+增值服费
        /*首期租金*/
        BigDecimal firstPeriodsRentPrice = totalRent;
        /*首期支付租金*/
        BigDecimal firstPeriodsPrice = totalRent.add(additionalServicesAmount);
        /* 剩余期每期租金 */
        BigDecimal otherPeriodsPrice = BigDecimal.ZERO;
        //如果不止一期
        if (orderByStagesDtoList.size() > 1) {
            /* 剩余期每期租金 */
            otherPeriodsPrice = totalRent.divide(new BigDecimal(orderByStagesDtoList.size()), 2,
                    BigDecimal.ROUND_HALF_UP);
            //首期租金 = 租金 - 每期租金 * （总期数-1）
            firstPeriodsRentPrice = totalRent.subtract(
                            otherPeriodsPrice.multiply(new BigDecimal(orderByStagesDtoList.size() - 1)))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            if (null != orderCouponDto && orderCouponDto.getScene()
                    .equals(CouponTemplateConstant.SCENE_FIRST)) {
                //计算总租金
                BigDecimal discountAmount;
                discountAmount = firstPeriodsRentPrice.subtract(orderCouponDto.getDiscountAmount())
                        .compareTo(BigDecimal.ZERO) < 0 ? firstPeriodsRentPrice.subtract(new BigDecimal("0.01")) :
                        firstPeriodsRentPrice.subtract(orderCouponDto.getDiscountAmount());
                totalRent = totalRent.subtract(discountAmount);
                //首期租金优惠券 如果减后<0 设为0.01
                firstPeriodsRentPrice = firstPeriodsRentPrice.subtract(orderCouponDto.getDiscountAmount())
                        .compareTo(BigDecimal.ZERO) < 0 ? new BigDecimal("0.01") : firstPeriodsRentPrice.subtract(
                        orderCouponDto.getDiscountAmount());
                if (StringUtil.isNotEmpty(orderCouponDto.getShopId())) {
                    orderPricesDto.setShopCouponPrice(orderCouponDto.getDiscountAmount());
                } else {
                    orderPricesDto.setPlatformCouponPrice(orderCouponDto.getDiscountAmount());
                }
                orderPricesDto.setCouponPrice(orderCouponDto.getDiscountAmount());
            }
            //首期支付租金 = 首期纯租金 + 增值服务费用
            firstPeriodsPrice = firstPeriodsRentPrice.add(additionalServicesAmount);
        }
        //月租金
        // 计算原始月租金 除不尽的情况余数会加到首月，这里只是平均每月
        // 原始月租金
        BigDecimal originalMonthRentPrice;
        if (duration > 30) {
            originalMonthRentPrice = AmountUtil.safeMultiply(skuPrice, new BigDecimal(30));
        } else {
            originalMonthRentPrice = AmountUtil.safeMultiply(skuPrice, new BigDecimal(duration));
        }
        /* 订单总金额(总租金+增值服务+物流费用) */
        BigDecimal orderAmount = totalRent.add(additionalServicesAmount);

        //计算押金 市场加和总租金中取较大者，没有免押的情况下
        //市场价
        BigDecimal depositSkuMarketPrice = marketPrice.multiply(new BigDecimal(depositRatio))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        //租金
        BigDecimal depositTotalRent = totalRent.multiply(new BigDecimal(depositRentRatio)).subtract(firstPeriodsPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
        //押金取两者中大者
        BigDecimal deposit = depositTotalRent.compareTo(depositSkuMarketPrice) < 0 ? depositSkuMarketPrice : depositTotalRent;
        // 2023年8月16日21:30:51 新增商品手动控制押金逻辑
        if (depositPrice != null) {
            deposit = depositPrice;
        }
        log.info("押金金额为:{}", deposit);
        /* 冻结金额 */
        BigDecimal freezePrice = firstPeriodsPrice.add(deposit);
        //填充还款计划金额
        for (OrderByStagesDto orderByStagesDto : orderByStagesDtoList) {
            orderByStagesDto.setTotalRent(totalRent);
            if (orderByStagesDto.getCurrentPeriodsRent() == null) {
                if (orderByStagesDto.getCurrentPeriods() == 1) {
                    orderByStagesDto.setCurrentPeriodsRent(firstPeriodsPrice);
                } else {
                    orderByStagesDto.setCurrentPeriodsRent(otherPeriodsPrice);
                }
            }
        }
        orderPricesDto.setSkuPrice(skuPrice);
        orderPricesDto.setRentPrice(rentPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setTotalRent(totalRent.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setTotalPeriods(orderByStagesDtoList.size());
        orderPricesDto.setFirstPeriodsPrice(firstPeriodsPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setFirstPeriodsRentPrice(firstPeriodsRentPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setOtherPeriodsPrice(otherPeriodsPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setRestPeriods(orderByStagesDtoList.size() - 1);
        orderPricesDto.setAdditionalServicesPrice(additionalServicesAmount);
        orderPricesDto.setLogisticPrice(BigDecimal.ZERO);
        orderPricesDto.setDepositAmount(deposit);
        orderPricesDto.setDepositReduce(BigDecimal.ZERO);
        orderPricesDto.setOriginalMonthRentPrice(originalMonthRentPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setOrderAmount(orderAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setFreezePrice(freezePrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setDeposit(deposit.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setOrderByStagesDtoList(orderByStagesDtoList);
        return orderPricesDto;
    }
}