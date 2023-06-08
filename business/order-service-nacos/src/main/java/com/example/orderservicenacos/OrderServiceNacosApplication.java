package com.example.orderservicenacos;

import com.example.feignapi.clients.UserClient;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(clients = {UserClient.class})
@ComponentScan("com.example")
public class OrderServiceNacosApplication{

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceNacosApplication.class, args);
    }

    @Bean
    @LoadBalanced //负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    //将MQ的消息转换改为JSON序列化
//    @Bean
//    public MessageConverter messageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }

//    @Bean
//    public IRule randomRule(){
//        //全局定义负载均衡策略规则，如需单独配置某个服务的负载均衡规则可配置文件进行定翼
//        return new RandomRule();
//    }
}
