package com.rent.service.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.request.AddEmergencyContactReq;
import com.rent.common.dto.order.ChannelUserOrdersDto;
import com.rent.common.dto.order.UserOrdersDto;
import com.rent.common.dto.order.UserOrdersReqDto;
import com.rent.common.dto.order.response.OrderCloseResponse;
import com.rent.common.dto.order.response.OrderFreezeAgainResponse;
import com.rent.common.dto.order.resquest.ChannelUserOrdersReqDto;
import com.rent.common.dto.order.resquest.OrderGiveBackRequest;
import com.rent.common.enums.order.EnumOrderCloseType;
import com.rent.common.enums.order.EnumOrderType;
import com.rent.common.enums.order.PaymentMethod;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Service
 *
 * @author xiaoyao
 * @Date 2020-06-07 19:50
 */
public interface UserOrdersService {


    /**
     * <p>
     * 根据条件查询一条记录
     * </p>
     *
     * @param request 实体对象
     * @return UserOrders
     */
    UserOrdersDto queryUserOrdersDetail(UserOrdersReqDto request);

    Boolean addEmergencyContact(AddEmergencyContactReq request);


    OrderFreezeAgainResponse liteOrderFreezeAgain(String orderId, Long templateId);

    /**
     * @param orderId 订单编号
     * @param closeType 关闭类型
     * @param cancelReason 取消原因
     * @return
     */
    OrderCloseResponse closeOrder(String orderId, EnumOrderCloseType closeType, String cancelReason);

    /**
     * 更新订单状态为支付中
     *
     * @param orderId
     * @param payerUserId
     */
    void userOrdersPaying(String orderId, String payerUserId);

    /**
     * 已支付关闭订单
     *
     * @param orderId 订单id
     * @param closeType 关单类型
     * @param cancelReason 取消原因
     */
    void payedCloseOrder(String orderId, EnumOrderCloseType closeType, String cancelReason);

    /**
     * 订单支付成功处理
     * @param orderType 订单类型
     * @param orderId 订单编号
     * @param tradeNo 支付宝交易号
     * @param paymentTime 支付时间
     * @param outTradeNo 商户订单号
     * @param totalAmount 支付金额
     * @param payerUserId 付款方支付宝userId
     */
    void paySuccessUserOrderHandle(EnumOrderType orderType, String orderId, String tradeNo, Date paymentTime, String outTradeNo,
                                   BigDecimal totalAmount, String payerUserId, PaymentMethod paymentMethod);







    /**
     * 首期支付失败关单
     * @param orderId
     * @param closeType
     * @param cancelReason
     */
    void payFailedCloseOrder(String orderId, EnumOrderCloseType closeType, String cancelReason);

    /**
     * 订单商品归还提交
     * @param request 请求类
     */
    void userOrderBackSubmitConfirm(OrderGiveBackRequest request);

    /**
     * 查询商品的销量
     *
     * @param productId
     * @return
     */
    Integer getProductSales(String productId);

    void smsSuccess(String orderId, String shopId);

    /**
     * 查询渠道用户订单
     * @param request
     * @return
     */
    Page<ChannelUserOrdersDto> queryChannelUserOrdersPage(ChannelUserOrdersReqDto request);


    String exportChannelUserOrdersPage(ChannelUserOrdersReqDto request);
}