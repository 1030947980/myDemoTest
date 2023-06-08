package com.example.orderservicenacos.config.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "hotelEntityManagerFactory",
        transactionManagerRef = "hotelTransactionManager",
        basePackages = {"com.example.orderservicenacos.dao.hotel"})  //设置Repository所在位置
public class HotelJpa {
    @Autowired
    private HibernateProperties hibernateProperties;

    @Bean(name = "hotelDataSource")
    @ConfigurationProperties(prefix="spring.datasource.hotel")
    public DataSource healthDataSource() {
        return DataSourceBuilder.create().build();
    }



    @Bean(name = "hotelEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary(
            @Qualifier("hotelDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setPackagesToScan("com.example.entity.hotel");
        emfb.setPersistenceUnitName("hotel");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emfb.setJpaVendorAdapter(vendorAdapter);
        emfb.setJpaProperties(hibernateProperties.hibProperties());

        return emfb;
    }


    @Bean(name = "hotelTransactionManager")
    JpaTransactionManager transactionManagerSecondary(
            @Qualifier("hotelEntityManagerFactory")EntityManagerFactory builder) {
        return new JpaTransactionManager(builder);
    }
}