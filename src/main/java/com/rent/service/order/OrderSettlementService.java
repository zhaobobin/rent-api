package com.rent.service.order;

import com.rent.common.dto.order.response.UserPaySettlementResponse;
import com.rent.common.dto.order.resquest.PaySettlementCallBackRequest;
import com.rent.common.dto.order.resquest.UserPaySettlementReqDto;


/**
 * 订单结算Service
 *
 * @author xiaoyao
 * @Date 2020-06-18 18:16
 */
public interface OrderSettlementService {

    /**
     * 用户申请修改结算单
     * @param orderId 订单编号
     */
    void userModifySettlementApply(String orderId);

    /**
     * 用户支付结算单
     * @param userPaySettlementReqDto
     * @return
     */
    UserPaySettlementResponse liteUserPaySettlement(UserPaySettlementReqDto userPaySettlementReqDto);

    /**
     * 结算单支付回调
     * @param request
     */
    void paySettlementCallBack(PaySettlementCallBackRequest request);


}