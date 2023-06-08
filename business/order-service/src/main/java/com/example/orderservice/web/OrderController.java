package com.example.orderservice.web;

import com.example.orderservice.pojo.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private OrderService orderService;

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单并返回
        return orderService.queryOrderById(orderId);
    }

    @GetMapping("selectAll")
    public List<Order> selectAll(@RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "size",required = false) Integer size) {
        // 根据id查询订单并返回
        return orderService.selectAll(page,size);
    }

    @GetMapping("insertOrder")
    public Order insertOrder() {
        // 根据id查询订单并返回
        return orderService.insertOrder();
    }

    @GetMapping("selectOrderByCondition")
    public List<Order> selectOrderByCondition() {
        // 根据id查询订单并返回
        return orderService.selectOrderByCondition();
    }

    @GetMapping("updateOrderByCondition")
    public String updateOrderByCondition() {
        // 根据id查询订单并返回
        orderService.updateOrderByCondition();
        return "success";
    }

    @GetMapping("deleteByIds")
    public String deleteByIds() {
        // 根据id查询订单并返回
        orderService.deleteByIds();
        return "success";
    }
}
