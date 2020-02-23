package com.dongz.hrm.system.shiro.realms;

import com.dongz.hrm.common.entities.Profile;
import com.dongz.hrm.common.shiro.realms.BaseRealm;
import com.dongz.hrm.system.services.UserService;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dong
 * @date 2020/2/20 00:42
 * @desc
 */
public class SystemRealm extends BaseRealm {

    @Autowired
    private UserService service;

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1， 获取用户手机号和密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String mobile = token.getUsername();
        String password = new String(token.getPassword());
        // 2，根据手机号查询用户
        // 3，判断用户是否存在， 密码是否一致
        Profile profile = service.assertUserAndGetRoles(mobile, password);
        if (profile != null) {
            // 4，构造安全数据并返回 （安全数据：用户基本信息， 权限信息 ）
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(profile, password, this.getName());
            return info;
        }
        return null;
    }
}
