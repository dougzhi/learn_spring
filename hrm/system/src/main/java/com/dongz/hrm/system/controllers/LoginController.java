package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.common.enums.EnableState;
import com.dongz.hrm.common.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dong
 * @date 2020/2/20 00:45
 * @desc
 */
@CrossOrigin
@RestController
@RequestMapping("/api/sys")
public class LoginController extends BaseController {

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@RequestParam String mobile,@RequestParam String password) {
        //查询用户信息
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        Map<String, Object> map = this.queryForObject("select t.username,t.passsword,t.enable_state as enableState,company_id as companyId,company_name as companyName form user t where t.mobile = :mibile and t.isdeleted = false", params);
        Assert.isTrue(password.equals(map.get("password")), "密码错误，请重新输入密码！");
        Assert.isTrue(EnableState.Enable.equals(EnableState.parse((Integer) map.get("enableState"))), "用户已被禁用，请联系管理员！");

        map.remove("password");
        String token = jwtUtils.createJwt(mobile, (String) map.get("username"), map);
        return Result.LOGINSUCCESS(token);
    }
}
