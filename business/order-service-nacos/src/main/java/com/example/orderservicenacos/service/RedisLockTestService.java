package com.example.orderservicenacos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bing on 2023/5/1.
 */
@Service
public class RedisLockTestService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分布式锁实现
     * @return
     */
    public String redisLockTest(){
        String lockKye="product_001";
        String lockUUid = UUID.randomUUID().toString();
        //1
        try {
            boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKye,lockUUid,10, TimeUnit.SECONDS);
            if (!result){
                return "error";
            }
            //业务流程

        }finally {
            if (lockUUid.equals(stringRedisTemplate.opsForValue().get(lockKye))){
                //释放锁
                stringRedisTemplate.delete(lockKye);
            }
        }
        return "success";
    }

}
