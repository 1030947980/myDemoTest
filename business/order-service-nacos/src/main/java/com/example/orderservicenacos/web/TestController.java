package com.example.orderservicenacos.web;

import com.example.entity.order.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Bing on 2023/4/3.
 */
@RestController
@RequestMapping("/test")
@RefreshScope //热部署数据刷新
public class TestController {

    @Value("${pattern.dateformat}")
    private String dateformatStr;

    @GetMapping("now")
    public String getNow() {
        SimpleDateFormat sdf = new SimpleDateFormat(dateformatStr);
        String nowDate = sdf.format(new Date());
        return nowDate;
    }
}
