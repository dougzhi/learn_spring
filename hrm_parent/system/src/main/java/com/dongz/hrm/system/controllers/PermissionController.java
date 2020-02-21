package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.PageResult;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.common.enums.PermissionStatus;
import com.dongz.hrm.domain.system.Permission;
import com.dongz.hrm.domain.system.vos.PermissionVO;
import com.dongz.hrm.system.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from permission t ");
        Map<String, Object> params = new HashMap<>();
        PageResult<Map<String, Object>> pageResult = this.queryForPagination(sb, params, page, size);
        return Result.SUCCESS(pageResult);
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam Long id) {
        String sql = "select t.* from permission t where t.id = :id ";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Map<String, Object> map = this.queryForObject(sql, params);
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from ")
                .append(PermissionStatus.parse((Integer) map.get("type")).getTableName())
                .append(" t where t.id = :id");
        Map<String, Object> maps = this.queryForObject(sb.toString(), params);
        map.putAll(maps);
        return Result.SUCCESS(map);
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
