package com.example.userservicenacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = "com.example.entity.user")
public class UserServiceNacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceNacosApplication.class, args);
    }

}
