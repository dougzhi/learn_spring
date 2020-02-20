package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.PageResult;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.domain.system.vos.RoleVO;
import com.dongz.hrm.system.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService service;

    @GetMapping("/findAll")
    public Result findAll(
            @RequestParam(required = false, defaultValue = "1") Integer currPage,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from role t where t.is_deleted = 0");
        Map<String, Object> params = new HashMap<>();
        PageResult<Map<String, Object>> pageResult = this.queryForPagination(sb, params, currPage, pageSize);
        return Result.SUCCESS(pageResult);
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam Long id) {
        String sql = "select t.* from role t where t.id = :id and t.is_deleted = 0";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<Map<String, Object>> list = this.queryForList(sql, params);
        Assert.isTrue(list.size() == 1, "查询失败");
        return Result.SUCCESS(list.get(0));
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
}
