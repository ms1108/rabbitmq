package com.routing;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //创建连接工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("admin");
            connectionFactory.setPassword("admin");
            connectionFactory.setVirtualHost("/");//虚拟节点

            final String queueName = Thread.currentThread().getName();
            Connection connection = null;
            Channel channel = null;
            try {
                //创建连接
                connection = connectionFactory.newConnection("生产者");
                //通过连接获取通道
                channel = connection.createChannel();
                channel.basicConsume(queueName, true, new DeliverCallback() {
                    @Override
                    public void handle(String s, Delivery delivery) throws IOException {
                        System.out.println(queueName + ":接受的消息是：" + new String(delivery.getBody(), "UTF-8"));
                    }
                }, new CancelCallback() {
                    @Override
                    public void handle(String s) throws IOException {
                        System.out.println(queueName + ":接受消息失败。。");
                    }
                });
                System.out.println(queueName + ":开始接受消息。。。");
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
    };

    public static void main(String[] args) {
        new Thread(runnable,"queue1").start();
        new Thread(runnable,"queue2").start();
    }
}
