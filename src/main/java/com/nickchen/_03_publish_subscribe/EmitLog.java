package com.nickchen._03_publish_subscribe;
import java.util.Date;

import com.nickchen.utils.EquipFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * @author nickChen
 * @create 2017-07-26 11:27.
 */
public class EmitLog {
    private final static String EXCHANGE_NAME = "ex_log";

    public static void main(String[] args) throws Exception {
        // 创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();
        EquipFactory.equip(factory,"mq");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明转发器和类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout" );

        String message = new Date().toLocaleString()+" : log something";
        // 往转发器上发送消息
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }


        channel.close();
        connection.close();

    }
}
