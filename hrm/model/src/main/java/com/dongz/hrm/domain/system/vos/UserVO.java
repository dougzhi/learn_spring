package com.dongz.hrm.domain.system.vos;

import com.dongz.hrm.common.enums.ServiceStatus;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author dong
 * @date 2020/2/20 13:51
 * @desc
 */
@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 6276205424227877247L;
    /**
     * ID
     */
    @Id
    private Long id;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 启用状态 0为禁用 1为启用
     */
    private Integer enableState;

    private String companyId;

    private String companyName;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 入职时间
     */
    private Date timeOfEntry;

    /**
     * 聘用形式
     */
    private Integer formOfEmployment;

    /**
     * 工号
     */
    private String workNumber;

    /**
     * 管理形式
     */
    private String formOfManagement;

    /**
     * 工作城市
     */
    private String workingCity;

    /**
     * 转正时间
     */
    private Date correctionTime;

    /**
     * 在职状态 1.在职  2.离职
     */
    private ServiceStatus serviceStatus;

    /**
     * 离职时间
     */
    private Date exitTime;

    private String departmentName;
}
