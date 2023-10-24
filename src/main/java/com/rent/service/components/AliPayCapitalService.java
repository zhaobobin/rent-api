package com.rent.service.components;

import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.rent.common.dto.components.request.*;
import com.rent.common.dto.components.response.*;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.EnumOrderType;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-23 下午 5:44:48
 * @since 1.0
 */
public interface AliPayCapitalService {

    /**
     * 资金预授权
     * https://opendocs.alipay.com/open/02f912?scene=common&pathHash=b2e036c3
     * alipay.fund.auth.order.app.freeze(线上资金授权冻结接口)
     *
     * @param orderId         订单id
     * @param uid             uid
     * @param amount          冻结金额
     * @param skuId           skuId
     * @param productId       商品id
     * @param freezeAgainFlag 是否重新支付标识
     * @param orderType       订单类型
     * @param rentAmount      总租金 元
     * @param rentPeriod      天数
     */
    AliPayFreezeResponse aliPayFreeze(String orderId, String uid, BigDecimal amount, Long skuId, String productId,
                                      Integer freezeAgainFlag, EnumOrderType orderType, BigDecimal rentAmount, Integer rentPeriod);

    /**
     * 冻结转支付
     * https://opendocs.alipay.com/open/02f912?scene=common&pathHash=b2e036c3
     * alipay.trade.pay(统一收单交易支付接口)
     *
     * @param orderId     订单编号
     * @param outTradeNo  商户订单号
     * @param authCode    支付授权码
     * @param subject     订单标题
     * @param notifyUrl   回调地址
     * @param totalAmount 订单金额
     * @param payerUserId 付款方id
     * @param periodList  期次
     * @param tradeType
     * @return 结果
     */
    AliPayTradePayResponse aliPayTradePay(String orderId, String outTradeNo, String authCode, String subject,
                                          String notifyUrl, BigDecimal totalAmount, String payerUserId, List<String> periodList, EnumTradeType tradeType);

    /**
     * 分期账单支付
     * https://opendocs.alipay.com/open/02f912?scene=common&pathHash=b2e036c3
     * alipay.trade.pay(统一收单交易支付接口)
     *
     * @param orderId     订单号
     * @param subject     订单标题
     * @param totalAmount 金额
     * @param periodList  期次
     * @param tradeType
     * @return
     */
    AliPayTradePayResponse orderByStageAliPayTradePay(String orderId, String subject,
                                                      BigDecimal totalAmount, List<String> periodList, EnumTradeType tradeType);

    /**
     * 查询资金授权接口
     * https://opendocs.alipay.com/open/02f912?scene=common&pathHash=b2e036c3
     * alipay.fund.auth.operation.detail.query(资金授权操作查询接口)
     *
     * @param authNo       授权编号
     * @param outOrderNo   商户订单号
     * @param outRequestNo 请求流水号
     * @param channelId
     * @return 冻结详情
     */
    AliPayOperationDetailResponse alipayOperationDetailQuery(String authNo, String outOrderNo, String outRequestNo, String channelId);

    /**
     * 资金预授权解冻
     * https://docs.open.alipay.com/api_28/alipay.fund.auth.order.unfreeze
     * alipay.fund.auth.order.unfreeze(资金授权解冻接口)
     *
     * @param unfreezeRequest 解冻请求类
     * @return 出参
     */
    AlipayUnfreezeResponse alipayOrderUnfreeze(AlipayUnfreezeRequest unfreezeRequest);

    /**
     * 网站支付
     * https://opendocs.alipay.com/open/270/105899
     * alipay.trade.page.pay(统一收单下单并支付页面接口)
     *
     * @param request 入参
     * @return 出参
     */
    AlipayAppPayResponse alipayTradePagePay(AliPayTradeAppPageRequest request);

    /**
     * 用户主动支付拉起收银台
     * https://opendocs.alipay.com/open/02ekfj?scene=common
     * alipay.trade.create(统一收单交易创建接口)
     *
     * @param request 入参
     * @return 出参
     */
    AliPayTradeCreateResponse alipayTradeCreate(AliPayTradeCreateRequest request);

    /**
     * 退款接口
     * 统一收单交易退款接口
     * https://opendocs.alipay.com/open/02ekfk?pathHash=b45b14f7
     *
     * @param request 入参
     * @return 出参
     */
    AlipayRefundResponse alipayTradeRefund(AlipayOrderRefundRequest request);


    /**
     * 转账到支付宝账户
     * https://opendocs.alipay.com/open/309/106235?ref=api
     * alipay.fund.trans.uni.transfer(单笔转账接口)
     *
     * @param outBizNo      外部订单号
     * @param amount        金额
     * @param identity      转入账户名称（收款方支付宝登陆账号 邮箱|手机号）->  例如：13253536897@163.com
     * @param name          转入账户用户真实姓名 ->  例如：赵文超
     * @param remark        转账备注
     * @param payerShowName 付款方显示名称 -> 例如：租物租
     * @return 失败原因 为空表示支付成功
     */
    AliPayTransferRespModel transfer(String outBizNo, BigDecimal amount, String identity, String name, String remark,
                                     String payerShowName, String userId);

    /**
     * 预授权代扣同步违约守约信息
     * https://opendocs.alipay.com/open/03w0al
     * alipay.trade.orderinfo.sync(支付宝订单信息同步接口)
     *
     * @param orderId      订单号
     * @param tradeNo      支付宝交易号，和商户订单号不能同时为空
     * @param outRequestNo 标识一笔交易多次请求，同一笔交易多次信息同步时需要保证唯一
     * @param bizType      交易信息同步对应的业务类型 支付宝约定；信用授权场景下传CREDIT_AUTH
     *                     信用代扣场景下传CREDIT_DEDUCT
     * @return
     */
    void alipayTradeOrderInfoSync(String orderId, String tradeNo, String outRequestNo, String bizType,
                                  String orderBizInfo);

    /**
     * 预授权代扣同步违约守约信息
     * https://opendocs.alipay.com/open/03w0al
     * alipay.trade.orderinfo.sync(支付宝订单信息同步接口)
     *
     * @param req
     * @return
     */
    void alipayTradeOrderInfoSync(AliPayTradeSyncReq req);

    /**
     * 根据订单号查询资金授权信息
     * https://opendocs.alipay.com/open/02f912?scene=common&pathHash=b2e036c3
     * alipay.fund.auth.operation.detail.query(资金授权操作查询接口)
     *
     * @param orderId   订单编号
     * @param channelId 订单所属小程序
     * @return
     */
    AliPayOperationDetailResponse alipayOperationDetailQuery(String orderId, String channelId);


    /**
     * 查询订单交易结果
     * https://opendocs.alipay.com/open/02loyv
     * alipay.trade.query(统一收单交易查询)
     *
     * @param request
     * @return
     */
    AlipayTradeQueryResponse alipayTradeQuery(AlipayTradePayQueryRequest request);


    /**
     * 统一收单交易关闭接口
     * https://opendocs.alipay.com/open/02loyv
     * alipay.trade.close(统一收单交易查询)
     *
     * @param outTradeNo
     * @return
     */
    AlipayTradeCloseResponse alipayTradeClose(String outTradeNo, String tradeNo);


}
