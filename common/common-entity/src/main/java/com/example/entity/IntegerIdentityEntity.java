package com.example.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 由于ORACEL 与mysql Id策略不一样
 * Integer类型的主键基类，需要根据不同环境打包
 * Created by progr1mmer on 2018/8/13.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class IntegerIdentityEntity implements Serializable {

    protected Integer id;

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "identity")
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
