package com.dongz.hrm.system.services;

import com.dongz.hrm.common.enums.IsVisible;
import com.dongz.hrm.domain.system.*;
import com.dongz.hrm.domain.system.enums.PermissionStatus;
import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.domain.system.vos.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
        Assert.notNull(vo.getPid(), "父级权限不能为空");
        Assert.notNull(vo.getIsVisible(), "权限可见性不能为空");
        IsVisible isVisible = IsVisible.parse(vo.getIsVisible());

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

        if (vo.getPid() != 0) {
            long count = em.createQuery("select count(1) from Permission u where u.id = ?1 ", Long.class).setParameter(1, vo.getPid()).getSingleResult();
            Assert.isTrue(count == 1, "父级权限不存在");
        }

        long count = em.createQuery("select count(1) from Permission u where u.name = ?1 and u.pid = ?2 ", Long.class).setParameter(1, vo.getName()).setParameter(2, vo.getPid()).getSingleResult();
        Assert.isTrue(count == 0, "权限名称重复， 新增失败");

        Permission permission = new Permission();

        permission.setId(idWorker.nextId());
        permission.setName(vo.getName());
        permission.setType(permissionStatus);
        permission.setCode(vo.getCode());
        permission.setDescription(vo.getDescription());
        permission.setPid(vo.getPid());
        permission.setIsVisible(isVisible);

        em.persist(permission);
        Long permissionId = permission.getId();
        vo.setId(permissionId);

        permissionOption(vo, "create", permissionStatus.getClassName());

        return permissionId;
    }

    /**
     * 修改
     * @param vo vo
     */
    public void update(PermissionVO vo) {
        Assert.notNull(vo, "权限信息不能为空");
        Assert.notNull(vo.getId(), "要修改的权限ID不能为空");
        Assert.hasText(vo.getName(), "权限名称不能为空");
        Assert.hasText(vo.getCode(), "权限码不能为空");
        Assert.notNull(vo.getIsVisible(), "权限可见性不能为空");
        IsVisible isVisible = IsVisible.parse(vo.getIsVisible());

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

        Permission permission = em.find(Permission.class, vo.getId());
        Assert.notNull(permission, "权限信息不存在， 修改失败");
        Assert.isTrue(permissionStatus.equals(permission.getType()), "权限类型禁止修改");

        if (!permission.getName().equals(vo.getName())) {
            Optional<Permission> first = em.createQuery("select u from Permission u where u.name = ?1 ", Permission.class).setParameter(1, vo.getName()).getResultStream().findFirst();
            Assert.isTrue((!first.isPresent()) || (first.get().getName().equals(vo.getName())), "权限名称重复， 修改失败");
            permission.setName(vo.getName());
        }

        permission.setDescription(vo.getDescription());
        permission.setCode(vo.getCode());
        permission.setIsVisible(isVisible);

        em.merge(permission);

        permissionOption(vo, "update", permissionStatus.getClassName());
    }

    /**
     * 删除
     * @param id id
     */
    public void delete(Long id) {
        Assert.notNull(id, "要删除的权限ID不能为空");

        Permission permission = em.find(Permission.class, id);
        Assert.notNull(permission, "权限信息不存在， 修改失败");

        //删除所有角色管理
        List<RolePermission> roleList = em.createQuery("select u from RolePermission u where u.permissionId = ?1", RolePermission.class).setParameter(1, id).getResultList();
        roleList.forEach(item -> em.remove(item));

        //级联删除
        List<Long> resultList = em.createQuery("select u.id from Permission u where u.pid = ?1", Long.class).setParameter(1, id).getResultList();
        resultList.forEach(this::delete);

        permissionOption(id, "delete", permission.getType().getClassName());
        em.remove(permission);
    }

    private void permissionOption(Object vo,String option, String className) {
        try {
            Class<PermissionService> pClazz = PermissionService.class;
            Class<?>[] declaredClasses = pClazz.getDeclaredClasses();
            Optional<Class<?>> first = Arrays.stream(declaredClasses).filter(item -> item.getSimpleName().equals(className)).findFirst();
            Assert.isTrue(first.isPresent(), "方法未找到");

            Class<?> aClass = first.get();
            Method method = aClass.getDeclaredMethod(option, Object.class, EntityManager.class);
            method.invoke(aClass.getDeclaredConstructors()[0].newInstance(pClazz.newInstance()), vo, em);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            Assert.isTrue(false, e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    class PermissionApiService {
        public void create(Object vo, EntityManager em) {
            PermissionVO permissionVO = (PermissionVO) vo;
            PermissionApi api = new PermissionApi();

            api.setId(permissionVO.getId());
            api.setApiUrl(permissionVO.getApiUrl());
            api.setApiMethod(permissionVO.getApiMethod());
            api.setApiLevel(permissionVO.getApiLevel());

            em.persist(api);
        }

        public void update(Object vo, EntityManager em) {
            PermissionVO permissionVO = (PermissionVO) vo;

            PermissionApi api = em.find(PermissionApi.class, permissionVO.getId());
            Assert.notNull(api, "权限链接信息不能为空");

            api.setApiUrl(permissionVO.getApiUrl());
            api.setApiMethod(permissionVO.getApiMethod());
            api.setApiLevel(permissionVO.getApiLevel());

            em.merge(api);
        }

        public void delete(Object id, EntityManager em) {
            Long permissionId = (Long) id;
            em.remove(em.getReference(PermissionApi.class, permissionId));
        }
    }

    @Transactional
    class PermissionMenuService {
        public void create(Object vo, EntityManager em) {
            PermissionVO permissionVO = (PermissionVO) vo;
            PermissionMenu menu = new PermissionMenu();

            menu.setId(permissionVO.getId());
            menu.setMenuIcon(permissionVO.getMenuIcon());
            menu.setMenuOrder(permissionVO.getMenuOrder());

            em.persist(menu);
        }

        public void update(Object vo, EntityManager em) {
            PermissionVO permissionVO = (PermissionVO) vo;

            PermissionMenu menu = em.find(PermissionMenu.class, permissionVO.getId());
            Assert.notNull(menu, "权限菜单信息不能为空");

            menu.setMenuIcon(permissionVO.getMenuIcon());
            menu.setMenuOrder(permissionVO.getMenuOrder());

            em.merge(menu);
        }

        public void delete(Object id, EntityManager em) {
            Long permissionId = (Long) id;
            em.remove(em.getReference(PermissionMenu.class, permissionId));
        }
    }

    @Transactional
    class PermissionPointService {
        public void create(Object vo, EntityManager em) {
            PermissionVO permissionVO = (PermissionVO) vo;
            PermissionPoint point = new PermissionPoint();

            point.setId(permissionVO.getId());
            point.setPointClass(permissionVO.getPointClass());
            point.setPointIcon(permissionVO.getPointIcon());
            point.setPointStatus(permissionVO.getPointStatus());

            em.persist(point);
        }

        public void update(Object vo, EntityManager em) {
            PermissionVO permissionVO = (PermissionVO) vo;
            PermissionPoint point = em.find(PermissionPoint.class, permissionVO.getId());
            Assert.notNull(point, "权限按钮信息不能为空");

            point.setPointClass(permissionVO.getPointClass());
            point.setPointIcon(permissionVO.getPointIcon());
            point.setPointStatus(permissionVO.getPointStatus());

            em.merge(point);
        }

        public void delete(Object id, EntityManager em) {
            Long permissionId = (Long) id;
            em.remove(em.getReference(PermissionPoint.class, permissionId));
        }
    }
}