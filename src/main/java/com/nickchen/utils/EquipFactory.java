package com.nickchen.utils;

import com.rabbitmq.client.ConnectionFactory;

import java.util.ResourceBundle;

/**
 * @author nickChen
 * @create 2017-07-26 10:26.
 */
public class EquipFactory {
    public static ConnectionFactory equip(ConnectionFactory factory,String propsName){
        ResourceBundle resource = ResourceBundle.getBundle("mq");
        factory.setHost(resource.getString("host"));
        factory.setPort(Integer.valueOf(resource.getString("port")));
        factory.setUsername(resource.getString("username"));
        factory.setPassword(resource.getString("password"));
        return factory;
    }
}
