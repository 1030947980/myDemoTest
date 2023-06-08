package com.example.orderservicenacos.config.jpa;

import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 多数据源配置
 */

@Component
public class HibernateProperties {

    public  Properties hibProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.enable_lazy_load_no_trans","true");
        properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        return properties;
    }
}
