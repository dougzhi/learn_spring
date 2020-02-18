package com.dongz.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dong
 * @date 2020/2/18 16:33
 * @desc 自定义Realm对象
 */
public class PermissionRealm extends AuthorizingRealm {


    @Override
    public void setName(String name) {
        super.setName("permissionRealm");
    }
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1，获取安全数据， username id
        String  username = (String) principalCollection.getPrimaryPrincipal();
        // 2，根据id或者名称查询用户
        // 3，查询用户角色和权限信息
        List<String> perms = new ArrayList<>();
        perms.add("user:save");
        List<String> roles = new ArrayList<>();
        roles.add("role1");
        // 4，构造返回
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(perms);
        info.addRoles(roles);
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1，构建uptoken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        // 2，获取输入的用户名密码
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        // 3，根据用户名查询数据库
        // 4，比较密码和数据库中是否一致(密码可能需要加密）
        if ("123456".equals(password)) {
            // 安全数据， 密码， 当前域名称
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, this.getName());
            return info;
        } else {
            throw new RuntimeException("用户名密码错误");
        }
        // 5，如果成功，向shiro中存入安全数据
        // 6，抛出异常或返回null
    }
}
