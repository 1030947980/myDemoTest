package com.example.rabbitmq.consume;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;

/**
 * Created by Bing on 2023/4/14.
 */
@Component
public class SpringAMQTListener {

    //简单队列
    @RabbitListener(queues = "simple.queue")
    public void simpleQueueListener(String msg){
        System.out.println("消费者接受到simple queue的消息：【"+msg+"】");
    }

    //工作队列
    @RabbitListener(queues = "work.queue")
    public void workQueueListener(String msg) throws InterruptedException {
        System.out.println("消费者1接受到work queue的消息：【"+msg+"】"+ LocalTime.now());
        Thread.sleep(20);
    }

    @RabbitListener(queues = "work.queue")
    public void workQueueListener2(String msg) throws InterruptedException {
        System.err.println("消费者2接受到work queue的消息：【"+msg+"】"+LocalTime.now());
        Thread.sleep(200);
    }

    // 广播 FanoutExchange 交换机
    @RabbitListener(queues = "founout.queue1")
    public void finoutQueue1Listener(String msg){
        System.out.println("消费者1接受到founout.queue1的消息：【"+msg+"】");
    }
    @RabbitListener(queues = "founout.queue2")
    public void finoutQueue2Listener(String msg){
        System.out.println("消费者2接受到founout.queue2的消息：【"+msg+"】");
    }
    @RabbitListener(queues = "founout.queue3")
    public void finoutQueue3Listener(String msg){
        System.out.println("消费者1接受到founout.queue3的消息：【"+msg+"】");
    }
    @RabbitListener(queues = "founout.queue4")
    public void finoutQueue4Listener(String msg){
        System.out.println("消费者2接受到founout.queue4的消息：【"+msg+"】");
    }

    //DirectExchahnge 路由交换机
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "example.direct",type = ExchangeTypes.DIRECT),
            key = {"red","blue"}
    ))
    public void directQueue1Listener(String msg){
        System.out.println("消费者1接受到direct.queue1的消息：【"+msg+"】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "example.direct",type = ExchangeTypes.DIRECT),
            key = {"red","yellow"}
    ))
    public void directQueue2Listener(String msg){
        System.out.println("消费者2接受到direct.queue2的消息：【"+msg+"】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "example.topic",type = ExchangeTypes.TOPIC),
            key = "china.#"
    ))
    public void topicQueue1Listener(String msg){
        System.out.println("消费者1接受到topic.queue1的消息：【"+msg+"】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "example.topic",type = ExchangeTypes.TOPIC),
            key = "#.news"
    ))
    public void topicQueue2Listener(String msg){
        System.out.println("消费者2接受到topic.queue2的消息：【"+msg+"】");
    }
}
