package com.example.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bing on 2023/4/14.
 */
@Component
public class RabbitMqProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 简单队列
     * @param queueName
     * @param message
     */
    public void SimpleQueueSend(String queueName,String message){
        rabbitTemplate.convertAndSend(queueName,message);
    }

    /**
     * 工作队列
     * @param queueName
     * @param message
     * @throws InterruptedException
     */
    public void workQueueSend(String queueName,String message) throws InterruptedException {
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }
    }

    /**
     * 广播
     * @param exchangeName 交换机名称
     * @param message 消息
     */
    public void fanoutExchangeSend(String exchangeName,String message) {
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    /**
     * 路由
     * @param exchangeName
     * @param routerKey
     * @param message
     */
    public void testSendDirectExchange(String exchangeName,String routerKey,String message) {
        rabbitTemplate.convertAndSend(exchangeName, routerKey, message);
    }

    /**
     * 话题
     * @param exchangeName
     * @param key
     * @param message
     */
    public void testSendTopicExchange(String exchangeName,String key,String message) {
        rabbitTemplate.convertAndSend(exchangeName, key, message);
    }
}
