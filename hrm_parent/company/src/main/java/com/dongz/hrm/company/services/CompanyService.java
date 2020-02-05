package com.dongz.hrm.company.services;

import com.dongz.hrm.common.enums.AuditState;
import com.dongz.hrm.common.enums.State;
import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.domain.company.Company;
import com.dongz.hrm.domain.company.vos.CompanyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author dong
 * @date 2020/1/2 23:51
 * @desc
 */
@Service
public class CompanyService extends BaseService {

    @Autowired
    private IdWorker idWorker;

    /**
     * 新增
     * @param vo vo
     */
    public Long add(CompanyVO vo) {
        Assert.hasText(vo.getName(), "企业名称不能为空");
        Assert.hasText(vo.getBusinessLicense(), "企业营业执照不能为空");

        Optional<Company> first = em.createQuery("select u from Company u where u.businessLicense = ?1 and u.isDeleted = false ", Company.class).setParameter(1, vo.getBusinessLicense()).getResultStream().findFirst();
        Assert.isTrue(!first.isPresent(), "企业营业执照重复， 新增失败");

        Company company = new Company();
        BeanUtils.copyProperties(vo, company);
        company.setId(idWorker.nextId());
        company.setAuditState(AuditState.Unreviewed);
        company.setState(State.Enable);

        setCreate(company);
        em.persist(company);
        return company.getId();
    }

    /**
     * 修改
     * @param vo vo
     */
    public void update(CompanyVO vo) {
        Assert.notNull(vo.getId(), "要修改的企业ID不能为空");
        Assert.hasText(vo.getName(), "企业名称不能为空");
        Assert.hasText(vo.getBusinessLicense(), "企业营业执照不能为空");

        Company company = em.find(Company.class, vo.getId());
        Assert.notNull(company, "企业信息不存在， 修改失败");
        Assert.isTrue(company.isDeleted(), "企业信息已删除， 修改失败");

        // 如果修改营业执照
        if (!company.getBusinessLicense().equals(vo.getBusinessLicense())) {
            Optional<Company> first = em.createQuery("select u from Company u where u.businessLicense = ?1 and u.isDeleted = false ", Company.class).setParameter(1, vo.getBusinessLicense()).getResultStream().findFirst();
            Assert.isTrue((!first.isPresent()) || (first.get().getBusinessLicense().equals(company.getBusinessLicense())), "企业营业执照重复， 新增失败");
            company.setBusinessLicense(vo.getBusinessLicense());
        }

        company.setName(vo.getName());

        setLastUpdate(company);
        em.merge(company);
    }

    /**
     * 删除
     * @param id id
     */
    public void delete(Long id) {
        Assert.notNull(id, "要修改的企业ID不能为空");

        Company company = em.find(Company.class, id);
        Assert.notNull(company, "企业信息不存在， 修改失败");
        Assert.isTrue(company.isDeleted(), "企业信息已删除， 修改失败");

        setDelete(company);
        em.merge(company);
    }
}
