package com.mosn.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = {"sms.direct.queue"})
@Service
public class DirectSmsConsumer {
    @RabbitHandler
    public void receiveMessage(String massage) {
        System.out.println("sms direct 接收到了信息：" + massage);
    }
}
