package com.dongz.hrm.company.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.common.shiro.sessions.ApiSession;
import com.dongz.hrm.domain.company.Company;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ApiSession apiSession;

    @RequiresPermissions("API-COMPANY-LIST")
    @GetMapping(value = "list",name = "企业查询")
    public Result list() {
        List<Company> query = jdbcTemplate.query("select * from company", new BeanPropertyRowMapper<>(Company.class));
        return Result.SUCCESS(query);
    }

    @GetMapping("info")
    public Result info(@RequestParam(value = "id") Long id) {
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        List<Company> query = jdbcTemplate.query("select * from company where id = :id ", params, new BeanPropertyRowMapper<>(Company.class));
        Assert.isTrue(query.size() == 1, "企业信息不存在");
        return Result.SUCCESS(query.get(0));
    }
}
