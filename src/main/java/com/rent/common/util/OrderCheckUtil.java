package com.rent.common.util;

import com.rent.common.enums.order.EnumOrderError;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.UserOrders;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-29 下午 5:47:48
 * @since 1.0
 */
@Slf4j
public class OrderCheckUtil {

    /**
     * 校验订单状态工具类
     *  @param userOrders 订单信息
     * @param checkStatus 原状态
     * @param operator
     */
    public static void checkUserOrdersStatus(UserOrders userOrders, EnumOrderStatus checkStatus, String operator) {
        //校验订单
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(),
                EnumOrderError.ORDER_NOT_EXISTS.getMsg());
        }
        if (!checkStatus.equals(userOrders.getStatus())) {
            log.error("订单:{}状态错误，期望的状态是:[{}],实际状态是:[{}]", userOrders.getOrderId(), checkStatus.getDescription(),
                userOrders.getStatus()
                    .getDescription());
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_NOT_ALLOW_APPLY.getCode(),
                "订单当前状态不允许进行["+operator+"]操作");
        }
    }

    public static void checkQueryDate(Date startDate, Date endDate, EnumOrderError orderError) {
        if (null == startDate ^ null == endDate) {
            throw new HzsxBizException(orderError.getCode(), orderError.getMsg());
        }
    }
}
