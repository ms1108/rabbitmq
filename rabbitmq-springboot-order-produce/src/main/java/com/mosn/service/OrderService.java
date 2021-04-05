package com.mosn.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void makeOrder(String userId, String produceId, int num) {
        //根据商品id查询出是否充足
        //保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单ID：" + orderId);
        //通过消息队列完成消息分发
        /*
        param1 交换机
        param2 路由key，或队列名称
        param3 消息内容
        * */
        String exchangeName = "fanout_order_exchange";
        String routingKey = "";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
    }

    public void makeDirectOrder(String userId, String produceId, int num) {
        //根据商品id查询出是否充足
        //保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单ID：" + orderId);
        //通过消息队列完成消息分发
        /*
        param1 交换机
        param2 路由key，或队列名称
        param3 消息内容
        * */
        String exchangeName = "direct_order_exchange";
        rabbitTemplate.convertAndSend(exchangeName, "email", orderId);
        rabbitTemplate.convertAndSend(exchangeName, "duanxin", orderId);
    }

    public void makeTopicOrder(String userId, String produceId, int num) {
        //根据商品id查询出是否充足
        //保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单ID：" + orderId);
        //通过消息队列完成消息分发
        /*
        param1 交换机
        param2 路由key，或队列名称
        param3 消息内容
        * */
        String exchangeName = "topic_order_exchange";
        String routingKey = "com.duanxin";
        /*
        "#.duanxin.#" duanxin
        "*.email.#" email
        "com.#" sms
         */
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
    }
    public void ttlOrder(String userId, String produceId, int num) {
        //根据商品id查询出是否充足
        //保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单ID：" + orderId);
        //通过消息队列完成消息分发
        /*
        param1 交换机
        param2 路由key，或队列名称
        param3 消息内容
        * */
        String exchangeName = "ttl_order_exchange";
        String routingKey = "ttl";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
    }

    public void ttlMessageOrder(String userId, String produceId, int num) {
        //根据商品id查询出是否充足
        //保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单ID：" + orderId);
        //通过消息队列完成消息分发
        /*
        param1 交换机
        param2 路由key，或队列名称
        param3 消息内容
        * */
        String exchangeName = "ttl_order_exchange";
        String routingKey = "ttlMessage";

        //给消息设置过期时间,当两边都设置了过期时间，以时间小的为准，过期直接移除了，不能设置进入死信
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("5000");
                message.getMessageProperties().setContentEncoding("UTF-8");
                return message;
            }
        };
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId,messagePostProcessor);
    }
}
