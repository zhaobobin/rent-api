package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.rent.common.converter.order.*;
import com.rent.common.dto.order.OrderByStagesDto;
import com.rent.common.dto.order.OrderPricesDto;
import com.rent.common.dto.order.UserOrderReletSubmitRequest;
import com.rent.common.dto.product.ConfirmOrderProductDto;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.dto.user.UserAddressDto;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.enums.order.*;
import com.rent.dao.order.*;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.*;
import com.rent.service.order.OrderOperateCore;
import com.rent.service.order.OrderSubmitCore;
import com.rent.util.AppParamUtil;
import com.rent.util.DateUtil;
import com.rent.util.SnowflakeIdGenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhangqing@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020-6-15 18:27
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderSubmitCoreImpl implements OrderSubmitCore {

    private final UserOrderCashesDao userOrderCashesDao;
    private final OrderAddressDao orderAddressDao;
    private final OrderAdditionalServicesDao orderAdditionalServicesDao;
    private final UserOrderItemsDao userOrderItemsDao;
    private final OrderByStagesDao orderByStagesDao;
    private final UserOrdersDao userOrdersDao;
    private final OrderOperateCore orderOperateCore;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderData(ShopDto shopDto,
                              Integer snapshotsId,
                              List<ShopAdditionalServicesDto> additionalServicesList,
                              UserAddressDto userAddress,
                              OrderPricesDto orderPricesDto,
                              UserCertificationDto userCertificationDto,
                              String orderId,
                              int duration,
                              String productId,
                              Long skuId,
                              String remark,
                              String uid,
                              String nsfLevel,
                              ConfirmOrderProductDto productDto
    ) {
        Date now = DateUtil.getNowDate();
        // 添加数据
        UserOrderCashes userOrderCashes = UserOrderCashesConverter.prices2Model(now, orderId, orderPricesDto);
        // 保存user_order_caches表信息
        if (!userOrderCashesDao.save(userOrderCashes)) {
            log.error("submit:Failure to save user_order_caches table information");
            throw new HzsxBizException(EnumOrderError.ORDER_CASHES_SAVE_ERROR.getCode(), EnumOrderError.ORDER_CASHES_SAVE_ERROR.getMsg(), this.getClass());
        }
        //保存订单地址信息
        OrderAddress orderAddress = OrderAddressConverter.userAddress2Model(now, orderId, userAddress);
        orderAddressDao.save(orderAddress);
        // 创建订单表
        UserOrders orders = UserOrdersConverter.submitDto2Model(shopDto.getShopId(), now, orderId, duration, productId, remark, uid, nsfLevel);
        //是否需要平台电审
        orders.setAuditLabel(shopDto.getIsPhoneExamination().equals(1) ? EnumOrderAuditLabel.PLATFORM_AUDIT : EnumOrderAuditLabel.BUSINESS_AUDIT);
        //是否需要平台电审
        orders.setExamineStatus(shopDto.getIsPhoneExamination().equals(1) ? EnumOrderExamineStatus.PLATFORM_TO_EXAMINE : EnumOrderExamineStatus.BUSINESS_TO_EXAMINE);
        orders.setUserName(userCertificationDto == null ? "" : userCertificationDto.getUserName());
        userOrdersDao.save(orders);
        //添加订单操作记录 add at 2020年10月27日13:58:41
        orderOperateCore.orderOperationRegister(orders.getOrderId(), EnumOrderStatus.WAITING_PAYMENT, EnumOrderStatus.WAITING_PAYMENT, orders.getUid(), orders.getUserName(), "买家下单");
        // 创建订单增值服务记录Id
        List<OrderAdditionalServices> orderAdditionalServices = OrderAdditionalServicesConverter.additionalServices2Model(now, orderId, additionalServicesList);
        orderAdditionalServices.forEach(orderAdditionalService -> orderAdditionalServicesDao.save(orderAdditionalService));
        //商品快照
        BigDecimal skuMarketPrice = productDto.getProductSkus().getMarketPrice();
        String specJoinName = StringUtils.join(productDto.getSpecName(), "/");
        UserOrderItems userOrderItems = this.buildModel(now, orderId, productId, skuId, snapshotsId, productDto.getMainImage(),
                productDto.getSkuCycleSalePrice(), skuMarketPrice, specJoinName, productDto.getProductName(), productDto.getBuyOutSupport());
        userOrderItemsDao.save(userOrderItems);
        //保存账单信息
        List<OrderByStages> orderByStagesList = this.buildOrderByStagesList(now, duration, orderId, orderPricesDto.getOrderByStagesDtoList(), shopDto.getShopId());
        orderByStagesDao.saveBatch(orderByStagesList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveReletOrderData(ShopDto shopDto, Integer snapshotsId,
                                   List<ShopAdditionalServicesDto> additionalServicesDtoList, UserOrderReletSubmitRequest request,
                                   OrderPricesDto orderPricesDto, UserCertificationDto userCertificationDto, UserOrders originalUserOrders) {
        Date now = DateUtil.getNowDate();
        // 添加数据
        UserOrderCashes userOrderCashes = UserOrderCashesConverter.prices2Model(now, request.getOrderId(),
                orderPricesDto);
        // 保存user_order_caches表信息
        if (!userOrderCashesDao.save(userOrderCashes)) {
            log.error("submit:Failure to save user_order_caches table information");
            throw new HzsxBizException(EnumOrderError.ORDER_CASHES_SAVE_ERROR.getCode(),
                    EnumOrderError.ORDER_CASHES_SAVE_ERROR.getMsg(), this.getClass());
        }

        // 创建订单表
        UserOrders orders = UserOrdersConverter.submitDto2Model(shopDto.getShopId(), now, request);
        //是否需要平台电审
        orders.setUserName(userCertificationDto.getUserName());
        orders.setExpressId(originalUserOrders.getExpressId());
        orders.setExpressNo(originalUserOrders.getExpressNo());
        orders.setDeliveryTime(originalUserOrders.getDeliveryTime());
        userOrdersDao.save(orders);
        // 创建订单增值服务记录Id
        List<OrderAdditionalServices> orderAdditionalServices
                = OrderAdditionalServicesConverter.additionalServices2Model(now, request.getOrderId(),
                additionalServicesDtoList);
        orderAdditionalServices.forEach(
                orderAdditionalService -> orderAdditionalServicesDao.save(orderAdditionalService));
        //商品快照

        UserOrderItems originItems = userOrderItemsDao.selectOneByOrderId(orders.getOriginalOrderId());
        UserOrderItems userOrderItems = this.buildModel(now, request.getOrderId(), request.getProductId(), request.getSkuId(), snapshotsId,
                originItems.getProductImage(), originItems.getSalePrice(), originItems.getMarketPrice(), originItems.getSpecJoinName(), originItems.getProductName(), originItems.getBuyOutSupport());
        userOrderItemsDao.save(userOrderItems);
        //保存账单信息
        List<OrderByStages> orderByStagesList = this.buildOrderByStagesList(now, request.getDuration(),
                request.getOrderId(), orderPricesDto.getOrderByStagesDtoList(), shopDto.getShopId());
        orderByStagesDao.saveBatch(orderByStagesList);

        OrderAddress orderAddress = orderAddressDao.queryByOrderId(orders.getOriginalOrderId());
        orderAddress.setOrderId(orders.getOrderId());
        orderAddress.setId(null);
        orderAddressDao.save(orderAddress);

    }

    private List<OrderByStages> buildOrderByStagesList(Date now, int duration, String orderId, List<OrderByStagesDto> orderByStagesDtoList, String shopId) {
        if (CollectionUtil.isEmpty(orderByStagesDtoList)) {
            return null;
        }
        List<OrderByStages> orderByStagesList = Lists.newArrayList();
        orderByStagesDtoList.forEach(orderByStagesDto -> {
            OrderByStages orderByStages = OrderByStagesConverter.dto2Model(orderByStagesDto);
            orderByStages.setId(SnowflakeIdGenUtil.snowflakeId());
            orderByStages.setTotalPeriods(orderByStages.getTotalPeriods());
            orderByStages.setChannelId(AppParamUtil.getChannelId());
            orderByStages.setCreateTime(now);
            orderByStages.setUpdateTime(now);
            orderByStages.setOrderId(orderId);
            orderByStages.setShopId(shopId);
            orderByStages.setLeaseTerm(duration);
            orderByStages.setStatus(EnumOrderByStagesStatus.UN_PAY);
            orderByStagesList.add(orderByStages);
        });
        return orderByStagesList;
    }

    /**
     * 构建订单商品快照记录
     *
     * @param now         时间
     * @param orderId     订单号
     * @param productId   商品id
     * @param skuId       skuid
     * @param snapshotsId 快照id
     * @return 订单商品快照
     */
    private UserOrderItems buildModel(Date now, String orderId, String productId, Long skuId, Integer snapshotsId,
                                      String productImage, BigDecimal salePrice, BigDecimal marketPrice, String specJoinName, String productName, Integer buyOutSupport) {
        UserOrderItems userOrderItems = new UserOrderItems();
        userOrderItems.setCreateTime(now);
        userOrderItems.setUpdateTime(now);
        userOrderItems.setSnapShotId(snapshotsId.longValue());
        userOrderItems.setAmount(1);
        userOrderItems.setProductId(productId);
        userOrderItems.setSkuId(skuId);
        userOrderItems.setOrderId(orderId);
        userOrderItems.setProductImage(productImage);
        userOrderItems.setSalePrice(salePrice);
        userOrderItems.setMarketPrice(marketPrice);
        userOrderItems.setSpecJoinName(specJoinName);
        userOrderItems.setProductName(productName);
        userOrderItems.setBuyOutSupport(buyOutSupport);
        return userOrderItems;
    }
}
