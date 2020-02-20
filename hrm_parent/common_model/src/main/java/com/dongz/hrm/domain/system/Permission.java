package com.dongz.hrm.domain.system;

import com.dongz.hrm.common.enums.PermissionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author dong
 */
@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
public class Permission implements Serializable {
    private static final long serialVersionUID = -4990810027542971546L;
    /**
     * 主键
     */
    @Id
    private Long id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型 1为菜单 2为功能 3为API
     */
    @Convert(converter = PermissionStatus.MyConverter.class)
    private PermissionStatus type;

    private String code;

    /**
     * 权限描述
     */
    private String description;

    private String pid;

    private Integer enVisible;

    public Permission(String name, PermissionStatus type, String code, String description) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.description = description;
    }


}