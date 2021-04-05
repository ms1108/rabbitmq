package com.mosn.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = {"email.fanout.queue"})
@Service
public class FanoutEmailConsumer {
    @RabbitHandler
    public void receiveMessage(String massage) {
        System.out.println("email fanout 接收到了信息：" + massage);
    }
}
