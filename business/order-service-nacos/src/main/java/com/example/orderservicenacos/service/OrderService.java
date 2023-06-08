package com.example.orderservicenacos.service;

import com.example.entity.order.Order;
import com.example.entity.user.User;
import com.example.feignapi.clients.UserClient;
import com.example.orderservicenacos.dao.order.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderDao.findById(orderId.intValue());
        // 4.返回
//        String url = "http://userservicenacos/user/"+order.getUserId();//通过注册服务名称请求
//        User user = restTemplate.getForObject(url,User.class);
        User user = userClient.findById(order.getUserId());
        order.setUser(user);
        return order;
    }
}
