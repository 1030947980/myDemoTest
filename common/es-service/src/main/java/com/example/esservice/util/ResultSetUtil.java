package com.example.esservice.util;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResultSetUtil {

   public List<Map<String,Object>> resultToMapList(ResultSet resultSet) throws SQLException {
       List<Map<String,Object>> result = new ArrayList<>();
       ResultSetMetaData md = resultSet.getMetaData();
       int columnCount = md.getColumnCount();
       while (resultSet.next()){
           Map<String,Object> tmp = new HashMap<>();
           for (int i = 1; i <= columnCount; i++) {
               tmp.put(md.getColumnName(i), resultSet.getObject(i));
           }
           result.add(tmp);
       }
       return  result;
   }

    public JSONArray resultToJSONArray(ResultSet resultSet) throws SQLException {
        JSONArray result = new JSONArray();
        ResultSetMetaData md = resultSet.getMetaData();
        int columnCount = md.getColumnCount();
        while (resultSet.next()){
            Map<String,Object> tmp = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                tmp.put(md.getColumnName(i), resultSet.getObject(i));
            }
            result.add(tmp);
        }
        return  result;
    }

    public <T> List<T> resultToEntity(ResultSet resultSet, Class<T> target) throws Exception {
        List<T> result = new ArrayList<>();
        List<Map<String,Object>> List =  resultToMapList(resultSet);
        for(Map<String,Object> datas: List){
            T t = target.newInstance();
            BeanUtils.copyProperties(t,datas);
            result.add(t);
        }
        return result;
    }

}
