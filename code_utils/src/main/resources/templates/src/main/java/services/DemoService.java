package ${pPackage}.services;

import ${pPackage}.entities.${ClassName};
<#if hasVO>
import ${pPackage}.entities.vos.${ClassName}VO;
</#if>
<#list foreignTables as foreignTable>
import ${pPackage}.entities.${foreignTable.className};
</#list>
import ${pPackage}.services.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ${author}
 * @date ${currTime?datetime}
 * @desc
 */
@Service
@Transactional
public class ${ClassName}Service extends BaseService {

    @Autowired
    private IdWorker idWorker;

    <#if hasVO>
    /**
     * 新增
     *
     * @param vo vo
     */
    public Long create(${ClassName}VO vo) {
        Assert.notNull(vo, "${table.comment?default('xxx')}不能为空");
        //断言
        <#list tableVO.columns as column>
        <#if column.selected && column.notNull && !baseEntity?contains(column.fieldName) && column.fieldName != 'id'>
        <#if column.columnType=='String'>
        Assert.hasText(vo.${column.getName}(), "${column.columnComment?default('xxx')}不能为空");
        <#else>
        Assert.notNull(vo.${column.getName}(), "${column.columnComment?default('xxx')}不能为空");
        </#if>
        </#if>
        </#list>

        //唯一
        <#list table.columns as column>
        <#if column.selected && column.only>
        long count = em.createQuery("select count(1) from ${table.className} u where u.${column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", Long.class).setParameter(1, vo.${column.getName}()).getSingleResult();
        Assert.isTrue(count == 0, "${column.columnComment?default('xxx')}重复， 新增失败");
        </#if>
        </#list>

        //外键
        <#list table.columns as column>
        <#if column.selected && column.foreignColumn??>
        long count = em.createQuery("select count(1) from ${column.foreignColumn.table.className} u where u.${column.foreignColumn.column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", Long.class).setParameter(1, vo.${column.getName}()).getSingleResult();
        Assert.isTrue(count == 0, "${column.columnComment?default('xxx')}外键关联不存在， 新增失败");
        </#if>
        </#list>

        ${table.className} ${table.shortName} = new ${table.className}();
        BeanUtils.copyProperties(vo, ${table.shortName});
        ${table.shortName}.setId(idWorker.nextId());

        setCreate(${table.shortName});
        em.persist(${table.shortName});
        return ${table.shortName}.getId();
    }
    <#else>
    /**
     * 新增
     *
     */
    public Long create(<#list table.columns as column><#if column.selected && !baseEntity?contains(column.fieldName) && column.fieldName != 'id'>${column.columnType} ${column.fieldName}<#if column_has_next>, </#if></#if></#list>) {
        //断言
        <#list table.columns as column>
        <#if column.selected && column.notNull=true && !baseEntity?contains(column.fieldName) && column.fieldName != 'id'>
        <#if column.columnType=='String'>
        Assert.hasText(${column.fieldName}, "${column.columnComment?default('xxx')}不能为空");
        <#else>
        Assert.notNull(${column.fieldName}, "${column.columnComment?default('xxx')}不能为空");
        </#if>
        </#if>
        </#list>

        //唯一
        <#list table.columns as column>
        <#if column.selected && column.only>
        long count = em.createQuery("select count(1) from ${table.className} u where u.${column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", Long.class).setParameter(1, ${column.fieldName}).getSingleResult();
        Assert.isTrue(count == 0, "${column.columnComment?default('xxx')}重复， 新增失败");
        </#if>
        </#list>

        //外键
        <#list table.columns as column>
        <#if column.selected && column.foreignColumn??>
        long count = em.createQuery("select count(1) from ${column.foreignColumn.table.className} u where u.${column.foreignColumn.column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", Long.class).setParameter(1, ${column.fieldName}).getSingleResult();
        Assert.isTrue(count == 0, "${column.columnComment?default('xxx')}外键关联不存在， 新增失败");
        </#if>
        </#list>

        ${table.className} ${table.shortName} = new ${table.className}();

        ${table.shortName}.setId(idWorker.nextId());
        <#list table.columns as column>
        <#if column.selected && !baseEntity?contains(column.fieldName)>
        ${table.shortName}.${column.setName}(${column.fieldName});
        </#if>
        </#list>

        setCreate(${table.shortName});
        em.persist(${table.shortName});
        return ${table.shortName}.getId();
    }
    </#if>

    <#if hasVO>
    /**
     * 修改
     *
     * @param vo vo
     */
    public void update(${ClassName}VO vo) {
        Assert.notNull(vo, "${table.comment?default('xxx')}不能为空");
        //断言
        <#list tableVO.columns as column>
        <#if column.selected && column.notNull && !baseEntity?contains(column.fieldName)>
        <#if column.columnType=='String'>
        Assert.hasText(vo.${column.getName}(), "${column.columnComment?default('xxx')}不能为空");
        <#else>
        Assert.notNull(vo.${column.getName}(), "${column.columnComment?default('xxx')}不能为空");
        </#if>
        </#if>
        </#list>

        ${table.className} ${table.shortName} = em.find(${table.className}.class, vo.getId());
        Assert.notNull(${table.shortName}, "${table.comment?default('xxx')}不存在， 修改失败");
        <#if table.extendsBase>
        Assert.isTrue(!${table.shortName}.isDeleted(), "${table.comment?default('xxx')}已删除");
        </#if>

        // 唯一
        <#list table.columns as column>
        <#if column.selected && column.only>
        if (!${table.shortName}.${column.getName}().equals(vo.${column.getName}())) {
            Optional<${table.className}> optional = em.createQuery("select u from ${table.className} u where u.${column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", ${table.className}.class).setParameter(1, vo.${column.getName}()).getResultStream().findFirst();
            Assert.isTrue(!optional.isPresent() && optional.get().${column.getName}().equals(vo.${column.getName}()), "${column.columnComment?default('xxx')}重复， 修改失败");
            ${table.shortName}.${column.setName}(vo.${column.getName}());
        }
        </#if>
        </#list>

        //外键
        <#list table.columns as column>
        <#if column.selected && column.foreignColumn??>
        long count = em.createQuery("select count(1) from ${column.foreignColumn.table.className} u where u.${column.foreignColumn.column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", Long.class).setParameter(1, ${column.fieldName}).getSingleResult();
        Assert.isTrue(count == 0, "${column.columnComment?default('xxx')}外键关联不存在， 新增失败");
        </#if>
        </#list>

        <#list table.columns as column>
        <#if column.selected && !baseEntity?contains(column.fieldName) && column.fieldName != 'id'>
        ${table.shortName}.${column.setName}(vo.${column.getName}());
        </#if>
        </#list>

        setLastUpdate(${table.shortName});
        em.merge(${table.shortName});
    }
    <#else>
    /**
     * 修改
     *
     */
    public void update(<#list table.columns as column><#if column.selected && !baseEntity?contains(column.fieldName)>${column.columnType} ${column.fieldName}<#if column_has_next>, </#if></#if></#list>) {
        //断言
        <#list table.columns as column>
        <#if column.selected && column.notNull=true && !baseEntity?contains(column.fieldName) && column.fieldName != 'id'>
        <#if column.columnType=='String'>
        Assert.hasText(${column.fieldName}, "${column.columnComment?default('xxx')}不能为空");
        <#else>
        Assert.notNull(${column.fieldName}, "${column.columnComment?default('xxx')}不能为空");
        </#if>
        </#if>
        </#list>

        ${table.className} ${table.shortName} = em.find(${table.className}.class, id);
        Assert.notNull(${table.shortName}, "${table.comment?default('xxx')}不存在， 修改失败");
        <#if table.extendsBase>
        Assert.isTrue(!${table.shortName}.isDeleted(), "${table.comment?default('xxx')}已删除");
        </#if>

        // 唯一
        <#list table.columns as column>
        <#if column.selected && column.only>
        if (!${table.shortName}.${column.getName}().equals(${column.fieldName})) {
        Optional<${table.className}> optional = em.createQuery("select u from ${table.className} u where u.${column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", ${table.className}.class).setParameter(1, ${column.fieldName}).getResultStream().findFirst();
        Assert.isTrue(!optional.isPresent() || optional.get().${column.getName}().equals(${column.fieldName}), "${column.columnComment?default('xxx')}重复， 修改失败");
        ${table.shortName}.${column.setName}(${column.fieldName});
        }
        </#if>
        </#list>

        //外键
        <#list table.columns as column>
        <#if column.selected && column.foreignColumn??>
        long count = em.createQuery("select count(1) from ${column.foreignColumn.table.className} u where u.${column.foreignColumn.column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", Long.class).setParameter(1, ${column.fieldName}).getSingleResult();
        Assert.isTrue(count == 0, "${column.columnComment?default('xxx')}外键关联不存在， 新增失败");
        </#if>
        </#list>

        <#list table.columns as column>
        <#if column.selected && !baseEntity?contains(column.fieldName) && column.fieldName != 'id'>
        ${table.shortName}.${column.setName}(${column.fieldName});
        </#if>
        </#list>

        setLastUpdate(${table.shortName});
        em.merge(${table.shortName});
    }
    </#if>

    /**
     * 删除
     *
     * @param id id
     */
    public void delete(Long id) {
        Assert.notNull(id, "要删除的${table.comment?default('xxx')}ID不能为空");

        ${table.className} ${table.shortName} = em.find(${table.className}.class, id);
        Assert.notNull(${table.shortName}, "${table.comment?default('xxx')}不存在， 删除失败");
        <#if table.extendsBase>
        Assert.isTrue(!${table.shortName}.isDeleted(), "${table.comment?default('xxx')}已删除");

        setDelete(${table.shortName});
        em.merge(${table.shortName});
        <#else>
        em.remove(${table.shortName});
        </#if>
    }
}
