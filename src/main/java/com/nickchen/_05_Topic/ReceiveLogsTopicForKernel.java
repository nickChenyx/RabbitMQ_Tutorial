package com.nickchen._05_Topic;
import com.nickchen.utils.EquipFactory;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author nickChen
 * @create 2017-07-26 14:29.
 */
public class ReceiveLogsTopicForKernel {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception
    {
        // 创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();
        EquipFactory.equip(factory,"mq");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String queueName = channel.queueDeclare().getQueue();
        // 只接受 kernel.<关键字> 的信息
        channel.queueBind(queueName, EXCHANGE_NAME, "kernel.*");

        System.out.println(" [*] Waiting for kernel messages. To exit press CTRL+C");

        final Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                String routingKey = envelope.getRoutingKey();
                System.out.println(" [x] Received routingKey = " + routingKey
                        + ",msg = " + message + ".");
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }
}
