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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
@RequestMapping("/api/sys/permission")
public class DemoController extends BaseController {

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
        Assert.notNull(ApiSession.topApiList, "系统管理api列表获取失败，请联系管理员");
        Assert.notNull(ApiSession.childrenApis, "系统管理api集合获取失败，请联系管理员");
        List<String> topApiList = new ArrayList<>(ApiSession.topApiList);
        Map<String, List<Map<String, Object>>> childrenApis = new HashMap<>(ApiSession.childrenApis);
        //company项目所有api
        Result apiList = companyFeignClient.getApiList();
        Assert.isTrue(apiList.isSuccess(), "企业管理系统api列表获取失败，请联系管理员");
        List<String> companyApiList = (List<String>) apiList.getData();
        topApiList.addAll(companyApiList);

        //company项目所有api
        Result apiMap = companyFeignClient.getChildrenApiMap();
        Assert.isTrue(apiMap.isSuccess(), "企业管理系统api集合获取失败，请联系管理员");
        Map<String, List<Map<String, Object>>> companyApiMap = (Map<String, List<Map<String, Object>>>) apiMap.getData();
        //合并
        mergeMap(childrenApis, companyApiMap);
        //去重
        List<Map<String, Object>> collect = topApiList.stream().distinct().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("value", item);
            map.put("label", item);
            map.put("children", childrenApis.get(item).stream().map(e -> {
                Map<String, Object> child = new HashMap<>();
                child.put("value", e.get("authUrl"));
                child.put("label", e.get("authUrl"));
                return child;
            }).collect(Collectors.toList()));
            return map;
        }).collect(Collectors.toList());
        return Result.SUCCESS(collect);
    }

    @GetMapping("/getApi")
    public Result getApi(@RequestParam String name) {
        Assert.notNull(ApiSession.apiMaps, "系统管理api信息获取失败，请联系管理员");
        Map<String, Map<String, Object>> childrenApis = new HashMap<>(ApiSession.apiMaps);

        if (childrenApis.containsKey(name)) {
            return Result.SUCCESS(childrenApis.get(name));
        }

        Result apiMaps = companyFeignClient.getApiMaps();
        Assert.isTrue(apiMaps.isSuccess(), "企业管理系统api信息获取失败，请联系管理员");
        Map<String, Map<String, Object>> data = (Map<String, Map<String, Object>>) apiMaps.getData();

        return Result.SUCCESS(data.getOrDefault(name, null));
    }
}
