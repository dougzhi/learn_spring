package com.dongz.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * @author dong
 * @date 2020/2/18 15:55
 * @desc
 */
public class ShiroTest {

    public Subject before(String fileName) {
        // 1，根据配置文件创建SecurityFactoryManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:"+fileName);
        // 2，通过工厂获取securityManager
        SecurityManager securityManager = factory.getInstance();
        // 3，将securityManager绑定到当前运行环境
        SecurityUtils.setSecurityManager(securityManager);
        // 4，从当前运行环境中构建subject
        return SecurityUtils.getSubject();
    }

    /**
     * 测试用户认证(登录）
     */
    @Test
    public void test1() {
        Subject subject = before("shiro-auth.ini");

        // 5，构建用户登录数据
        UsernamePasswordToken token = new UsernamePasswordToken("dongz", "123456");
        // 6，主体登录
        subject.login(token);

        System.out.println("是否登录成功：" + subject.isAuthenticated());
        System.out.println(subject.getPrincipal());
    }

    /**
     * 测试用户权限
     */
    @Test
    public void test2() {
        Subject subject = before("shiro-auth-roles.ini");

        // 5，构建用户登录数据
        UsernamePasswordToken token = new UsernamePasswordToken("dongz", "123456");
        // 6，主体登录
        subject.login(token);

        System.out.println("subject has role1 ="+ subject.hasRole("role1"));
        System.out.println("subject has permit:"+ subject.isPermitted("user:save"));
    }

    /**
     * 测试用户权限
     */
    @Test
    public void test3() {
        Subject subject = before("shiro-auth-realm.ini");

        // 5，构建用户登录数据
        UsernamePasswordToken token = new UsernamePasswordToken("dongz", "123456");
        // 6，主体登录
        subject.login(token);

        System.out.println("subject has role1 ="+ subject.hasRole("role1"));
        System.out.println("subject has permit:"+ subject.isPermitted("user:save"));
    }
}
