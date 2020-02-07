package com.dongz.hrm.domain.system;

import com.dongz.hrm.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author dong
 * @date 2020/2/6 15:41
 * @desc
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pe_role")
public class Role extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2157584532535018836L;
    @Id
    private String id;
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
