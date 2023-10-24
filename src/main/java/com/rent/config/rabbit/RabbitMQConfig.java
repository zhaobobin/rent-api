package com.rent.config.rabbit;

import com.rent.common.constant.QueuesConst;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    // 声明业务Exchange
    @Bean("orderExchange")
    public DirectExchange orderExchange() {
        return new DirectExchange("order_exchange", true, false);
    }

    // 声明死信Exchange
    @Bean("deadExchange")
    public DirectExchange deadExchange() {
        return new DirectExchange("dead_exchange", true, false);
    }

    @Bean("orderExpiration")
    public Queue orderExpiration() {
        Map<String, Object> args = new HashMap<>(3);
//       x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", "dead_exchange");
//       x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", "order.dead.expiration");
        args.put("x-message-ttl", 60 * 1000 * 30);
        return QueueBuilder.durable(QueuesConst.orderExpiration).withArguments(args).build();
    }

    @Bean("orderSmsSuccess")
    public Queue orderSmsSuccess() {
        Map<String, Object> args = new HashMap<>(3);
//       x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", "dead_exchange");
//       x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", "order.dead.smsSuccess");
        //队列的消息 TTL 存活时间 (单位ms)
        args.put("x-message-ttl", 60 * 1000 * 3);
        return QueueBuilder.durable(QueuesConst.orderSmsSuccess).withArguments(args).build();
    }

    @Bean("orderSubmit")
    public Queue orderSubmit() {
        return QueueBuilder.durable(QueuesConst.orderSubmit).build();
    }

    @Bean("orderDelivery")
    public Queue orderDelivery() {
        return QueueBuilder.durable(QueuesConst.orderDelivery).build();
    }


    // 声明死信队列A
    @Bean("orderDead")
    public Queue orderDead() {
        return QueueBuilder.durable(QueuesConst.orderDead).build();
    }

    // 声明死信队列绑定关系
    @Bean
    public Binding deadLetterBindingOrderDead(@Qualifier("orderDead") Queue queue,
                                              @Qualifier("deadExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueuesConst.orderDead + ".expiration");
    }

    @Bean
    public Binding deadLetterBindingOrderSmsSuccess(@Qualifier("orderDead") Queue queue,
                                                    @Qualifier("deadExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueuesConst.orderDead + ".smsSuccess");
    }


    @Bean
    public Binding businessBindingOrderExpiration(@Qualifier("orderExpiration") Queue queue,
                                                  @Qualifier("orderExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueuesConst.orderExpiration);
    }

    @Bean
    public Binding businessBindingOrderSmsSuccess(@Qualifier("orderSmsSuccess") Queue queue,
                                                  @Qualifier("orderExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueuesConst.orderSmsSuccess);
    }

    @Bean
    public Binding businessBindingOrderSubmit(@Qualifier("orderSubmit") Queue queue,
                                              @Qualifier("orderExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueuesConst.orderSubmit);
    }

    @Bean
    public Binding businessBindingOrderDelivery(@Qualifier("orderDelivery") Queue queue,
                                                @Qualifier("orderExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QueuesConst.orderDelivery);
    }


}
