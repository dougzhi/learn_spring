package com.dongz.hrm.company.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.PageResult;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.company.services.DepartmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dong
 * @date 2020/2/5 22:53
 * @desc
 */
@CrossOrigin
@RestController
@RequestMapping("/api/sys/user")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService service;

    @RequiresPermissions("API-DEPARTMENT-FINDALL")
    @GetMapping(value = "/findAll",name = "组织机构查询")
    public Result findAll(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from department t where t.is_deleted = false");
        Map<String, Object> params = new HashMap<>();
        PageResult<Map<String, Object>> pageResult = this.queryForPagination(sb, params, page, size);
        return Result.SUCCESS(pageResult);
    }
}
