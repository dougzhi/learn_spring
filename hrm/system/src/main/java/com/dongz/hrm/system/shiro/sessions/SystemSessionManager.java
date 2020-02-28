package com.dongz.hrm.system.shiro.sessions;

import com.dongz.hrm.common.entities.Profile;
import com.dongz.hrm.system.SystemShiroConfiguration;
import com.dongz.hrm.system.shiro.realms.SystemRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dong
 * @date 2020/2/26 14:52
 * @desc
 */
public class SystemSessionManager {

    private final static String SESSION_KEY = "org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY";

    /**
     * 重新赋值权限(在比如:给一个角色临时添加一个权限,需要调用此方法刷新权限,否则还是没有刚赋值的权限)
     * @param profile 用户名
     */
    public static void reloadAuthorizing(@NotNull Profile profile){
        String realmName = clearCacheAndgetRealmName();

        Collection<Session> activeSessions = SystemShiroConfiguration.dao.getActiveSessions();
        activeSessions.stream().filter(item -> isContent(item, profile)).forEach(item -> {
            updateSession(item, profile, realmName);
        });
    }

    /**
     * 重新赋值权限(在比如:给一个角色临时添加一个权限,需要调用此方法刷新权限,否则还是没有刚赋值的权限)
     * @param profiles 用户名
     */
    public static void reloadAuthorizing(List<Profile> profiles){
        String realmName = clearCacheAndgetRealmName();

        Map<Long, Profile> map = profiles.stream().collect(Collectors.toMap(Profile::getId, item -> item));

        List<Long> userIds = profiles.stream().distinct().map(Profile::getId).collect(Collectors.toList());
        Collection<Session> activeSessions = SystemShiroConfiguration.dao.getActiveSessions();
        activeSessions.stream().filter(item -> userIds.contains(getId(item))).forEach(item -> {
            updateSession(item, map.get(getId(item)), realmName);
        });
    }

    private static synchronized void updateSession(final Session item,Profile profile,String realmName) {
        SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
        simplePrincipalCollection.add(profile, realmName);
        item.setAttribute(SESSION_KEY, simplePrincipalCollection);
        SystemShiroConfiguration.dao.update(item);
    }

    private static String clearCacheAndgetRealmName() {
        DefaultWebSecurityManager shiroSession = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        SystemRealm systemRealm = (SystemRealm) shiroSession.getRealms().iterator().next();
        Subject subject = SecurityUtils.getSubject();

        //第一个参数为用户名,第二个参数为realmName
        systemRealm.getAuthorizationCache().remove(subject.getPrincipals());
        systemRealm.clearAllCache();
        return systemRealm.getName();
    }

    private static boolean isContent(final Session activeSession, Profile profile) {
       return getId(activeSession).equals(profile.getId());
    }

    private static Long getId(final Session activeSession) {
        return ((Profile) ((SimplePrincipalCollection) activeSession.getAttribute(SESSION_KEY)).getPrimaryPrincipal()).
        getId();
    }
}
