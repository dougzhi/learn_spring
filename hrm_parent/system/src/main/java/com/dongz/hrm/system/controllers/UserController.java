package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.PageResult;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.domain.system.vos.UserVO;
import com.dongz.hrm.system.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
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
@RequestMapping("/api/sys/user")
public class UserController extends BaseController {

    @Autowired
    private UserService service;

    @GetMapping("/findAll")
    public Result findAll(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from user t where t.is_deleted = 0");
        Map<String, Object> params = new HashMap<>();
        PageResult<Map<String, Object>> pageResult = this.queryForPagination(sb, params, page, size);
        return Result.SUCCESS(pageResult);
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam Long id) {
        String sql = "select t.* from user t where t.id = :id and t.is_deleted = 0";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<Map<String, Object>> list = this.queryForList(sql, params);
        Assert.isTrue(list.size() == 1, "查询失败");
        return Result.SUCCESS(list.get(0));
    }

    @PostMapping("/create")
    public Result create(@RequestBody UserVO vo) {
        service.create(vo);
        return Result.SUCCESS();
    }

    @PutMapping("/update")
    public Result update(@RequestBody UserVO vo) {
        service.update(vo);
        return Result.SUCCESS();
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam Long id) {
        service.delete(id);
        return Result.SUCCESS();
    }

    @PostMapping("/assignRoles")
    public Result assignRoles(@RequestParam Long id,@RequestParam("roleIds") Long[] roles) {
        service.assignRoles(id, roles);
        return Result.SUCCESS();
    }
}
