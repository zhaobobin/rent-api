package com.rent.service.order.impl;

import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.dao.order.UserOrdersStatusTranferDao;
import com.rent.model.order.UserOrdersStatusTranfer;
import com.rent.service.order.OrderOperateCore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-10-27 下午 1:48:17
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderOperateCoreImpl implements OrderOperateCore {

    private final UserOrdersStatusTranferDao userOrdersStatusTranferDao;

    @Override
    public void orderOperationRegister(String orderId, EnumOrderStatus oldStatus, EnumOrderStatus newStatus,
        String operatorId, String operatorName, String operate) {
        try {
            UserOrdersStatusTranfer ordersStatusTranfer = new UserOrdersStatusTranfer();
            ordersStatusTranfer.setOrderId(orderId);
            ordersStatusTranfer.setOperatorId(operatorId);
            ordersStatusTranfer.setOperatorName(operatorName);
            ordersStatusTranfer.setOldStatus(oldStatus.getCode());
            ordersStatusTranfer.setNewStatus(newStatus.getCode());
            ordersStatusTranfer.setOperate(operate);
            ordersStatusTranfer.setCreateTime(new Date());
            userOrdersStatusTranferDao.save(ordersStatusTranfer);
        } catch (Exception e) {
            log.error("登记订单状态异常",e);
        }
    }
}
