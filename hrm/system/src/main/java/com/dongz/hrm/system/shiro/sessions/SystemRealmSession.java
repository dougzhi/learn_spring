package com.dongz.hrm.system.shiro.sessions;

import com.dongz.hrm.domain.system.User;
import com.dongz.hrm.system.shiro.realms.SystemRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author dong
 * @date 2020/2/26 14:52
 * @desc
 */
public class SystemRealmSession {

    /**
     * 重新赋值权限(在比如:给一个角色临时添加一个权限,需要调用此方法刷新权限,否则还是没有刚赋值的权限)
     * @param userList 用户名
     */
    public static void reloadAuthorizing(@NotNull List<User> userList){
        DefaultWebSecurityManager shiroSession = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        SystemRealm systemRealm = (SystemRealm) shiroSession.getRealms().iterator().next();
        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();
        //第一个参数为用户名,第二个参数为realmName
        userList.forEach(user -> {
            SimplePrincipalCollection principals = new SimplePrincipalCollection(user,realmName);
            subject.runAs(principals);
            systemRealm.getAuthorizationCache().remove(subject.getPrincipals());
            subject.releaseRunAs();
        });
    }
}
