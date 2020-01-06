package com.dongz.hrm.domain.company;

import com.dongz.hrm.common.enums.AuditState;
import com.dongz.hrm.common.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "co_company")
public class Company implements Serializable {
    private static final long serialVersionUID = 594829320797158219L;
    //ID
    @Id
    private Long id;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 企业登录账号ID
     */
    private String managerId;
    /**
     * 当前版本
     */
    private String version;
    /**
     * 续期时间
     */
    private Date renewalDate;
    /**
     * 到期时间
     */
    private Date expirationDate;
    /**
     * 公司地区
     */
    private String companyArea;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 营业执照-图片ID
     */
    private String businessLicenseId;
    /**
     * 法人代表
     */
    private String legalRepresentative;
    /**
     * 公司电话
     */
    private String companyPhone;
    /**
     * 邮箱
     */
    private String mailbox;
    /**
     * 公司规模
     */
    private String companySize;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 审核状态
     */
    @Convert(converter = AuditState.MyConverter.class)
    private AuditState auditState;
    /**
     * 状态
     */
    @Convert(converter = State.MyConverter.class)
    private State state;
    /**
     * 当前余额
     */
    private Double balance;
    /**
     * 创建时间
     */
    private Date createTime;
}