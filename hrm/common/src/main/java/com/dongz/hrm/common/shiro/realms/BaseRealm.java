package com.dongz.hrm.common.shiro.realms;

import com.dongz.hrm.common.entities.Profile;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * @author dong
 * @date 2020/2/20 00:38
 * @desc 获取安全数据， 构造权限信息
 */
public class BaseRealm extends AuthorizingRealm {

    @Override
    public void setName(String name) {
        super.setName("baseRealm");
    }

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取安全数据
        Profile primaryPrincipal = (Profile) principalCollection.getPrimaryPrincipal();
        // 获取权限信息
        List<String> apis = primaryPrincipal.getRoles().getApis();
        // 构造权限数据， 返回值
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(apis);

        return info;
    }

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
