package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.common.enums.EnableState;
import com.dongz.hrm.common.enums.LevelState;
import com.dongz.hrm.common.utils.JwtUtils;
import com.dongz.hrm.domain.system.enums.PermissionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
        List<Map<String, Object>> list = this.queryForList("select t.username,t.mobile,t.password,t.enable_state as enableState,t.company_id as companyId,t.company_name as companyName from user t where t.mobile = :mobile and t.is_deleted = false", params);
        if (list.size() == 0) return Result.LOGINFAILE("用户不存在");
        if (list.size() > 1) return Result.LOGINFAILE("用户重复，请联系管理员！");

        Map<String, Object> map = list.get(0);
        if (!password.equals(map.get("password"))) return Result.LOGINFAILE("密码错误，请重新输入密码！");
        if (EnableState.Disable.equals(EnableState.parse((Integer) map.get("enableState"))))
            return Result.LOGINFAILE("用户已被禁用，请联系管理员！");

        map.remove("password");
        String token = jwtUtils.createJwt(mobile, (String) map.get("username"), map);
        return Result.LOGINSUCCESS(token);
    }

    @GetMapping("/userInfo")
    public Result login() {
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
        LevelState level = LevelState.parse(Integer.parseInt((String) userInfo.get("level")));
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
            roleList = this.queryForList("select p.code,p.type,p.full_code as fullCode from permission p " +
                    "left join role_permission o on p.id = o.permission_id " +
                    "left join role r on o.role_id = r.id " +
                    "left join user_role u on r.id = u.role_id where u.user_id = :id and r.is_deleted =0", params);
            getParentPermission(roleList);
        }
        Map<String, List<Map<String, Object>>> type = roleList.parallelStream().collect(Collectors.groupingBy(item -> PermissionStatus.parse((Integer) item.get("type")).name()));
        roles.put("menus", type.containsKey(PermissionStatus.MENU.name()) ? type.get(PermissionStatus.MENU.name()).parallelStream().map(item -> (String) item.get("code")).collect(Collectors.toList()) : null);
        roles.put("points", type.containsKey(PermissionStatus.POINT.name()) ? type.get(PermissionStatus.POINT.name()).parallelStream().map(item -> (String) item.get("code")).collect(Collectors.toList()) : null);
        roles.put("apis", type.containsKey(PermissionStatus.API.name()) ? type.get(PermissionStatus.API.name()).parallelStream().map(item -> (String) item.get("code")).collect(Collectors.toList()) : null);
        userInfo.put("roles", roles);
        return Result.SUCCESS(userInfo);
    }

    /**
     * 获取所有父级权限
     * @param roleList
     */
    private void getParentPermission(final List<Map<String, Object>> roleList) {
        //分割code，查询
        List<String> fullCode = Arrays.asList(roleList.parallelStream().map(item -> (String) item.get("fullCode")).distinct().collect(Collectors.joining(".")).split("\\.")).parallelStream().distinct().filter(item -> !"".equals(item)).collect(Collectors.toList());

        fullCode.parallelStream().forEach(item -> {
            Map<String, Object> params = new HashMap<>();
            params.put("code", item);
            List<Map<String, Object>> list = this.queryForList("select u.code,u.type,u.full_code as fullCode from permission u where u.code = :code", params);
            roleList.addAll(list);
        });
    }
}
