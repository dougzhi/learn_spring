package com.dongz.hrm.company.services;

import com.dongz.hrm.common.enums.AuditState;
import com.dongz.hrm.common.enums.State;
import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param company
     */
    public void add(Company company) {
        company.setId(idWorker.nextId());
        company.setAuditState(AuditState.Unreviewed);
        company.setState(State.Enable);
    }

    /**
     * 修改
     * @param company
     */
    public void update(Company company) {

    }

    /**
     * 删除
     * @param id
     */
    public void delete(Long id) {

    }
}
