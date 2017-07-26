package com.nickchen._01_helloworld;
import com.nickchen.utils.EquipFactory;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.text.MessageFormat;
import java.util.concurrent.TimeoutException;

/**
 * @author nickChen
 * @create 2017-07-24 13:22.
 */
public class Send {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] argv)
            throws java.io.IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        EquipFactory.equip(factory,"mq");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!{0}";
        for (int i = 0; i < 20; i++) {
            String msg = MessageFormat.format(message,i);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + msg + "'");
        }
        channel.close();
        connection.close();
    }
}
