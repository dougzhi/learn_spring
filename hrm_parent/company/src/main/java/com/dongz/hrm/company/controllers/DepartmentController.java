package com.dongz.hrm.company.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entity.Result;
import com.dongz.hrm.company.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dong
 * @date 2020/2/5 22:53
 * @desc
 */
@CrossOrigin
@RestController
@RequestMapping("/api/department")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService service;

    @RequestMapping("getList")
    public Result getList() {
        Map<String, Object> params = new HashMap<>();
        String sql = "";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, params);
        return Result.SUCCESS(maps);
    }
}
