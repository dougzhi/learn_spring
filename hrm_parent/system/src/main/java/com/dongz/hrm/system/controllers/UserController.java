package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * @author dong
 * @date 2020/2/20 00:45
 * @desc
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @PostMapping("/login")
    public Result login(@RequestParam String mobile,@RequestParam String password) {
        try {
            // 加密密码(密码，盐，加密次数)
            String hashPassword = new Md5Hash(password, mobile, 3).toString();
            // 1，构造登录令牌
            UsernamePasswordToken token = new UsernamePasswordToken(mobile, hashPassword);
            // 2，获取subject
            Subject subject = SecurityUtils.getSubject();
            // 3，login
            subject.login(token);

            String sessionId = (String) subject.getSession().getId();

            return Result.SUCCESS(sessionId);
        } catch (Exception e) {
            return Result.LOGINFAILE();
        }
    }
}
