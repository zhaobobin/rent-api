package com.rent.common.constant;


public class AlipayCallback {

    /**
     * 支付宝回调响应结果 成功
     */
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    /**
     * 付宝预授权冻结回调请求值 成功状态
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 支付宝预授权冻结回调请求参数名称
     */
    public static class FreezeCallbackParamName {
        public static final String AUTH_NO = "auth_no";
        public static final String OUT_ORDER_NO = "out_order_no";
        public static final String OPERATION_ID = "operation_id";
        public static final String OUT_REQUEST_NO = "out_request_no";
        public static final String AMOUNT = "amount";
        public static final String STATUS = "status";
        public static final String PAYER_USER_ID = "payer_user_id";
        public static final String PAYER_OPEN_ID = "payer_open_id";
        public static final String GMT_TRANS = "gmt_trans";
        public static final String PRE_AUTH_TYPE = "pre_auth_type";
        public static final String CREDIT_AMOUNT = "credit_amount";
        public static final String FUND_AMOUNT = "fund_amount";

    }



    /**
     * 支付宝回调请求参数名称
     */
    public static class TradePayCallbackParamName {
        public static final String TRADE_NO = "trade_no";
        public static final String OUT_TRADE_NO = "out_trade_no";
        public static final String TOTAL_AMOUNT = "total_amount";
        public static final String RECEIPT_AMOUNT = "receipt_amount";
        public static final String GMT_PAYMENT = "gmt_payment";
        public static final String FUND_BILL_LIST = "fund_bill_list";
        public static final String BUYER_ID = "buyer_id";
        public static final String TRADE_STATUS = "trade_status";
    }

    /**
     * 支付宝回调请求参数名称
     *                     params.get("auth_no"),
     *                     params.get("out_order_no"),
     *                     params.get("operation_id"),
     *                     params.get("out_request_no"),
     *                     params.get("amount"),
     *                     params.get("status")
     */
    public static class UnfreezeCallbackParamName {
        public static final String AUTH_NO = "auth_no";
        public static final String OUT_ORDER_NO = "out_order_no";
        public static final String OPERATION_ID = "operation_id";
        public static final String OUT_REQUEST_NO = "out_request_no";
        public static final String AMOUNT = "amount";
        public static final String STATUS = "status";
    }


    /**
     * 支付宝 预授权转支付 失败同步订单给支付宝，添加用户不良信用记录 阈值
     * 预授权转支付失败15次。上传不良信用记录。固定值，不可配置
     */
    public static final int TRADE_FAILED_SYNC_TIMES = 15;

    public static final String TRADE_SYNC_BIZ_INFO_COMPLETE = "{\"status\":\"COMPLETE\"}";
    public static final String TRADE_SYNC_BIZ_INFO_VIOLATED = "{\"status\":\"VIOLATED\"}";

}
