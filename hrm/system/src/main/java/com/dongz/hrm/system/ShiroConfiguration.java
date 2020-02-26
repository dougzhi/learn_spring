package com.dongz.hrm.system;

import com.dongz.hrm.common.shiro.realms.BaseRealm;
import com.dongz.hrm.common.shiro.sessions.ShiroSession;
import com.dongz.hrm.system.shiro.realms.SystemRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dong
 * @date 2020/2/23 23:41
 * @desc
 */
@Configuration
public class ShiroConfiguration {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;

    /**
     * 过滤器
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        // 1, 创建过滤工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        // 2，设置安全管理器
        filterFactory.setSecurityManager(securityManager);
        // 3，通用配置（跳转登录页，未授权页）
        filterFactory.setLoginUrl("/api/error/authorError?code=1");
        filterFactory.setUnauthorizedUrl("/api/error/authorError?code=2");
        // 设置请求过滤器集合
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 当前请求可匿名访问
        filterMap.put("/api/sys/login", "anon");
        filterMap.put("/api/sys/register", "anon");
        filterMap.put("/api/error /**", "anon");
        // 当前请求认证后可访问
        filterMap.put("/**", "authc");
        //filterMap.put("/xxx", "permis[role1]"); // 当前请求具有权限可访问 ，本系统使用注解配置授权

        filterFactory.setFilterChainDefinitionMap(filterMap);
        return filterFactory;
    }

    /**
     * Redis控制器：操作redis
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        return redisManager;
    }

    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO dao = new RedisSessionDAO();
        dao.setRedisManager(redisManager());
        return dao;
    }

    /**
     * 会话管理器
     * @return
     */
    public DefaultWebSessionManager sessionManager() {
        ShiroSession shiroSession = new ShiroSession();
        shiroSession.setSessionDAO(redisSessionDAO());
        // 禁用Cookie
        shiroSession.setSessionIdCookieEnabled(false);
        // 禁用url重写（禁止拼接 josnId）
        shiroSession.setSessionIdUrlRewritingEnabled(false);
        return shiroSession;
    }

    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 创建Realm
     * @return
     */
    @Bean
    public BaseRealm systemRealm() {
        return new SystemRealm();
    }

    @Bean
    public SecurityManager getSecurityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(systemRealm());
        // 将自定义的会话管理器注册到安全管理器中
        manager.setSessionManager(sessionManager());
        // 将自定义的Redis缓存管理器注册到安全管理器中
        manager.setCacheManager(cacheManager());
        return manager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
