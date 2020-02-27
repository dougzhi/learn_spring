package com.dongz.hrm.system.shiro.sessions;

import com.dongz.hrm.common.entities.Profile;
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
     * @param profile 用户名
     */
    public static void reloadAuthorizing(@NotNull Profile profile){
        DefaultWebSecurityManager shiroSession = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        SystemRealm systemRealm = (SystemRealm) shiroSession.getRealms().iterator().next();
        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();

        //第一个参数为用户名,第二个参数为realmName
        SimplePrincipalCollection principals = new SimplePrincipalCollection(profile,realmName);
        subject.runAs(principals);
        systemRealm.getAuthorizationCache().remove(subject.getPrincipals());
        systemRealm.clearAllCache();
        subject.releaseRunAs();
    }

    /**
     * 重新赋值权限(在比如:给一个角色临时添加一个权限,需要调用此方法刷新权限,否则还是没有刚赋值的权限)
     * @param profileList 用户名
     */
    public static void reloadAuthorizing(@NotNull List<Profile> profileList){
        DefaultWebSecurityManager shiroSession = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        SystemRealm systemRealm = (SystemRealm) shiroSession.getRealms().iterator().next();
        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();

        //第一个参数为用户名,第二个参数为realmName
        profileList.stream().distinct().forEach(profile -> {
            SimplePrincipalCollection principals = new SimplePrincipalCollection(profile,realmName);
            subject.runAs(principals);
            systemRealm.getAuthorizationCache().remove(subject.getPrincipals());
            subject.releaseRunAs();
        });
    }
}
