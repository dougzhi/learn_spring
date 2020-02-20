package com.dongz.hrm.system.services;

import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.domain.system.Role;
import com.dongz.hrm.domain.system.vos.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author dong
 * @date 2020/2/20 13:46
 * @desc
 */
@Service
@Transactional
public class RoleService extends BaseService {

    @Autowired
    private IdWorker idWorker;

    /**
     * 新增
     * @param vo vo
     */
    public Long create(RoleVO vo) {
        Assert.notNull(vo, "角色信息不能为空");
        Assert.hasText(vo.getName(), "角色名称不能为空");

        long count = em.createQuery("select count(1) from Role u where u.name = ?1 and u.isDeleted = false ", Long.class).setParameter(1, vo.getName()).getFirstResult();
        Assert.isTrue(count == 0, "角色名称重复， 新增失败");

        Role role = new Role();
        BeanUtils.copyProperties(vo, role);
        role.setId(idWorker.nextId());

        setCreate(role);
        em.persist(role);
        return role.getId();
    }

    /**
     * 修改
     * @param vo vo
     */
    public void update(RoleVO vo) {
        Assert.notNull(vo, "角色信息不能为空");
        Assert.notNull(vo.getId(), "要修改的角色ID不能为空");
        Assert.hasText(vo.getName(), "角色名称不能为空");

        Role role = em.find(Role.class, vo.getId());
        Assert.notNull(role, "角色信息不存在， 修改失败");
        Assert.isTrue(!role.isDeleted(), "角色信息已删除");

        // 角色名称
        if (!role.getName().equals(vo.getName())) {
            Optional<Role> first = em.createQuery("select u from Role u where u.name = ?1 and u.isDeleted = false ", Role.class).setParameter(1, vo.getName()).getResultStream().findFirst();
            Assert.isTrue((!first.isPresent()) || (first.get().getName().equals(vo.getName())), "角色名称重复， 新增失败");
            role.setName(vo.getName());
        }

        setLastUpdate(role);
        em.merge(role);
    }

    /**
     * 删除
     * @param id id
     */
    public void delete(Long id) {
        Assert.notNull(id, "要删除的角色ID不能为空");

        Role role = em.find(Role.class, id);
        Assert.notNull(role, "角色信息不存在， 修改失败");
        Assert.isTrue(!role.isDeleted(), "角色信息已删除");

        setDelete(role);
        em.merge(role);
    }
}
