package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.PageResult;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.domain.system.vos.RoleVO;
import com.dongz.hrm.system.services.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService service;

    @GetMapping("/findAll")
    public Result findAll(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from role t where t.is_deleted = 0");
        Map<String, Object> params = new HashMap<>();
        PageResult<Map<String, Object>> pageResult = this.queryForPagination(sb, params, page, size);
        return Result.SUCCESS(pageResult);
    }

    @GetMapping(value = "/findById",name = "通过id查询")
    public Result findById(@RequestParam Long id) {
        String sql = "select t.* from role t where t.id = :id and t.is_deleted = 0";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Map<String, Object> map = this.queryForObject(sql, params);

        sql = "select t.permission_id from role_permission t where t.role_id = :id";
        List<String> mapList = this.queryForList(sql, params, String.class);
        map.put("permIds", mapList);
        return Result.SUCCESS(map);
    }

    @PostMapping("/create")
    public Result create(@RequestBody RoleVO vo) {
        service.create(vo);
        return Result.SUCCESS();
    }

    @PutMapping("/update")
    public Result update(@RequestBody RoleVO vo) {
        service.update(vo);
        return Result.SUCCESS();
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam Long id) {
        service.delete(id);
        return Result.SUCCESS();
    }

    @RequiresPermissions("API-ROLE-ASSIGNPREM")
    @PostMapping(value = "/assignPrem", name = "角色授权")
    public Result assignPrem(@RequestParam Long id, @RequestParam("permIds") Long[] perms) {
        service.assignPrem(id, Arrays.asList(perms).stream().distinct().collect(Collectors.toList()));
        return Result.SUCCESS();
    }
}
