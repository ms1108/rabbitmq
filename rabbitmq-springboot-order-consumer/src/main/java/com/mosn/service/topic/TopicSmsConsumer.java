package com.mosn.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;
//建议使用配置类的方式，因为扩展性更强
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "ssm.topic.queue",declare = "true" ,autoDelete = "false"),
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
        key = "com.#"
))
@Service
public class TopicSmsConsumer {
    @RabbitHandler
    public void receiveMessage(String massage) {
        System.out.println("sms topic 接收到了信息：" + massage);
    }
}
