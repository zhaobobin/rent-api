package com.rent.service.components;


import com.rent.common.dto.backstage.StageOrderWithholdResponse;
import com.rent.common.dto.components.response.AliPayTradePayResponse;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrders;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SuningOpenApiService {

    public static final String KEY_ALGORITHM = "RSA";

    String confirm(String msgId, String code, String uid) throws IOException;

    /**
     * 易付宝 退款
     * @param orderByStages
     * @param userOrders
     * @param remark
     * @param amount
     * @param tradeType
     * @param uid
     */
    void yfbTradeRefund(OrderByStages orderByStages, UserOrders userOrders, String remark, BigDecimal amount,
                        EnumTradeType tradeType, String uid);

    AliPayTradePayResponse payWithYfb(String orderId, String subject,
                                      BigDecimal totalAmount, List<String> periodList, EnumTradeType tradeType) throws IOException;
    /**
     * 支付
     */
    String pay(String contractNo, String amount,
                String orderName, String orderTime,
                String outOrderNo, String goodsName) throws IOException;

    String createRefundOrder(Map<String, Object> orderMap) throws IOException;

    String queryMerchantOrder(Map<String, Object> orderMap) throws IOException;

    /**
     * 签约：银行卡模式
     */
    String sign(Map<String, String> params, String uid) throws IOException;

    Map<String, Object> signVerify(Map<String, Object> bizMap, String url) throws IOException;
}
