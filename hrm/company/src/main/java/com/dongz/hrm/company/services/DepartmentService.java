package com.dongz.hrm.company.services;

import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.domain.company.Department;
import com.dongz.hrm.domain.company.vos.DepartmentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author dong
 * @date 2020/2/5 22:21
 * @desc
 */
@Service
public class DepartmentService extends BaseService {

    @Autowired
    private IdWorker idWorker;

    /**
     * 新增部门
     * @param vo
     * @return
     */
    public Long add(DepartmentVO vo) {
        Assert.notNull(vo, "部门信息不能为空");
        Assert.hasText(vo.getName(), "部门名称不能为空");
        Assert.hasText(vo.getCode(), "部门编码不能为空");
        Assert.notNull(vo.getCategory(), "部门类型不能为空");

        long count = em.createQuery("select count(1) from Department t where t.name = ?1 and t.isDeleted = false", Long.class).setParameter(1, vo.getName()).getSingleResult();
        Assert.isTrue(count == 0, "部门名称重复， 新增失败");

        Department department = new Department();
        BeanUtils.copyProperties(vo, department);

        department.setId(idWorker.nextId());

        setCreate(department);
        em.persist(department);
        return department.getId();
    }

    /**
     * 修改部门
     * @param vo
     */
    public void update(DepartmentVO vo) {
        Assert.notNull(vo, "部门信息不能为空");
        Assert.notNull(vo.getId(),"部门id不能为空");
        Assert.hasText(vo.getName(), "部门名称不能为空");
        Assert.hasText(vo.getCode(), "部门编码不能为空");
        Assert.notNull(vo.getCategory(), "部门类型不能为空");

        Department department = em.find(Department.class, vo.getId());
        Assert.notNull(department, "要修改的部门信息不存在");
        Assert.isTrue(department.isDeleted(), "部门已删除");

        if (!department.getName().equals(vo.getName())) {
            Optional<Department> first = em.createQuery("select u from Department u where u.name = ?1 and u.isDeleted = false", Department.class).setParameter(1, vo.getName()).getResultStream().findFirst();
            Assert.isTrue((!first.isPresent()) || (first.get().getName().equals(vo.getName())),"要修改的部门名称已存在");
            department.setName(vo.getName());
        }
        department.setCategory(vo.getCategory());
        department.setCity(vo.getCity());

        setLastUpdate(department);
        em.merge(department);
    }

    /**
     * 删除部门
     * @param id
     */
    public void delete(Long id) {
        Assert.notNull(id, "部门id不能为空");

        Department department = em.find(Department.class, id);
        Assert.notNull(department, "要删除的部门信息不存在");
        Assert.isTrue(department.isDeleted(), "部门已删除");

        setDelete(department);
        em.merge(department);
    }
}
