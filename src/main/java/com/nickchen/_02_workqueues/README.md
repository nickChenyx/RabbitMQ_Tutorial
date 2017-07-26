# Work Queues

这是[官方文档](https://www.rabbitmq.com/tutorials/tutorial-two-java.html)中第二章对应的代码。

运行方法：
- 直接运行 `main` 方法即可。
- `NewTask` 为生产者，运行一次后自动结束。
- `Worker` 为消费者，持续监听队列，可运行多个消费者，观察消息分发。

本节有讲到的相关知识点：

- Fair dispatch | 使用 `channel.basicQos()` 设置队列在单位时间内处理的消息数
- Autoack | `channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);` | 当 autoAck 为 true 时为自动应答ack；为 false 关闭自动应答，这样可以防止消费者在异常退出后，消息体可以转移到另一个消费者处理。
- Message durable | 消息持久化

**队列持久化**
```java
boolean durable = true;  // 声明为持久化的队列
channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
```

**消息体持久化**
```java
 channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,  //　设置为可持久化的消息体
                    message.concat(genStr(i,".")).getBytes("UTF-8"));
```