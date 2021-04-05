package com.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {
    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");//虚拟节点
        Connection connection = null;
        Channel channel = null;
        try {
            //创建连接
            connection = connectionFactory.newConnection("生产者");
            //通过连接获取通道
            channel = connection.createChannel();
            final Channel finalChannel = channel;
            finalChannel.basicQos(1);
            channel.basicConsume("queue1", true, new DeliverCallback() {
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println("接受的消息是：" + new String(delivery.getBody(), "UTF-8"));
                    finalChannel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                }
            }, new CancelCallback() {
                public void handle(String s) throws IOException {
                    System.out.println("接受消息失败。。");
                }
            });
            System.out.println("开始接受消息。。。");
            //挂起程序
            System.in.read();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //关闭连接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
