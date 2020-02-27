package com.dongz.hrm.system.services;

import com.dongz.hrm.common.entities.Profile;
import com.dongz.hrm.common.enums.LevelState;
import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.domain.system.Permission;
import com.dongz.hrm.domain.system.User;
import com.dongz.hrm.domain.system.enums.PermissionStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dong
 * @date 2020/2/20 13:46
 * @desc
 */
@Service
@Transactional
public class AuthService extends BaseService {

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
        }
        getParentPermission(roleList);
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
