package com.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EntityUtils {

    protected static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T jsonToEntity(String json,Class<T> target) throws Exception {
        T entity = null;
        try {
            entity  = objectMapper.readValue(json,target);
        } catch (Exception e) {
           throw new Exception("Json字符串转换成【"+target.getName()+"】实体类异常：" + e.getMessage());
        }
        return entity;
    }

}
