package com.rent.service.components;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-28 下午 3:35:13
 * @since 1.0
 */
public interface AliPayCapitalNotifyService {

    /**
     * 线上资金授权冻结接口回调
     *
     * @param authNo 支付宝的资金授权订单号
     * @param outOrderNo 商户的授权资金订单号
     * @param operationId 支付宝的资金操作流水号
     * @param outRequestNo 商户本次资金操作的请求流水号
     * @param amount 本次操作冻结的金额，单位为：元（人民币），精确到小数点后两位
     * @param status 资金预授权明细的状态
     * @param payerUserId 付款方支付宝用户号
     * @param gmtTrans 资金授权成功时间
     * @param preAuthType 预授权类型 CREDIT_AUTH(信用预授权)  当返回值为空(普通资金预授权)
     * @param creditAmount 信用冻结金额 单位为：元（人民币）
     * @param fundAmount 自有资金冻结金额 单位为：元（人民币）
     * @return
     */
    void freezeCallBack(String authNo, String outOrderNo, String operationId, String outRequestNo, String amount,
        String status, String payerUserId, String gmtTrans, String preAuthType, String creditAmount, String fundAmount);

    /**
     * 支付成功回调
     *
     * @param tradeNo 支付宝交易号
     * @param outTradeNo 商户订单号
     * @param buyerLogonId 买家支付宝账号
     * @param totalAmount 交易金额
     * @param receiptAmount 实收金额
     * @param gmtPayment 交易支付时间
     * @param fundBillList 交易支付使用的资金渠道
     * @param buyerUserId 买家在支付宝的用户id
     * @return
     */
    void aliPayCallBack(String tradeNo, String outTradeNo, String buyerLogonId, String totalAmount,
        String receiptAmount, String gmtPayment, String fundBillList, String buyerUserId, String tradeStatus);
    /**
     *
     * @param serialNo
     * @param tradeNo
     * @param outTradeNo
     * @param buyerLogonId
     * @param totalAmount
     * @param receiptAmount
     * @param gmtPayment
     * @param fundBillList
     * @param buyerUserId
     * @param tradeStatus
     */
    void alipayTradePagePayCallback(String serialNo, String tradeNo, String outTradeNo, String buyerLogonId, String totalAmount,
                              String receiptAmount, String gmtPayment, String fundBillList, String buyerUserId, String tradeStatus);



    /**
     * 支付创建成功回调
     * @param serialNo 流水号，构造入参时通过扩展参数传递，回调时返回
     * @param tradeNo 支付宝交易号
     * @param outTradeNo 商户订单号
     * @param buyerLogonId 买家支付宝账号
     * @param totalAmount 交易金额
     * @param receiptAmount 实收金额
     * @param gmtPayment 交易支付时间
     * @param fundBillList 交易支付使用的资金渠道
     * @param buyerUserId 买家在支付宝的用户id
     * @param tradeStatus
     * @return
     */
    void aliPayTradeCreateCallBack(String serialNo, String tradeNo, String outTradeNo, String buyerLogonId, String totalAmount,
                              String receiptAmount, String gmtPayment, String fundBillList, String buyerUserId, String tradeStatus);

    /**
     * 分期租金-冻结转支付回调
     *
     * @param outTradeNo 商户订单号
     * @param tradeStatus 交易结果
     * @param buyerUserId 买家支付宝账号
     * @param fundBillList 交易支付使用的资金渠道
     * @param gmtPayment 交易支付时间
     * @param totalAmount 交易金额
     * @param receiptAmount 实收金额
     * @param tradeNo 支付宝交易号
     */
    void stageOrderAlipayCallback(String outTradeNo, String tradeStatus, String buyerUserId, String fundBillList,
        String gmtPayment, String totalAmount, String receiptAmount, String tradeNo);

    /**
     * 资金授权解冻回调
     *
     * @param authNo 支付宝资金授权订单号
     * @param outOrderNo 商户订单号
     * @param operationId 支付宝操作流水号
     * @param outRequestNo 商户本次资金操作的请求流水号
     * @param amount 解冻金额
     * @param status 解冻状态 INIT：初始 PROCESSING：处理中 SUCCESS：成功 FAIL：失败 CLOSED：关闭
     * @return
     */
    void alipayUnFreezeCallBack(String authNo, String outOrderNo, String operationId, String outRequestNo,
        String amount, String status);


}
