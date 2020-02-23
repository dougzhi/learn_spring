package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.common.enums.EnableState;
import com.dongz.hrm.common.enums.LevelState;
import com.dongz.hrm.common.utils.JwtUtils;
import com.dongz.hrm.domain.system.enums.PermissionStatus;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Result login(@RequestParam String mobile, @RequestParam String password) {
        //查询用户信息
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        Map<String, Object> map = this.queryForObject("select t.username,t.mobile,t.password,t.enable_state as enableState,t.company_id as companyId,t.company_name as companyName from user t where t.mobile = :mobile and t.is_deleted = false", params);
        Assert.isTrue(password.equals(map.get("password")), "密码错误，请重新输入密码！");
        Assert.isTrue(EnableState.Enable.equals(EnableState.parse((Integer) map.get("enableState"))), "用户已被禁用，请联系管理员！");

        map.remove("password");
        String token = jwtUtils.createJwt(mobile, (String) map.get("username"), map);
        return Result.LOGINSUCCESS(token);
    }

    @GetMapping("/userInfo")
    public Result login(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Assert.hasText(token, "签名信息为空，请求重新登录！");

        Claims claims = jwtUtils.parseJwt(token);
        String mobile = (String) claims.get("mobile");
        Assert.notNull(mobile, "手机号不能为空");
        //查询用户信息
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        Map<String, Object> userInfo = this.queryForObject("select t.id,t.username,t.mobile,t.company_id as companyId,t.company_name as company,t.level from user t where t.mobile = :mobile and t.is_deleted = false", params);
        //角色权限信息
        params.clear();
        Map<String, Object> roles = new HashMap<>();
        // 根据用户等级返回权限信息
        LevelState level = LevelState.parse((Integer) userInfo.get("level"));
        List<Map<String, Object>> roleList;
        // 超级管理员管理所有权限
        if (LevelState.Admin.equals(level)) {
            roleList = this.queryForList("select p.code,p.type from permission p", params);
        }
        // 企业管理员查看本企业
        else if (LevelState.CompanyAdmin.equals(level)) {
            roleList = this.queryForList("select p.code,p.type from permission p where p.is_visible = true", params);
        }
        // 普通用户查询权限列表
        else {
            params.put("id", userInfo.get("id"));
            roleList = this.queryForList("select p.code,p.type from permission p " +
                    "left join role_permission o on p.id = o.permission_id " +
                    "left join role r on o.role_id = r.id " +
                    "left join user_role u on r.id = u.role_id where u.user_id = :id and r.is_deleted =0", params);
        }
        Map<String, List<Map<String, Object>>> type = roleList.parallelStream().collect(Collectors.groupingBy(item -> PermissionStatus.parse((Integer) item.get("type")).getName()));
        roles.put("menus", type.get(PermissionStatus.MENU.getName()).parallelStream().map(item -> (String) item.get("code")).collect(Collectors.toList()));
        roles.put("points", type.get(PermissionStatus.POINT.getName()).parallelStream().map(item -> (String) item.get("code")).collect(Collectors.toList()));
        roles.put("apis", type.get(PermissionStatus.API.getName()).parallelStream().map(item -> (String) item.get("code")).collect(Collectors.toList()));
        userInfo.put("roles", roles);
        return Result.SUCCESS(userInfo);
    }
}
