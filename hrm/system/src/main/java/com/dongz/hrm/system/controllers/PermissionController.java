package com.dongz.hrm.system.controllers;

import com.dongz.hrm.common.controllers.BaseController;
import com.dongz.hrm.common.entities.Result;
import com.dongz.hrm.common.shiro.sessions.ApiSession;
import com.dongz.hrm.domain.system.enums.PermissionStatus;
import com.dongz.hrm.domain.system.vos.PermissionVO;
import com.dongz.hrm.system.controllers.clients.CompanyFeignClient;
import com.dongz.hrm.system.services.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    @Autowired
    private CompanyFeignClient companyFeignClient;

    @GetMapping("/findAll")
    public Result findAll(
            Integer type,Long pid) {
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from permission t where 1=1");
        Map<String, Object> params = new HashMap<>();
        if (type != null) {
            sb.append(" and t.type = :type");
            params.put("type", type);
        }
        if (pid != null) {
            sb.append(" and t.pid = :pid");
            params.put("pid", pid);
        }
        List<Map<String, Object>> pageResult = this.queryForList(sb.toString(), params);
        return Result.SUCCESS(pageResult);
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam Long id) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String sql = "select t.*,t.is_visible as isVisible from permission t where t.id = :id ";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Map<String, Object> map = this.queryForObject(sql, params);
        PermissionStatus type = PermissionStatus.parse((Integer) map.get("type"));
        Class clazz = type.getClazz();
        StringBuilder sb = new StringBuilder();
        sb.append("select t.* from ")
                .append(type.getTableName())
                .append(" t where t.id = :id");
        Method me = clazz.getDeclaredMethod("getDataBaseMap", Object.class);
        Object o = this.queryForObject(sb.toString(), params, clazz);
        Map<String, Object> invoke = (Map<String, Object>) me.invoke(clazz.newInstance(),o);
        map.putAll(invoke);
        return Result.SUCCESS(map);
    }

    @RequiresPermissions("API-PERMISSION-ADD")
    @PostMapping(value = "/create",name = "权限新增")
    public Result create(@RequestBody PermissionVO vo) {
        service.create(vo);
        return Result.SUCCESS();
    }

    @PutMapping("/update")
    public Result update(@RequestBody PermissionVO vo) {
        service.update(vo);
        return Result.SUCCESS();
    }

    @RequiresPermissions("API-PERMISSION-DELETE")
    @DeleteMapping(value = "/deleteById",name = "权限删除")
    public Result deleteById(@RequestParam Long id) {
        service.delete(id);
        return Result.SUCCESS();
    }

    @GetMapping("/getApis")
    public Result getApis() {
        List<String> topApiList = new ArrayList<>(ApiSession.topApiList);
        //company项目所有api
        List<String> companyApiList = (List<String>) companyFeignClient.getApiList().getData();
        topApiList.addAll(companyApiList);

        return Result.SUCCESS(topApiList);
    }

    @GetMapping("/getChildApis")
    public Result getChildApis(@RequestParam String name) {
        Map<String, List<ApiSession.Auth>> childrenApis = new HashMap<>(ApiSession.childrenApis);
        //company项目所有api
        Map<String, List<ApiSession.Auth>> companyApiMap = (Map<String, List<ApiSession.Auth>>) companyFeignClient.getApiMap().getData();
        childrenApis.putAll(companyApiMap);
        Assert.isTrue(!childrenApis.containsKey(name), "api列表不存在");
        List<ApiSession.Auth> auths = childrenApis.get(name);
        return Result.SUCCESS(auths);
    }
}
