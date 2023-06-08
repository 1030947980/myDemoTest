package com.example.orderservice.mapper;

import com.example.orderservice.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Bing on 2023/5/25.
 */
@Mapper
public interface OrderMapper {

    Order selectOrderById(Integer id);

    List<Order> selectOrderALL(Integer page,Integer size);

    int insertOrder(Order order);

    List<Order> selectOrderByCondition(Order order);

    int updateOrderByCondition(Order order);

    int deleteById(Long id);

    void deleteByIds(Long[] ids);
}
