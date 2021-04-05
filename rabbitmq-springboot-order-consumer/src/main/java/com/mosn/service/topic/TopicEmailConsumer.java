package com.mosn.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "email.topic.queue",declare = "true" ,autoDelete = "false"),
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
        key = "*.email.#"
))
@Service
public class TopicEmailConsumer {
    @RabbitHandler
    public void receiveMessage(String massage) {
        System.out.println("email topic 接收到了信息：" + massage);
    }
}
