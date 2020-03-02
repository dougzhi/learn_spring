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

        ${table.className} entity = new ${table.className}();
        BeanUtils.copyProperties(vo, entity);
        entity.setId(idWorker.nextId());

        setCreate(entity);
        em.persist(entity);
        return entity.getId();
    }
    <#else>
    /**
     * 新增
     *
     */
    public Long create(<#list table.columns as column><#if column.selected && !baseEntity?contains(column.fieldName) && column.fieldName != 'id'>${column.columnType} ${column.fieldName}<#if column_has_next>,</#if> </#if></#list>) {
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

        ${table.className} entity = new ${table.className}();

        entity.setId(idWorker.nextId());
        <#list table.columns as column>
        <#if column.selected && !baseEntity?contains(column.fieldName)>
        entity.${column.setName}(${column.fieldName});
        </#if>
        </#list>

        setCreate(entity);
        em.persist(entity);
        return entity.getId();
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

        ${table.className} entity = em.find(${table.className}.class, vo.getId());
        Assert.notNull(entity, "${table.comment?default('xxx')}不存在， 修改失败");
        <#if table.extendsBase>
        Assert.isTrue(!entity.isDeleted(), "${table.comment?default('xxx')}已删除");
        </#if>

        // 唯一
        <#list table.columns as column>
        <#if column.selected && column.only>
        if (!entity.${column.getName}().equals(vo.${column.getName}())) {
            Optional<${table.className}> optional = em.createQuery("select u from ${table.className} u where u.${column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", ${table.className}.class).setParameter(1, vo.${column.getName}()).getResultStream().findFirst();
            Assert.isTrue((!optional.isPresent()) || (optional.get().${column.getName}().equals(vo.${column.getName}())), "${column.columnComment?default('xxx')}重复， 修改失败");
            entity.${column.setName}(vo.${column.getName}());
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
        entity.${column.setName}(vo.${column.getName}());
        </#if>
        </#list>

        setLastUpdate(entity);
        em.merge(entity);
    }
    <#else>
    /**
     * 修改
     *
     */
    public void update(<#list table.columns as column><#if column.selected && !baseEntity?contains(column.fieldName)>${column.columnType} ${column.fieldName}<#if column_has_next>,</#if> </#if></#list>) {
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

        ${table.className} entity = em.find(${table.className}.class, id);
        Assert.notNull(entity, "${table.comment?default('xxx')}不存在， 修改失败");
        <#if table.extendsBase>
        Assert.isTrue(!entity.isDeleted(), "${table.comment?default('xxx')}已删除");
        </#if>

        // 唯一
        <#list table.columns as column>
        <#if column.selected && column.only>
        if (!entity.${column.getName}().equals(${column.fieldName})) {
        Optional<${table.className}> optional = em.createQuery("select u from ${table.className} u where u.${column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", ${table.className}.class).setParameter(1, ${column.fieldName}).getResultStream().findFirst();
        Assert.isTrue((!optional.isPresent()) || (optional.get().${column.getName}().equals(${column.fieldName})), "${column.columnComment?default('xxx')}重复， 修改失败");
        entity.${column.setName}(${column.fieldName});
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
        entity.${column.setName}(${column.fieldName});
        </#if>
        </#list>

        setLastUpdate(entity);
        em.merge(entity);
    }
    </#if>

    /**
     * 删除
     *
     * @param id id
     */
    public void delete(Long id) {
        Assert.notNull(id, "要删除的${table.comment?default('xxx')}ID不能为空");

        ${table.className} entity = em.find(${table.className}.class, id);
        Assert.notNull(entity, "${table.comment?default('xxx')}不存在， 删除失败");
        <#if table.extendsBase>
        Assert.isTrue(!entity.isDeleted(), "${table.comment?default('xxx')}已删除");

        setDelete(entity);
        em.merge(entity);
        <#else>
        em.remove(entity);
        </#if>
    }
}
