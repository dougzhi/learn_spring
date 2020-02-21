package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.PageResult;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.common.utils.StringCase;
import com.dongz.hrm.domain.system.Permission;
import com.dongz.hrm.domain.system.enums.PermissionStatus;
import com.dongz.hrm.domain.system.vos.PermissionVO;
import com.dongz.hrm.system.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dong
 * @date 2020/2/20 00:45
 * @desc
 */
@CrossOrigin
@RestController
@RequestMapping("/api/sys/permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService service;

    @GetMapping("/findAll")
    public Result findAll(
            @RequestParam Integer type,
            @RequestParam Long pid,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from permission t where t.type = :type and t.pid = :pid");
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("pid", pid);
        PageResult<Map<String, Object>> pageResult = this.queryForPagination(sb, params, page, size);
        return Result.SUCCESS(pageResult);
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam Long id) {
        String sql = "select t.* from permission t where t.id = :id ";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Map<String, Object> map = this.queryForObject(sql, params);
        PermissionStatus type = PermissionStatus.parse((Integer) map.get("type"));
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from ")
                .append(type.getTableName())
                .append(" t where t.id = :id");
        Map<String, Object> map1 = this.queryForObject(sb.toString(), params);
        map.putAll(map1);
        Map<String, Object> data = new HashMap<>();
        map.forEach((k, v) -> data.put(StringCase.underline2Camel(k), v));
        return Result.SUCCESS(data);
    }

    @PostMapping("/create")
    public Result create(@RequestBody PermissionVO vo) {
        service.create(vo);
        return Result.SUCCESS();
    }

    @PutMapping("/update")
    public Result update(@RequestBody PermissionVO vo) {
        service.update(vo);
        return Result.SUCCESS();
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam Long id) {
        service.delete(id);
        return Result.SUCCESS();
    }
}
