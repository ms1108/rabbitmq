package com.mosn.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

//通过注解声明交换机和队列
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "duanxin.topic.queue",declare = "true" ,autoDelete = "false"),
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
        key = "#.duanxin.#"
))
@Service
public class TopicDuanxinConsumer {
    @RabbitHandler
    public void receiveMessage(String massage) {
        System.out.println("duanxin topic 接收到了信息：" + massage);
    }
}
