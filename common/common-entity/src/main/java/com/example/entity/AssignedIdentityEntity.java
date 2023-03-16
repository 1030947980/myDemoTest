package com.example.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by progr1mmer on 2018/8/13.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AssignedIdentityEntity implements Serializable {

    protected String id;

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "assigned")
    @Column(name = "id", unique = true, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
