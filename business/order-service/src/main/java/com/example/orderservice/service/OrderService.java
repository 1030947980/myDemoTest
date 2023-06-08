package com.example.orderservice.service;

import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.pojo.Order;
import com.example.orderservice.pojo.User;
import com.netflix.discovery.converters.Auto;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.selectOrderById(orderId.intValue());
        // 4.返回
//        String url = "http://userservice/user/"+order.getUserId();//通过注册服务名称请求
//        User user = restTemplate.getForObject(url,User.class);
//        order.setUser(user);
        return order;
    }

    public List<Order> selectAll(Integer page,Integer size){
        if (null!=page){
            page = page>0?page-1:0;
            page = page*size;
        }
        List<Order> orderList =  orderMapper.selectOrderALL(page,size);
        return orderList;
    }

    public Order insertOrder(){
        Order order = new Order();
        order.setUserId(3l);
        order.setName("测试物品");
        order.setPrice(31900l);
        order.setNum(1);
        int id =  orderMapper.insertOrder(order);
        System.out.println(id);
        System.out.println(order.getId());
        return order;
    }

    public List<Order> selectOrderByCondition(){
        Order order = new Order();
        order.setUserId(3l);
        order.setName("梵班（FAMDBANN）休闲男鞋");
        List<Order> orderList = orderMapper.selectOrderByCondition(order);
        return orderList;
    }

    public void updateOrderByCondition(){
        Order order = new Order();
        order.setId(112l);
        order.setUserId(6l);
        order.setName("测试物品222");
        order.setPrice(32900l);
        order.setNum(2);
        orderMapper.updateOrderByCondition(order);
    }

    public void deleteByIds(){
        orderMapper.deleteByIds(new Long[]{108L,112L});
    }
}
