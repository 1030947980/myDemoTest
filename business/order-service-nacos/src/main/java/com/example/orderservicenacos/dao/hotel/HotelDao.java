package com.example.orderservicenacos.dao.hotel;

import com.example.entity.hotel.HotelDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Bing on 2023/5/15.
 */
public interface HotelDao extends JpaRepository<HotelDo,Long>, JpaSpecificationExecutor<HotelDo> {

    HotelDo findById(Integer id);

    @Query("select h from HotelDo h where h.id>?1 and h.id<?2 ")
    List<HotelDo> findHotelsByIds(Integer id1,Integer id2);

    List<HotelDo> findAll();
}
