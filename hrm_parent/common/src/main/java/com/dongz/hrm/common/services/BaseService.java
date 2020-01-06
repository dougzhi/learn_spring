package com.dongz.hrm.common.services;

import com.dongz.hrm.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.Date;

/**
 * @author dong
 * @date 2020/1/3 00:01
 * @desc
 */
public abstract class BaseService {

    @Autowired
    protected EntityManager em;

    protected void setCreate(BaseEntity entity) {
        Assert.notNull(entity, "要新增的实体不能为空");

        entity.setCreateTime(new Date());
        entity.setDeleted(false);
    }

    protected void setLastUpdate(BaseEntity entity) {
        Assert.notNull(entity, "要更新的实体不能为空");
        Assert.isTrue(!entity.isDeleted(), "实体已被删除");
        entity.setLastUpdateTime(new Date());
    }

    protected void setDelete(BaseEntity entity) {
        Assert.notNull(entity, "要删除的实体不能为空");
        Assert.isTrue(!entity.isDeleted(), "实体已被删除");

        entity.setDeleteTime(new Date());
        entity.setDeleted(true);
    }
}
