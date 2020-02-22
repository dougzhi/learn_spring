package com.dongz.hrm.domain.system;

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
 * @date 2020/2/6 17:16
 * @desc
 */
@EqualsAndHashCode
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role_permission")
public class RolePermission implements Serializable {
    private static final long serialVersionUID = -2905698512591343757L;
    @Id
    private Long roleId;
    @Id
    private Long permissionId;
}
