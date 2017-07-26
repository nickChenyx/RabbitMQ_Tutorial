package com.nickchen._02_workqueues;
import com.nickchen.utils.EquipFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
/**
 * @author nickChen
 * @create 2017-07-26 9:19.
 */
public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        EquipFactory.equip(factory,"mq");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;  // 声明为持久化的队列
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);

        String[] arg = {"i","am","robot"};
        String message = getMessage(arg);
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,  //　设置为可持久化的消息体
                    message.concat(genStr(i,".")).getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message.concat(genStr(i,".")) + "'");
        }

        channel.close();
        connection.close();
    }

    private static String genStr(int length, String c){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <length; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1)
            return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
