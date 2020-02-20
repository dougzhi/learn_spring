package com.dongz.hrm.domain.system.vos;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author dong
 * @date 2020/2/6 15:41
 * @desc
 */
@Data
public class RoleVO implements Serializable {
    private static final long serialVersionUID = 2903169340397898266L;
    @Id
    private Long id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 说明
     */
    private String description;
    /**
     * 企业id
     */
    private String companyId;
}
