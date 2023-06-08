package com.example.orderservicenacos.service;

import com.example.entity.hotel.HotelDo;
import com.example.esservice.ElasticSearch7Util;
import com.example.orderservicenacos.dao.hotel.HotelDao;
import com.example.orderservicenacos.esEntity.HotelDoc;
import com.example.resultful.web.exceptions.ServiceException;
import com.example.util.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bing on 2023/5/15.
 */
@Service
public class EsService {

    @Autowired
    private HotelDao hotelDao;
    @Autowired
    private ElasticSearch7Util elasticSearch7Util;

    public HotelDo getHotel(Long id){
        HotelDo hotelDo = hotelDao.findById(id.intValue());
        return hotelDo;
    }

    public boolean createEsIndex(String index,String source) throws IOException {
        if(elasticSearch7Util.createIndex(index,source)){
            return true;
        }
        return false;
    }

    public boolean existIndex(String index) throws IOException {
        return elasticSearch7Util.existIndex(index);
    }

    public boolean deleteEsIndex(String index) throws Exception {
        if (elasticSearch7Util.existIndex(index)){
            return elasticSearch7Util.deleteIndex(index);
        }else {
            throw new ServiceException("索引不存在无法删除");
        }
    }

    public boolean addEsIndexDocument(String index, Long id) throws IOException {
        HotelDo hotelDo = hotelDao.findById(id.intValue());
        if (null!= hotelDo){
            return elasticSearch7Util.addDocument(index,new HotelDoc(hotelDo));
        }else {
            throw new ServiceException("插入数据源不存在");
        }
    }

    public boolean addEsIndexDocuments(String index) throws Exception {
        List<HotelDo> hotelDoList = hotelDao.findAll();
        if (hotelDoList.size()>0){
            List<HotelDoc> hotelDocList = new ArrayList<>();
            hotelDoList.forEach(one->{
                hotelDocList.add(new HotelDoc(one));
            });
            return elasticSearch7Util.addDocument(index,hotelDocList);
        }else {
            throw new ServiceException("插入数据源不存在");
        }
    }

    public HotelDoc getDocuments(String index,Long id) throws Exception {
        String response = elasticSearch7Util.getDocument(index,id);
        if (StringUtils.isNotBlank(response)){
            return EntityUtils.jsonToEntity(response, HotelDoc.class);
        }
        return null;
    }

    public Boolean updateDocument(String index) throws IOException {
        HotelDoc hotelDoc = new HotelDoc();
        hotelDoc.setId(39106l);
        hotelDoc.setStarName("二钻333");
        return elasticSearch7Util.updateDocument(index,hotelDoc);
    }

    public Boolean deleteDocument(String index) throws IOException {
        HotelDoc hotelDoc = new HotelDoc();
        hotelDoc.setId(39106l);
        return elasticSearch7Util.deleteDocument(index,hotelDoc);
    }
}
