package com.mosn.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitMqConfig {
    //声明direct模式的交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_order_exchange", true, false);
    }

    //声明队列
    @Bean
    public Queue directSmsQueue() {
        return new Queue("sms.direct.queue", true);
    }

    @Bean
    public Queue directDuanxinQueue() {
        return new Queue("duanxin.direct.queue", true);
    }

    @Bean
    public Queue directEmailQueue() {
        return new Queue("email.direct.queue", true);
    }

    //完成交换机和队列的绑定关系,需要绑定路由key
    @Bean
    public Binding directSmsBinding() {
        return BindingBuilder.bind(directExchange()).to(directExchange()).with("sms");
    }

    @Bean
    public Binding directDuanxinBinding() {
        return BindingBuilder.bind(
                directDuanxinQueue()).to(directExchange()).with("duanxin");
    }

    @Bean
    public Binding directEmailBinding() {
        return BindingBuilder.bind(directEmailQueue()).to(directExchange()).with("email");
    }

}
