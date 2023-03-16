package com.example.orderservice.dao;


import com.example.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderDao extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {

    Order findById(Integer id);
}
