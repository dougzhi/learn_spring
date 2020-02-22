package com.dongz.hrm.domain.company.vos;

import com.dongz.hrm.common.enums.Category;
import lombok.Data;

import javax.persistence.Convert;

/**
 * @author dong
 * @date 2020/2/5 20:19
 * @desc 部门表
 */
@Data
public class DepartmentVO {
    /**
     * ID
     */
    private Long id;
    /**
     * 企业id
     */
    private Long companyId;
    /**
     * 父级部门id
     */
    private Long parentId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门标号
     */
    private String code;
    /**
     * 部门类型
     */
    @Convert(converter = Category.MyConverter.class)
    private Category category;
    /**
     * 部门负责人
     */
    private String manager;
    /**
     * 部门负责人ID
     */
    private String managerId;
    /**
     * 城市
     */
    private String city;
    /**
     * 介绍
     */
    private String introduce;
}
