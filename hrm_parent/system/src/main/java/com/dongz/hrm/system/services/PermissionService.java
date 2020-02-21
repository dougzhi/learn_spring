package com.dongz.hrm.system.services;

import com.dongz.hrm.common.enums.PermissionStatus;
import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.domain.system.Permission;
import com.dongz.hrm.domain.system.PermissionApi;
import com.dongz.hrm.domain.system.PermissionMenu;
import com.dongz.hrm.domain.system.PermissionPoint;
import com.dongz.hrm.domain.system.vos.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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

        long count = em.createQuery("select count(1) from Permission u where u.name = ?1 ", Long.class).setParameter(1, vo.getName()).getSingleResult();
        Assert.isTrue(count == 0, "权限名称重复， 新增失败");

        if (!(StringUtils.isEmpty(vo.getPid()) || "0".equals(vo.getPid()))) {
            count = em.createQuery("select count(1) from Permission u where u.id = ?1 ", Long.class).setParameter(1, vo.getPid()).getSingleResult();
            Assert.isTrue(count == 1, "父级权限不存在");
        }

        Permission permission = new Permission();

        permission.setId(idWorker.nextId());
        permission.setName(vo.getName());
        permission.setType(permissionStatus);
        permission.setCode(vo.getCode());
        permission.setDescription(vo.getDescription());
        permission.setPid(vo.getPid());
        permission.setIsVisible(vo.getIsVisible());

        em.persist(permission);
        Long permissionId = permission.getId();
        vo.setId(permissionId);

        permissionOption(vo, "create", permissionStatus.getClassName());

        return permissionId;
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

        public void delete(Object id, EntityManager em) {
            Long permissionId = (Long) id;
            em.remove(em.getReference(PermissionPoint.class, permissionId));
        }
    }
}