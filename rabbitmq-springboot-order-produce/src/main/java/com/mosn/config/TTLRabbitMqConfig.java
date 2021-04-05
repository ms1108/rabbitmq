package com.mosn.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//这些声明放在消费者或者生产者都可以，但是放在消费者这边会更好，
// 因为生产者启动时，即使交换机或者队列还没声明，启动也不会报错，
// 而消费者@RabbitListener(queues = {"duanxin.fanout.queue"})指定的队列还不存在，就会报错
// 因为消费者直接和mq打交道
@Configuration
public class TTLRabbitMqConfig {
    //声明direct模式的交换机
    @Bean
    public DirectExchange ttlDirectExchange() {
        return new DirectExchange("ttl_order_exchange", true, false);
    }

    //想要修改队列的配置需要删除原有的，在生产环境不能直接删除，要重新生成其他队列
    //声明队列，整个队列的消息都设置了过期时间
    @Bean
    public Queue ttlDirectSmsQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 5000);//int类型
        //设置队列的最大值，超过的部分直接进入死信队列
        args.put("x-max-length", 5);
        //设置死信队列
        args.put("x-dead-letter-exchange", "dead_order_exchange");
        //fanout模式没有路由key，可以不设置这个，direct模式需要设置
        args.put("x-dead-letter-routing-key", "dead");
        return new Queue("ttl.direct.queue", true, false, false, args);
    }

    //对某条信息条件ttl过期时间
    @Bean
    public Queue ttlMessageDirectSmsQueue() {
        return new Queue("ttl.message.direct.queue", true);
    }

    //完成交换机和队列的绑定关系,需要绑定路由key
    @Bean
    public Binding ttlDirectSmsBinding() {
        return BindingBuilder.bind(
                ttlDirectSmsQueue()).to(ttlDirectExchange()).with("ttl");
    }

    @Bean
    public Binding ttlMessageDirectSmsBinding() {
        return BindingBuilder.bind(
                ttlMessageDirectSmsQueue()).to(ttlDirectExchange()).with("ttlMessage");
    }
}
