package com.dongz.hrm.system.services;

import com.dongz.hrm.common.entities.Profile;
import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.domain.system.Role;
import com.dongz.hrm.domain.system.RolePermission;
import com.dongz.hrm.domain.system.User;
import com.dongz.hrm.domain.system.UserRole;
import com.dongz.hrm.domain.system.vos.RoleVO;
import com.dongz.hrm.system.shiro.sessions.SystemRealmSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    public AuthService authService;

    /**
     * 新增
     * @param vo vo
     */
    public Long create(RoleVO vo) {
        Assert.notNull(vo, "角色信息不能为空");
        Assert.hasText(vo.getName(), "角色名称不能为空");

        long count = em.createQuery("select count(1) from Role u where u.name = ?1 and u.isDeleted = false ", Long.class).setParameter(1, vo.getName()).getSingleResult();
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
     *
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
            Assert.isTrue((!first.isPresent()) || (first.get().getName().equals(vo.getName())), "角色名称重复， 修改失败");
            role.setName(vo.getName());
        }

        setLastUpdate(role);
        em.merge(role);
    }

    /**
     * 删除
     *
     * @param id id
     */
    public void delete(Long id) {
        Assert.notNull(id, "要删除的角色ID不能为空");

        Role role = em.find(Role.class, id);
        Assert.notNull(role, "角色信息不存在， 修改失败");
        Assert.isTrue(!role.isDeleted(), "角色信息已删除");

        // 删除用户关联
        List<UserRole> userList = em.createQuery("select u from UserRole u where u.roleId = ?1", UserRole.class).setParameter(1, id).getResultList();
        userList.forEach(item -> em.remove(item));

        // 删除权限关联
        List<RolePermission> resultList = em.createQuery("select u from RolePermission u where u.roleId = ?1", RolePermission.class).setParameter(1, id).getResultList();
        resultList.forEach(item -> em.remove(item));

        setDelete(role);
        em.merge(role);
    }

    /**
     * 授权
     *
     * @param id id
     */
    public void assignPrem(Long id, List<Long> permIds) {
        Assert.notNull(id, "角色ID不能为空");

        Role role = em.find(Role.class, id);
        Assert.notNull(role, "角色信息不存在， 修改失败");
        Assert.isTrue(!role.isDeleted(), "角色信息已删除");

        if (permIds.size() != 0){
            long count = em.createQuery("select count(1) from Permission u where u.id in (:ids)", Long.class).setParameter("ids", permIds).getSingleResult();
            Assert.isTrue(count == permIds.size(), "权限信息异常");
        }

        List<RolePermission> list = em.createQuery("select u from RolePermission u where u.roleId = :id", RolePermission.class).setParameter("id", id).getResultList();
        // 取左差集，新增
        List<Long> permissionList = list.parallelStream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        List<Long> createList = permIds.parallelStream().filter(item -> !permissionList.contains(item)).collect(Collectors.toList());
        // 取右差集，删除
        List<RolePermission> removeList = list.parallelStream().filter(item -> !permIds.contains(item.getPermissionId())).collect(Collectors.toList());

        createList.forEach(item -> {
            RolePermission rolePermission = new RolePermission(id, item);
            em.persist(rolePermission);
        });
        removeList.forEach(item -> em.remove(item));

        em.flush();
        em.clear();
        // 权限刷新
        if ((createList.size() + removeList.size()) > 0) {
            //查询所有具有role角色用户
            List<User> userList = em.createQuery("select u from User u left join UserRole r on u.id = r.userId where r.roleId = ?1 and u.isDeleted = 0 ", User.class).setParameter(1, id).getResultList();
            List<Profile> profileList = userList.stream().distinct().map(item -> authService.getProfile(item)).collect(Collectors.toList());
            SystemRealmSession.reloadAuthorizing(profileList);
        }
    }
}
