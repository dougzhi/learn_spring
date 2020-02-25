package com.dongz.hrm.company.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.common.shiro.sessions.ApiSession;
import com.dongz.hrm.domain.company.Company;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author dong
 * @date 2020/1/3 01:00
 * @desc
 */
@CrossOrigin
@RestController
@RequestMapping("/api/company")
public class CompanyController extends BaseController {

    @RequiresPermissions("API-COMPANY-LIST")
    @GetMapping(value = "list",name = "企业查询")
    public Result list() {
        List<Company> query = jdbcTemplate.query("select * from company", new BeanPropertyRowMapper<>(Company.class));
        return Result.SUCCESS(query);
    }

    @RequiresPermissions("API-COMPANY-INFO")
    @GetMapping(value = "info", name = "企业详情")
    public Result info(@RequestParam(value = "id") Long id) {
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        List<Company> query = jdbcTemplate.query("select * from company where id = :id ", params, new BeanPropertyRowMapper<>(Company.class));
        Assert.isTrue(query.size() == 1, "企业信息不存在");
        return Result.SUCCESS(query.get(0));
    }

    @GetMapping("getApiList")
    public Result getApiList() {
        Assert.notNull(ApiSession.topApiList, "企业管理api列表查询错误，请联系管理员");
        return Result.SUCCESS(ApiSession.topApiList);
    }

    @GetMapping("getApiMap")
    public Result getApiMap() {
        System.out.println(111);
        Assert.notNull(ApiSession.childrenApis, "企业管理api集合查询错误，请联系管理员");
        return Result.SUCCESS(ApiSession.childrenApis);
    }
}
