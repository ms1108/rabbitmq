package com.mosn.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeadRabbitMqConfig {
    //声明direct模式的交换机
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange("dead_order_exchange", true, false);
    }

    //声明队列
    @Bean
    public Queue deadQueue() {
        return new Queue("dead.direct.queue", true);
    }

       //完成交换机和队列的绑定关系,需要绑定路由key
    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("dead");
    }

}
