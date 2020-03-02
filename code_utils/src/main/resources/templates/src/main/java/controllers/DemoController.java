package ${pPackage}.controllers;

import ${pPackage}.controllers.BaseController;
import ${pPackage}.entities.Result;
import ${pPackage}.shiro.sessions.ApiSession;
<#if hasVO>
import ${pPackage}.entities.vos.${ClassName}VO;
</#if>
import ${pPackage}.services.${ClassName}Service;
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
 * @author ${author}
 * @date ${currTime?datetime}
 * @desc
 */
@CrossOrigin
@RestController
@RequestMapping("/api/${table.shortName}")
public class ${ClassName}Controller extends BaseController {

    @Autowired
    private ${ClassName}Service service;

    @GetMapping(value = "/list", name="分页")
    public Result list(
            <#list table.columns as column>
            <#if column.selected && !baseEntity?contains(column.fieldName)>
            @RequestParam(required = false) ${column.columnType} ${column.fieldName},
            </#if>
            </#list>
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
        ) {
        StringBuilder sb = new StringBuilder();
        <#if hasVO>
        sb.append("select <#list tableVO.columns as column><#if column.selected && column.notNull && !baseEntity?contains(column.fieldName)>t.${column.columnName} as ${column.fieldName}<#if column_has_next>,</#if></#if></#list> from permission t <#if !foreignTables??>where 1=1</#if>");
        <#else>
        sb.append("select <#list table.columns as column><#if column.selected && column.notNull && !baseEntity?contains(column.fieldName)>t.${column.columnName} as ${column.fieldName}<#if column_has_next>,</#if></#if></#list> from permission t <#if !foreignTables??>where 1=1</#if>");
        </#if>
        <#list foreignTables as foreignTable>
        sb.append(" left join ${foreignTable.table.name} as t${foreignTable_index + 1} on t${foreignTable_index + 1}.${foreignTable.foreignColumn.columnName} = t.${foreignTable.column.columnName} <#if !foreignTable_has_next>where 1=1 </#if>");
        </#list>
        Map<String, Object> params = new HashMap<>();
        <#list table.columns as column>
        <#if column.selected && !baseEntity?contains(column.fieldName)>
        <#if column.columnType=='String'>
        if (StringUtils.isNotBlank(${column.fieldName})) {
        <#else>
        if (${column.fieldName} != null) {
        </#if>
            sb.append(" and t.${column.columnName} = :${column.fieldName}");
            params.put("${column.fieldName}", ${column.fieldName});
        }
        </#if>
        </#list>

        PageResult<Map<String, Object>> pageResult = this.queryForPagination(sb, params, page, size);
        return Result.SUCCESS(pageResult);
    }

    @GetMapping(value = "/info", name="详情")
    public Result info(@RequestParam Long id) {
        <#if hasVO>
        String sql = "select <#list tableVO.columns as column><#if column.selected && column.notNull && !baseEntity?contains(column.fieldName)>t.${column.columnName} as ${column.fieldName}<#if column_has_next>,</#if></#if></#list> from permission t <#if !foreignTables??>where t.id = :id </#if>";
        <#else>
        String sql = "select <#list table.columns as column><#if column.selected && column.notNull && !baseEntity?contains(column.fieldName)>t.${column.columnName} as ${column.fieldName}<#if column_has_next>,</#if></#if></#list> from permission t <#if !foreignTables??>where t.id = :id </#if>";
        </#if>
        <#list foreignTables as foreignTable>
        sb.append(" left join ${foreignTable.table.name} as t${foreignTable_index + 1} on t${foreignTable_index + 1}.${foreignTable.foreignColumn.columnName} = t.${foreignTable.column.columnName} <#if !foreignTable_has_next>where t.id = :id </#if>");
        </#list>
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Map<String, Object> map = this.queryForObject(sql, params);
        return Result.SUCCESS(map);
    }

    <#if hasVO>
    @PostMapping(value = "/create",name = "新增")
    public Result create(@RequestBody ${ClassName}VO vo) {
        service.create(vo);
        return Result.SUCCESS();
    }

    @PutMapping(value = "/update",name = "修改")
    public Result update(@RequestBody ${ClassName}VO vo) {
        service.update(vo);
        return Result.SUCCESS();
    }
    <#else>
    @PostMapping(value = "/create",name = "新增")
    public Result create(
            <#list table.columns as column>
            <#if column.selected && !baseEntity?contains(column.fieldName) && column.fieldName != 'id'>
            @RequestParam(required = false) ${column.columnType} ${column.fieldName}<#if column_has_next>,</#if>
            </#if>
            </#list>
        ) {
        service.create(<#list table.columns as column><#if column.selected && !baseEntity?contains(column.fieldName) && column.fieldName != 'id'>${column.fieldName}<#if column_has_next>, </#if></#if></#list>);
        return Result.SUCCESS();
    }

    @PutMapping(value = "/update",name = "修改")
    public Result update(
            <#list table.columns as column>
            <#if column.selected && !baseEntity?contains(column.fieldName)>
            @RequestParam(required = false) ${column.columnType} ${column.fieldName}<#if column_has_next>,</#if>
            </#if>
            </#list>
        ) {
        service.update(<#list table.columns as column><#if column.selected && !baseEntity?contains(column.fieldName)>${column.fieldName}<#if column_has_next>, </#if></#if></#list>);
        return Result.SUCCESS();
    }
    </#if>

    @DeleteMapping(value = "/deleteById",name = "删除")
    public Result deleteById(@RequestParam Long id) {
        service.delete(id);
        return Result.SUCCESS();
    }
}
