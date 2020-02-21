package com.dongz.hrm.domain.system.vos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dong
 * @date 2020/2/21 15:02
 * @desc
 */
@Data
public class PermissionVO implements Serializable {
    private static final long serialVersionUID = 5792120620751662682L;

    private Long id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型 1为菜单 2为功能 3为API
     */
    private Integer type;

    private String code;
    /**
     * 权限描述
     */
    private String description;

    private Long pid;

    private String isVisible;

    /**
     * 链接
     */
    private String apiUrl;
    /**
     * 请求类型
     */
    private String apiMethod;
    /**
     * 权限等级，1为通用接口权限，2为需校验接口权限
     */
    private String apiLevel;

    /**
     * 展示图标
     */
    private String menuIcon;
    /**
     * 排序号
     */
    private String menuOrder;

    /**
     * 权限代码
     */
    private String pointClass;

    private String pointIcon;

    private String pointStatus;
}
