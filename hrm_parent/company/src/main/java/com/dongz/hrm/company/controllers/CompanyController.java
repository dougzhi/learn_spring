package com.dongz.hrm.company.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entity.Result;
import com.dongz.hrm.domain.company.Company;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dong
 * @date 2020/1/3 01:00
 * @desc
 */
@RestController
@RequestMapping("/api/company")
public class CompanyController extends BaseController {

    @GetMapping("list")
    public Result list() {
        List<Company> query = jdbcTemplate.query("select * from co_company", new BeanPropertyRowMapper<>(Company.class));
        return Result.SUCCESS(query);
    }
}
