package com.dongz.codeutils.entitys;

import lombok.Data;

import java.util.Date;

/**
 * @author ${author}
 * @date ${currTime}
 * @desc
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
