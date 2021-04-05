package com.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
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
            //通过通道创建交换机、声明队列、绑定关系、路由key、发送消息和接受消息
            //声明交换机
            String exchangeName = "exchangeName";
            String exchangeType = "exchangeType";
            channel.exchangeDeclare(exchangeName, exchangeType, true);
            String queueName = "queue1";
            /*声明队列
             * param1 队列名称
             * param2 是否持久化 durable
             * param3 排他性，是否独占队列
             * param4 是否自动删除，随着最后一个消费者，消费完毕后，是否把队列删除
             * param5 携带附加参数
             * */
            channel.queueDeclare(queueName, false, false, false, null);
            //交换机和队列绑定
            String rootingKey = "rootingKey";
            channel.queueBind(queueName, exchangeName, rootingKey);

            //消息内容
            String msg = "hello rabbit";
            //发送消息给队列
            channel.basicPublish("", queueName, null, msg.getBytes());
            System.out.println("消息已发送。。。");
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
