package com.nickchen._04_Routing;

import com.nickchen.utils.EquipFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.Random;
import java.util.UUID;

/**
 * @author nickChen
 * @create 2017-07-26 13:57.
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "ex_logs_direct";
    // 模拟日志的三个级别，用来作为routeKey
    private static final String[] SEVERITIES = { "info", "warning", "error" };

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        EquipFactory.equip(factory,"mq");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明转发器的类型为Direct
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        for (int i = 0; i < 6; i++)
        {
            String severity = getSeverity();
            String message = severity + "_log :" + UUID.randomUUID().toString();

            channel.basicPublish(EXCHANGE_NAME,
                    severity,  // 随机一个 binding_key
                    null,
                    message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }

    /**
     * 随机产生一种日志类型
     * @return
     */
    private static String getSeverity()
    {
        Random random = new Random();
        int ranVal = random.nextInt(3);
        return SEVERITIES[ranVal];
    }
}
