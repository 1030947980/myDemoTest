package com.example.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Listener - 创建日期、修改日期处理
 */
public class IdentityEntityListener {

    /**
     * 保存前处理
     *
     * @param entity
     *            基类
     */
    @PrePersist
    public void prePersist(BaseIdentityEntity entity) {
        entity.setCreateTime(new Date());
        entity.setLastUpdateTime(new Date());
    }

    /**
     * 更新前处理
     *
     * @param entity
     *            基类
     */
    @PreUpdate
    public void preUpdate(BaseIdentityEntity entity) {
        entity.setLastUpdateTime(new Date());
    }

}
