package com.rent.service.order.impl;

import com.rent.common.converter.order.OrderShopCloseConverter;
import com.rent.common.dto.order.resquest.ShopRiskReviewOrderIdDto;
import com.rent.common.enums.order.EnumOrderError;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.OrderShopCloseDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.OrderShopClose;
import com.rent.model.order.UserOrders;
import com.rent.service.order.OrderCloseCore;
import com.rent.service.order.OrderShopCloseService;
import com.rent.service.order.UserOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 商家风控关单Service
 *
 * @author xiaoyao
 * @date 2020-06-17 16:54
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderShopCloseServiceImpl implements OrderShopCloseService {

    private final OrderShopCloseDao orderShopCloseDao;
    private final UserOrdersDao userOrdersDao;
    private final OrderByStagesDao orderByStagesDao;
    private final OrderCloseCore orderCloseCore;
    private final UserOrdersService userOrdersService;

    @Override
    public void shopRiskCloseOrder(ShopRiskReviewOrderIdDto request) {
        //查询订单
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(request.getOrderId());
        //校验订单
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(),EnumOrderError.ORDER_NOT_EXISTS.getMsg(), this.getClass());
        }
        if ((!EnumOrderStatus.PENDING_DEAL.equals(userOrders.getStatus()) && !EnumOrderStatus.TO_AUDIT.equals(
            userOrders.getStatus())) || !request.getShopId()
            .equals(userOrders.getShopId())) {
            log.error("订单:{}状态错误，期望的状态是:[{}],实际状态是:[{}]", userOrders.getOrderId(),
                EnumOrderStatus.PENDING_DEAL.getDescription() + EnumOrderStatus.TO_AUDIT.getDescription(),
                userOrders.getStatus()
                    .getDescription());
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_NOT_ALLOW_APPLY.getCode(),
                EnumOrderError.ORDER_STATUS_NOT_ALLOW_APPLY.getMsg(), this.getClass());
        }
        //关闭订单
        orderCloseCore.payedCloseOrder(userOrders, request.getCloseType(), "订单关闭");
        // 插入店铺图款记录
        OrderShopClose orderShopClose = OrderShopCloseConverter.riskOrderDto2Model(request);
        orderShopCloseDao.save(orderShopClose);
    }

}