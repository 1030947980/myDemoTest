package com.example.orderservice.service;

import com.example.entity.order.Order;
import com.example.entity.user.User;
import com.example.orderservice.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderDao.findById(orderId.intValue());
        // 4.返回
        String url = "http://userservice/user/"+order.getUserId();//通过注册服务名称请求
        User user = restTemplate.getForObject(url,User.class);
        order.setUser(user);
        return order;
    }
}
