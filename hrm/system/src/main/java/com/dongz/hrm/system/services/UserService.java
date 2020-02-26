package com.dongz.hrm.system.services;

import com.dongz.hrm.common.entities.Profile;
import com.dongz.hrm.common.enums.EnableState;
import com.dongz.hrm.common.enums.FormOfEmployment;
import com.dongz.hrm.common.enums.LevelState;
import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.domain.system.Permission;
import com.dongz.hrm.domain.system.User;
import com.dongz.hrm.domain.system.UserRole;
import com.dongz.hrm.domain.system.enums.PermissionStatus;
import com.dongz.hrm.domain.system.vos.UserVO;
import com.dongz.hrm.system.shiro.sessions.SystemRealmSession;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dong
 * @date 2020/2/20 13:46
 * @desc
 */
@Service
@Transactional
public class UserService extends BaseService {

    @Autowired
    private IdWorker idWorker;

    /**
     * 新增
     * @param vo vo
     */
    public Long create(UserVO vo) {
        Assert.notNull(vo, "用户信息不能为空");
        Assert.hasText(vo.getUsername(), "用户名称不能为空");
        Assert.hasText(vo.getMobile(), "用户手机号码不能为空");

        long count = em.createQuery("select count(1) from User u where u.mobile = ?1 and u.isDeleted = false ", Long.class).setParameter(1, vo.getMobile()).getSingleResult();
        Assert.isTrue(count == 0, "用户手机号码重复， 新增失败");

        User user = new User();
        BeanUtils.copyProperties(vo, user);
        user.setId(idWorker.nextId());
        user.setLevel(LevelState.NormalUser);
        //默认密码
        user.setPassword(new Md5Hash("123456", vo.getMobile(), 3).toString());

        setCreate(user);
        em.persist(user);
        return user.getId();
    }

    /**
     * 修改
     * @param vo vo
     */
    public void update(UserVO vo) {
        Assert.notNull(vo, "用户信息不能为空");
        Assert.notNull(vo.getId(), "要修改的用户ID不能为空");
        Assert.hasText(vo.getUsername(), "用户名称不能为空");
        Assert.hasText(vo.getMobile(), "用户手机号码不能为空");

        User user = em.find(User.class, vo.getId());
        Assert.notNull(user, "用户信息不存在， 修改失败");
        Assert.isTrue(!user.isDeleted(), "用户信息已删除");

        // 用户手机号码
        if (!user.getMobile().equals(vo.getMobile())) {
            Optional<User> first = em.createQuery("select u from User u where u.mobile = ?1 and u.isDeleted = false ", User.class).setParameter(1, vo.getMobile()).getResultStream().findFirst();
            Assert.isTrue((!first.isPresent()) || (first.get().getMobile().equals(vo.getMobile())), "用户手机号码重复， 修改失败");
            user.setMobile(vo.getMobile());
        }

        user.setUsername(vo.getUsername());
        user.setEnableState(EnableState.Enable);
        user.setFormOfEmployment(FormOfEmployment.Formal);

        setLastUpdate(user);
        em.merge(user);
    }

    /**
     * 删除
     * @param id id
     */
    public void delete(Long id) {
        Assert.notNull(id, "要删除的用户ID不能为空");

        User user = em.find(User.class, id);
        Assert.notNull(user, "用户信息不存在， 修改失败");
        Assert.isTrue(!user.isDeleted(), "用户信息已删除");

        //删除角色关联
        List<UserRole> resultList = em.createQuery("select u from UserRole u where u.userId = ?1", UserRole.class).setParameter(1, id).getResultList();
        resultList.forEach(item -> em.remove(item));

        setDelete(user);
        em.merge(user);
    }

    /**
     * 授权
     * @param id id
     */
    public void assignRoles(Long id, List<Long> roleIds) {
        Assert.notNull(id, "要授权的用户ID不能为空");

        User user = em.find(User.class, id);
        Assert.notNull(user, "用户信息不存在， 修改失败");
        Assert.isTrue(!user.isDeleted(), "用户信息已删除");

        long count = em.createQuery("select count(1) from Role u where u.id in (:ids) and u.isDeleted = false", Long.class).setParameter("ids", roleIds).getSingleResult();
        Assert.isTrue(count == roleIds.size(), "角色信息异常");

        List<UserRole> list = em.createQuery("select u from UserRole u where u.userId = :id", UserRole.class).setParameter("id", id).getResultList();
        // 取左差集，新增
        List<Long> roleIdList = list.parallelStream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Long> createList = roleIds.parallelStream().filter(item -> !roleIdList.contains(item)).collect(Collectors.toList());
        // 取右差集，删除
        List<UserRole> removeList = list.parallelStream().filter(item -> !roleIds.contains(item.getRoleId())).collect(Collectors.toList());

        createList.forEach(item -> {
            UserRole userRole = new UserRole(id, item);
            em.persist(userRole);
        });
        removeList.forEach(item -> em.remove(item));

        if ((createList.size() + removeList.size()) > 0 ) {
            SystemRealmSession.reloadAuthorizing(getProfile(user));
        }
    }


    /**
     * 验证用户并返回权限
     * @param mobile
     * @param password
     * @return
     */
    public Profile assertUserAndGetRoles(@NotNull String mobile, @NotNull String password) {
        User user = em.createQuery("select u from User u where u.mobile = ?1 and u.isDeleted = 0 ", User.class).setParameter(1, mobile).getResultStream().findFirst().orElse(null);
        if (user == null) return null;
        if (!user.getPassword().equals(password)) return null;

        return getProfile(user);
    }

    /**
     * 获取安全数据
     * @param user
     * @return
     */
    public Profile getProfile(User user) {
        // 根据用户等级返回权限信息
        LevelState level = user.getLevel();
        List<Permission> roleList;
        // 超级管理员管理所有权限
        if (LevelState.Admin.equals(level)) {
            roleList = em.createQuery("select u from Permission u", Permission.class).getResultList();
        }
        // 企业管理员查看本企业
        else if (LevelState.CompanyAdmin.equals(level)) {
            roleList = em.createQuery("select u from Permission u where u.isVisible = 1", Permission.class).getResultList();
        }
        // 普通用户查询权限列表
        else {
            roleList = em.createQuery("select p from Permission p left join RolePermission o on p.id = o.permissionId " +
                    "left join Role r on o.roleId = r.id " +
                    "left join UserRole u on u.roleId = r.id where u.userId = ?1 and r.isDeleted = 0", Permission.class)
                    .setParameter(1, user.getId()).getResultList();
            getParentPermission(roleList);
        }
        return new Profile(user.getId(), user.getUsername(), user.getMobile(), user.getCompanyId(), user.getCompanyName(), getRoles(roleList));
    }

    /**
     * 获取所有父级权限
     * @param roleList
     */
    private void getParentPermission(final List<Permission> roleList) {
        //分割code，查询
        List<String> fullCode = Arrays.stream(roleList.parallelStream().map(Permission::getFullCode).distinct().collect(Collectors.joining(".")).split("\\.")).distinct().filter(item -> !"".equals(item)).collect(Collectors.toList());

        fullCode.parallelStream().forEach(item -> {
            List<Permission> list = em.createQuery("select u from Permission u where u.code = ?1", Permission.class).setParameter(1, item).getResultList();
            roleList.addAll(list);
        });
    }

    /**
     * 获取权限列表
     * @param roleList
     * @return
     */
    private Profile.Roles getRoles(List<Permission> roleList) {
        if (roleList == null) return null;
        Profile.Roles roles = new Profile.Roles();
        Map<String, List<String>> type = roleList.parallelStream().distinct()
                .collect(Collectors.groupingBy(item -> item.getType().name()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, item -> item.getValue().stream().map(Permission::getCode)
                        .collect(Collectors.toList())));

        roles.setMenus(type.getOrDefault(PermissionStatus.MENU.name(), null));
        roles.setPoints(type.getOrDefault(PermissionStatus.POINT.name(), null));
        roles.setApis(type.getOrDefault(PermissionStatus.API.name(), null));
        return roles;
    }

}
