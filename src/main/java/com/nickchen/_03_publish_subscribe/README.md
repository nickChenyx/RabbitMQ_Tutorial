# Publish / Subscribe

这是[官方文档](https://www.rabbitmq.com/tutorials/tutorial-three-java.html)中第三章对应的代码。

运行方法：
- 直接运行 `main` 方法即可。
- `EmitLog` 为生产者，运行一次后自动结束。
- `RecvLog` 为消费者，可运行多个消费者。

### Exchanges

前面两章使用了队列来传输消息，本章引入转发器(exchange)概念，通过转发器来分发生产者的消息，通过 binding 将队列绑定到转发器上，消息会统一发布至消费者。

RabbitMQ提供的转发器有如下几个：

- Direct 
> 默认的转发器，消息传递时需要一个RouteKey(即队列名称)，任何消息都会直接发送到RouteKey指定的Queue上
- Topic
> [引用链接](http://blog.csdn.net/csethcrm/article/details/51673050)   
任何发送到Topic Exchange的消息都会被转发到所有关心RouteKey中指定话题的Queue上
> 1. 这种模式需要RouteKey，要提前绑定Exchange与Queue。
> 2. 如果Exchange没有发现能够与RouteKey匹配的Queue，则会抛弃此消息。
> 3. 在进行绑定时，要提供一个该队列关心的主题，如“#.log.#”表示该队列关心所有涉及log的消息(一个RouteKey为”MQ.log.error”的消息会被转发到该队列)。
> 4. “#”表示0个或若干个关键字，“*”表示一个关键字。如“log.*”能与“log.warn”匹配，无法与“log.warn.timeout”匹配；但是“log.#”能与上述两者匹配。
- Headers
- Fanout

本章使用最后一个 `fanout` ，声明转发器类型的代码：

```java
// 第一个参数为exchange的名称，第二个参数为exchange的类型
channel.exchangeDeclare("logs","fanout");
```

`fanout` 类型转发器特别简单，把所有它介绍到的消息，广播到所有它所知道的队列。

声明队列绑定到转发器：*此处使用了临时队列*
```java
channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
// 创建一个非持久的、唯一的且自动删除的队列
String queueName = channel.queueDeclare().getQueue();
// 为转发器指定队列，设置binding
channel.queueBind(queueName, EXCHANGE_NAME, "");
```



