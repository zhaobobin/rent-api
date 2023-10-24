package com.rent.service.product;




import com.rent.common.dto.order.DepositCallBackRequest;
import com.rent.common.dto.order.OpeDepositOrderPageDto;
import com.rent.common.dto.order.response.OrderByStagesPayResponse;

import java.math.BigDecimal;

/**
 * @author zhaowenchao
 */
public interface OrderPayDepositService {

    /**
     * 押金支付
     *
     * @param orderId
     * @return
     */
    OrderByStagesPayResponse orderDepositPay(String orderId);

    /**
     * 押金支付回调
     * @param request
     * @return
     */
    void orderDepositPayCallBack(DepositCallBackRequest request);

    /**
     * 退款
     * @param uid
     * @param orderId
     */
    void refund(String uid, String orderId);

    /**
     * 强制退款
     * @param orderId
     * @param backstageUserId
     */
    void forceRefund(String orderId, Long backstageUserId);

    /**
     * 修改押金金额
     * @param orderId
     * @param afterAmount
     * @param backstageUserName
     * @param backstageUserId
     */
    void updateAmount(String orderId, BigDecimal afterAmount,String backstageUserName,Long backstageUserId);

    /**
     * 查询押金信息
     * @param orderId
     * @return
     */
    OpeDepositOrderPageDto queryLog(String orderId);
}
