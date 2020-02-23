package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.Profile;
import com.dongz.hrm.common.entities.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 * @author dong
 * @date 2020/2/20 00:45
 * @desc
 */
@CrossOrigin
@RestController
@RequestMapping("/api/sys")
public class LoginController extends BaseController {

    @PostMapping("/login")
    public Result login(@RequestParam String mobile, @RequestParam String password) {
        try {
            //密码加密
            String hash = new Md5Hash(password, mobile, 3).toString();
            // 构造登录令牌
            UsernamePasswordToken token = new UsernamePasswordToken(mobile, hash);
            // 获取subject
            Subject subject = SecurityUtils.getSubject();
            // login
            subject.login(token);
            // 获取sessionId
            String sessionId = (String) subject.getSession().getId();
            return Result.LOGINSUCCESS(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.LOGINFAILE("用户名或密码错误！");
        }
    }

    @GetMapping("/userInfo")
    public Result login() {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        // 获取安全数据
        Profile profile = (Profile) principals.getPrimaryPrincipal();

        return Result.SUCCESS(profile);
    }
}
