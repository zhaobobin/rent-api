package com.rent.listener;


import com.rabbitmq.client.Channel;
import com.rent.common.constant.QueuesConst;
import com.rent.common.dto.mq.OrderDeadMessage;
import com.rent.common.enums.order.EnumOrderCloseType;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.dao.order.UserOrdersDao;
import com.rent.model.order.UserOrders;
import com.rent.service.order.OrderContractService;
import com.rent.service.order.SwipingActivityOrderService;
import com.rent.service.order.UserOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 用户下单
 * @author zhaowenchao
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderMqListener {

    private final UserOrdersService userOrdersService;
    private final UserOrdersDao userOrdersDao;
    private final OrderContractService orderContractService;
    private final SwipingActivityOrderService swipingActivityOrderService;

    @RabbitListener(queues = QueuesConst.orderSubmit)
    public void orderSubmit(String orderId, Message message, Channel channel) throws Exception {
        log.info("用户下单处理:生成PDF，订单编号:{}",orderId);
        long msgTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(msgTag,false);
        //生成订单合同 不盖章
        orderContractService.generateOrderContractUnsignedFile(orderId);
        //判断是否是刷单商品订单并保存
        swipingActivityOrderService.saveOrUpdateSwiping(orderId);
    }

    @RabbitListener(queues = QueuesConst.orderDelivery)
    public void orderDelivery(String orderId, Message message, Channel channel) throws Exception {
        log.info("订单发货处理:生成PDF，订单编号:{}",orderId);
        long msgTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(msgTag,false);
        //生成订单合同 不盖章 再生成一次的原因是有可能订单会转单，商家名称变了
        orderContractService.generateOrderContractUnsignedFile(orderId);
    }


    /**
     * 监听订单死信队列
     * @param orderDeadMessage
     * @param message
     * @param channel
     * @throws Exception
     */
    @RabbitListener(queues = QueuesConst.orderDead)
    public void dealOrderDeadMessage(OrderDeadMessage orderDeadMessage, Message message, Channel channel) throws Exception {

        log.info("死信处理:订单编号:{},操作：{}",orderDeadMessage.getOrderId(),orderDeadMessage.getOperate().getMsg());
        long msgTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(msgTag,false);

        String orderId = orderDeadMessage.getOrderId();
        switch (orderDeadMessage.getOperate()){
            case EXPIRATION:
                dealExpiration(orderId);
                break;
            case ORDER_SMS_SUCCESS:
                dealOrderSmsSuccess(orderId);
                break;
            default:
                break;
        }
    }

    private void dealExpiration(String orderId){
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if (EnumOrderStatus.WAITING_PAYMENT.equals(userOrders.getStatus())) {
            // 将待支付的订单改为已取消(超时未支付)
            userOrdersService.closeOrder(orderId, EnumOrderCloseType.OVER_TIME_PAY, EnumOrderCloseType.OVER_TIME_PAY.getDescription());
        }
    }

    private void dealOrderSmsSuccess(String orderId){
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if (null != userOrders && EnumOrderStatus.TO_AUDIT.equals(userOrders.getStatus())) {
            userOrdersService.smsSuccess(orderId, userOrders.getShopId());
        }
    }


}
