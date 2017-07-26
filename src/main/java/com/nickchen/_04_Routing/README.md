# Routing

这是[官方文档](https://www.rabbitmq.com/tutorials/tutorial-four-java.html)中第四章对应的代码。

运行方法：
- 直接运行 `main` 方法即可。
- `EmitLog` 为生产者，运行一次后自动结束。
- `RecvLog` 为消费者，可运行多个消费者。

本章采用类型为 Direct 的 Exchange，通过 bindingKey 来完成消息的转发。  
```java
channel.exchangeDeclare(EXCHANGE_NAME, "direct");
```

发送消息时可以指定routeKey(队列名)，转发器和接收队列可以设置bindingKey，接收者接收 routeKey 与 bindingKey 均匹配的消息。

```java
channel.basicPublish(EXCHANGE_NAME,
                    severity,  // 随机一个 binding_key
                    null,
                    message.getBytes("UTF-8"));
```

接收消息：

```java
channel.queueBind(queueName, EXCHANGE_NAME, severity);
```