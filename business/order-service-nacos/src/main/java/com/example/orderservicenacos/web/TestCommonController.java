//package com.example.orderservicenacos.web;
//
//import com.example.orderservicenacos.service.RedisLockTestService;
//import com.example.rabbitmq.producer.RabbitMqProducer;
//import org.checkerframework.checker.units.qual.A;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Bing on 2023/4/14.
// */
//@RestController
//@RequestMapping("/test2")
//public class TestCommonController {
//
//    @Autowired
//    private RabbitMqProducer rabbitMqProducer;
//    @Autowired
//    private RedisLockTestService redisLockTestService;
//
//    @GetMapping("/rabbitMqSend")
//    public String rabbitMqSend(@RequestParam("type")String type) {
//        try {
//            switch (type){
//                case "simple":
//                    //简单队列
//                    rabbitMqProducer.SimpleQueueSend("simple.queue","hello, spring amqp!");
//                    break;
//                case "work":
//                    //工作队列
//                    rabbitMqProducer.workQueueSend("work.queue","hello, spring amqp workQueue!");
//                    break;
//                case "fanout":
//                    //广播 fanoutexchange bean定义
//                    rabbitMqProducer.fanoutExchangeSend("example.fanout","hell every one");
//                    rabbitMqProducer.fanoutExchangeSend("example.fanout2","hell every one");
//                    break;
//                case "direct":
//                    //DirectExchahnge 路由交换机 根据规则路由到指定队列
//                    rabbitMqProducer.testSendDirectExchange("example.direct","red","red");
//                    rabbitMqProducer.testSendDirectExchange("example.direct","blue","blue");
//                    rabbitMqProducer.testSendDirectExchange("example.direct","yellow","yellow");
//                    break;
//                case "topic":
//                    //topicExchahnge 根据规则路由到指定队列
//                    // topicExchahnge的routingkey必须是多个单词的列表，并且以点(.)分割
//                    //#代表0个多多个单词、
//                    // *代表一个单词
//                    rabbitMqProducer.testSendTopicExchange("example.topic","china.news","中国新闻1");
//                    rabbitMqProducer.testSendTopicExchange("example.topic","china.weather","中国厦门天气：阴");
//                    rabbitMqProducer.testSendTopicExchange("example.topic","china.temperature","舒适");
//                    break;
//            }
//            return "success";
//        }catch (Exception e){
//            e.printStackTrace();
//            return "error";
//        }
//    }
//
//    @RequestMapping("/redisLockTest")
//    public String redosLockTest(){
//        return redisLockTestService.redisLockTest();
//    }
//}
