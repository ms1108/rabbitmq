package com.mosn.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = {"duanxin.fanout.queue"})
@Service
public class FanoutDuanxinConsumer {
    @RabbitHandler
    public void receiveMessage(String massage) {
        System.out.println("duanxin fanout 接收到了信息：" + massage);
    }
}
