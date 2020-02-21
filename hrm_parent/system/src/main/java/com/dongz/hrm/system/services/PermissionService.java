package com.dongz.hrm.system.services;

import com.dongz.hrm.common.enums.PermissionStatus;
import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.domain.system.Permission;
import com.dongz.hrm.domain.system.PermissionMenu;
import com.dongz.hrm.domain.system.vos.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @author dong
 * @date 2020/2/20 13:46
 * @desc
 */
@Service
@Transactional
public class PermissionService extends BaseService {

    @Autowired
    private IdWorker idWorker;

    /**
     * 新增
     * @param vo vo
     */
    public Long create(PermissionVO vo) {
        Assert.notNull(vo, "权限信息不能为空");
        Assert.notNull(vo.getType(), "权限类型不能为空");
        Assert.hasText(vo.getName(), "权限名称不能为空");
        Assert.hasText(vo.getCode(), "权限码不能为空");
        PermissionStatus permissionStatus = PermissionStatus.parse(vo.getType());
        if (permissionStatus.equals(PermissionStatus.API)) {
            Assert.hasText(vo.getApiUrl(), "链接不能为空");
            Assert.hasText(vo.getApiMethod(), "链接请求类型不能为空");
            Assert.hasText(vo.getApiLevel(), "链接权限等级不能为空");
        } else if (permissionStatus.equals(PermissionStatus.MENU)) {
            Assert.hasText(vo.getMenuIcon(), "菜单图标不能为空");
            Assert.hasText(vo.getMenuOrder(), "菜单排序码不能为空");
        } else {
            Assert.hasText(vo.getPointIcon(), "权限点图标不能为空");
            Assert.hasText(vo.getPointClass(), "权限代码不能为空");
            Assert.hasText(vo.getPointStatus(), "权限点状态不能为空");
        }

        long count = em.createQuery("select count(1) from Permission u where u.name = ?1 ", Long.class).setParameter(1, vo.getName()).getFirstResult();
        Assert.isTrue(count == 0, "权限名称重复， 新增失败");

        if (!StringUtils.isEmpty(vo.getPid())) {
            count = em.createQuery("select count(1) from Permission u where u.id = ?1 ", Long.class).setParameter(1, vo.getPid()).getFirstResult();
            Assert.isTrue(count == 1, "父级权限不存在");
        }

        Permission permission = new Permission();

        permission.setId(idWorker.nextId());
        permission.setName(vo.getName());
        permission.setType(permissionStatus);
        permission.setCode(vo.getCode());
        permission.setDescription(vo.getDescription());
        permission.setPid(vo.getPid());
        permission.setIsVisible(true);

        em.persist(permission);
        Long permissionId = permission.getId();
        vo.setId(permissionId);

        permissionOption(vo, "create", permissionStatus.getClassName());

        return permissionId;
    }

    private void permissionOption(Object vo,String option, String className) {
        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getMethod(option, Object.class, EntityManager.class);
            method.invoke(clazz.newInstance(), vo, em);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | InstantiationException e) {
            Assert.isTrue(false, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 修改
     * @param vo vo
     */
    public void update(PermissionVO vo) {
        Assert.notNull(vo, "权限信息不能为空");
        Assert.notNull(vo.getId(), "要修改的权限ID不能为空");
        Assert.hasText(vo.getName(), "权限名称不能为空");

        Permission permission = em.find(Permission.class, vo.getId());
        Assert.notNull(permission, "权限信息不存在， 修改失败");

        // 权限手机号码
        if (!permission.getName().equals(vo.getName())) {
            Optional<Permission> first = em.createQuery("select u from Permission u where u.name = ?1 ", Permission.class).setParameter(1, vo.getName()).getResultStream().findFirst();
            Assert.isTrue((!first.isPresent()) || (first.get().getName().equals(vo.getName())), "权限名称重复， 修改失败");
            permission.setName(vo.getName());
        }

        permission.setDescription(vo.getDescription());

        em.merge(permission);
    }

    /**
     * 删除
     * @param id id
     */
    public void delete(Long id) {
        Assert.notNull(id, "要删除的权限ID不能为空");

        Permission permission = em.find(Permission.class, id);
        Assert.notNull(permission, "权限信息不存在， 修改失败");

        permissionOption(id, "delete", permission.getType().getClassName());
        em.remove(permission);
    }

    /**
     * 授权
     * @param id id
     */
    public void assignRoles(Long id, List<Long> roleIds) {
       /* Assert.notNull(id, "要授权的权限ID不能为空");

        Permission permission = em.find(Permission.class, id);
        Assert.notNull(permission, "权限信息不存在， 修改失败");
        Assert.isTrue(!permission.isDeleted(), "权限信息已删除");

        long count = em.createQuery("select count(1) from Role u where u.id in (:ids) and u.isDeleted = false", Long.class).setParameter("ids", roleIds).getSingleResult();
        Assert.isTrue(count == roleIds.size(), "角色信息异常");

        List<PermissionRole> list = em.createQuery("select u from PermissionRole u where u.permissionId = :id", PermissionRole.class).setParameter("id", id).getResultList();
        // 取左差集，新增
        List<Long> roleIdList = list.parallelStream().map(PermissionRole::getRoleId).collect(Collectors.toList());
        List<Long> createList = roleIds.parallelStream().filter(item -> !roleIdList.contains(item)).collect(Collectors.toList());
        // 取右差集，删除
        List<PermissionRole> removeList = list.parallelStream().filter(item -> !roleIds.contains(item.getRoleId())).collect(Collectors.toList());

        createList.forEach(item -> {
            PermissionRole permissionRole = new Permission()Role(id, item);
            em.persist(permissionRole);
        });
        removeList.forEach(item -> em.remove(item));*/
    }
}