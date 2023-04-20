package com.example.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Bing on 2023/4/17.
 */
@Configuration
public class FanoutConfig {

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("example.fanout");
    }

    @Bean
    public Queue fanoutQueue1(){
        return new Queue("founout.queue1");
    }

    @Bean
    public Binding fanoutBinding(Queue fanoutQueue1,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    @Bean
    public Queue fanoutQueue2(){
        return new Queue("founout.queue2");
    }

    @Bean
    public Binding fanoutBinding2(Queue fanoutQueue2,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    /**
     * 交换机二
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange2(){
        return new FanoutExchange("example.fanout2");
    }

    @Bean
    public Queue fanoutQueue3(){
        return new Queue("founout.queue3");
    }

    @Bean
    public Binding fanoutBinding3(Queue fanoutQueue3,FanoutExchange fanoutExchange2){
        return BindingBuilder.bind(fanoutQueue3).to(fanoutExchange2);
    }

    @Bean
    public Queue fanoutQueue4(){
        return new Queue("founout.queue4");
    }

    @Bean
    public Binding fanoutBinding4(Queue fanoutQueue4,FanoutExchange fanoutExchange2){
        return BindingBuilder.bind(fanoutQueue4).to(fanoutExchange2);
    }

}
