package com.example.userservice;

import com.example.userservice.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Bing on 2023/3/15.
 */
public class MainClass
{
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("application.yml");
        UserService userService = (UserService) context.getBean("UserService");
        System.out.println(userService);
    }
}
