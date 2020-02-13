package com.dongz.codeutils.entitys;

import lombok.Data;

import java.util.Date;

/**
 * @author dong
 * @date 2020/1/6 16:30
 * @desc 实体基类
 */
@Data
public abstract class BaseEntity {

    private long creatorId;
    private String creatorName;
    private Date createTime;
    private long lastUpdaterId;
    private String lastUpdaterName;
    private Date lastUpdateTime;
    private long deleterId;
    private String deleterName;
    private Date deleteTime;
    private boolean isDeleted;
}
