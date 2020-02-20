package com.dongz.hrm.system.shiro.realms;

import com.dongz.hrm.common.shiro.realms.BaseRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author dong
 * @date 2020/2/20 00:42
 * @desc
 */
public class SystemRealm extends BaseRealm {

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1， 获取用户手机号和密码
        // 2，根据手机号查询用户
        // 3，判断用户是否存在， 密码是否一致
        // 4，构造安全数据并返回 （安全数据：用户基本信息， 权限信息 ）
        return null;
    }
}
