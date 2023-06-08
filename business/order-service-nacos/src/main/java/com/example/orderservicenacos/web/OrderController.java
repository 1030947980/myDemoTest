package com.example.orderservicenacos.web;

import com.example.entity.order.Order;
import com.example.orderservicenacos.service.OrderService;
import com.example.resultful.web.ObjEnvelop;
import com.example.resultful.web.endpoint.EnvelopRestEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController extends EnvelopRestEndpoint {

   @Autowired
   private OrderService orderService;

    @GetMapping("{orderId}")
    public ObjEnvelop queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单并返回
        return ObjEnvelop.getSuccess("succes",orderService.queryOrderById(orderId));
    }
}
