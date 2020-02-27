package com.dongz.hrm.system;

import com.dongz.hrm.common.ShiroConfiguration;
import com.dongz.hrm.system.shiro.realms.SystemRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dong
 * @date 2020/2/23 23:41
 * @desc
 */
@Configuration
public class SystemShiroConfiguration extends ShiroConfiguration {

    @Bean
    public SystemRealm systemRealm() {
        SystemRealm systemRealm = new SystemRealm();
        systemRealm.setCachingEnabled(true);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        systemRealm.setAuthenticationCachingEnabled(true);
        //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
        systemRealm.setAuthenticationCacheName("authenticationCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        systemRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
        systemRealm.setAuthorizationCacheName("authorizationCache");
        //配置自定义密码比较器
        //systemRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher());
        return systemRealm;
    }

    @Override
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置自定义realm.
        securityManager.setRealm(systemRealm());
        //配置记住我
        securityManager.setRememberMeManager(rememberMeManager());
        //配置redis缓存
        securityManager.setCacheManager(cacheManager());
        //配置自定义session管理，使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
}
