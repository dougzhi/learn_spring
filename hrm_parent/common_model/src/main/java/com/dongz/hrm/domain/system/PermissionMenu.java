package com.dongz.hrm.domain.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author dong
 */
@Entity
@Table(name = "permission_menu")
@Getter
@Setter
public class PermissionMenu implements Serializable {
    private static final long serialVersionUID = -1002411490113957485L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 展示图标
     */
    private String menuIcon;

    /**
     * 排序号
     */
    private String menuOrder;
}