# Topic

这是[官方文档](https://www.rabbitmq.com/tutorials/tutorial-five-java.html)中第五章对应的代码。

运行方法：
- 直接运行 `main` 方法即可。
- `EmitLog` 为生产者，运行一次后自动结束。
- `ReceiveLogsTopicFor*` 为消费者，可运行多个消费者。

本章采用类型为 Topic 的 Exchange，通过 routeKey 发送消息。
> 任何发送到Topic Exchange的消息都会被转发到所有关心RouteKey中指定话题的Queue上
> 1. 这种模式需要RouteKey，要提前绑定Exchange与Queue。
> 2. 如果Exchange没有发现能够与RouteKey匹配的Queue，则会抛弃此消息。
> 3. 在进行绑定时，要提供一个该队列关心的主题，如“#.log.#”表示该队列关心所有涉及log的消息(一个RouteKey为”MQ.log.error”的消息会被转发到该队列)。
> 4. “#”表示0个或若干个关键字，“*”表示一个关键字。如“log.*”能与“log.warn”匹配，无法与“log.warn.timeout”匹配；但是“log.#”能与上述两者匹配。

声明一个类型为 Topic 的Exchange：
```java
channel.exchangeDeclare(EXCHANGE_NAME, "topic");
```
发送消息时绑定 routeKey ：
```java
channel.basicPublish(EXCHANGE_NAME, routing_key, null, msg
                    .getBytes());
```
接收消息时绑定 routeKey：
```java
channel.queueBind(queueName, EXCHANGE_NAME, "*.critical");
```