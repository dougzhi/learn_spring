package com.dongz.hrm.common.entities;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author dong
 * @date 2020/1/6 16:30
 * @desc 实体基类
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {

    private long creatorId;
    private String creatorName;
    private Date createTime;
    private long lastUpdateId;
    private String lastUpdateName;
    private Date lastUpdateTime;
    private long deleteId;
    private String deleteName;
    private Date deleteTime;
    private boolean isDeleted;
}
