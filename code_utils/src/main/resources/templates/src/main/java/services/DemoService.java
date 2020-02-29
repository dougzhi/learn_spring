package ${pPackage}.services;

import ${pPackage}.entities.${ClassName};
<#if hasVO=true>
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

    /**
     * 新增
     *
     * @param vo vo
     */
    public Long create(${ClassName}VO vo) {
        Assert.notNull(vo, "${table.comment?default('xxx')}不能为空");
        //断言
        <#list table.columns as column>
        <#if column.notNull=true>
        <#if column.columnType=='String'>
        Assert.hasText(vo.${column.getName}(), "${column.columnComment?default('xxx')}不能为空");
        <#else>
        Assert.notNull(vo.${column.getName}(), "${column.columnComment?default('xxx')}不能为空");
        </#if>
        </#if>
        </#list>

        //唯一
        <#list table.columns as column>
        <#if column.only=true>
        long count = em.createQuery("select count(1) from ${table.className} u where u.${column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", Long.class).setParameter(1, vo.${column.getName}()).getSingleResult();
        Assert.isTrue(count == 0, "${column.columnComment}?default('xxx')重复， 新增失败");
        </#if>
        </#list>

        //外键
        <#list table.columns as column>
        <#if column.foreignColumn??>
        long count = em.createQuery("select count(1) from ${column.foreignColumn.table.className} u where u.${column.foreignColumn.column.fieldName} = ?1 <#if table.extendsBase>and u.isDeleted = false</#if>", Long.class).setParameter(1, vo.${column.getName}()).getSingleResult();
        Assert.isTrue(count == 0, "${column.columnComment}?default('xxx')外键关联不存在， 新增失败");
        </#if>
        </#list>

        ${table.className} entity = new ${table.className}();
        BeanUtils.copyProperties(vo, entity);
        entity.setId(idWorker.nextId());

        setCreate(entity);
        em.persist(entity);
        return entity.getId();
    }

    /**
     * 修改
     *
     * @param vo vo
     */
    public void update(${ClassName}VO vo) {
        Assert.notNull(vo, "用户信息不能为空");
        Assert.notNull(vo.getId(), "要修改的用户ID不能为空");
        Assert.hasText(vo.getUsername(), "用户名称不能为空");
        Assert.hasText(vo.getMobile(), "用户手机号码不能为空");

        User user = em.find(User.class, vo.getId());
        Assert.notNull(user, "用户信息不存在， 修改失败");
        Assert.isTrue(!user.isDeleted(), "用户信息已删除");

        // 用户手机号码
        if (!user.getMobile().equals(vo.getMobile())) {
            Optional<User> first = em.createQuery("select u from User u where u.mobile = ?1 and u.isDeleted = false ", User.class).setParameter(1, vo.getMobile()).getResultStream().findFirst();
            Assert.isTrue((!first.isPresent()) || (first.get().getMobile().equals(vo.getMobile())), "用户手机号码重复， 修改失败");
            user.setMobile(vo.getMobile());
        }

        user.setUsername(vo.getUsername());
        user.setEnableState(EnableState.Enable);
        user.setFormOfEmployment(FormOfEmployment.Formal);

        setLastUpdate(user);
        em.merge(user);
    }

    /**
     * 删除
     *
     * @param id id
     */
    public void delete(Long id) {
        Assert.notNull(id, "要删除的用户ID不能为空");

        User user = em.find(User.class, id);
        Assert.notNull(user, "用户信息不存在， 修改失败");
        Assert.isTrue(!user.isDeleted(), "用户信息已删除");

        //删除角色关联
        List<UserRole> resultList = em.createQuery("select u from UserRole u where u.userId = ?1", UserRole.class).setParameter(1, id).getResultList();
        resultList.forEach(item -> em.remove(item));

        setDelete(user);
        em.merge(user);
    }

    /**
     * 授权
     *
     * @param id id
     */
    public void assignRoles(Long id, List<Long> roleIds) {
        Assert.notNull(id, "要授权的用户ID不能为空");

        User user = em.find(User.class, id);
        Assert.notNull(user, "用户信息不存在， 修改失败");
        Assert.isTrue(!user.isDeleted(), "用户信息已删除");

        long count = em.createQuery("select count(1) from Role u where u.id in (:ids) and u.isDeleted = false", Long.class).setParameter("ids", roleIds).getSingleResult();
        Assert.isTrue(count == roleIds.size(), "角色信息异常");

        List<UserRole> list = em.createQuery("select u from UserRole u where u.userId = :id", UserRole.class).setParameter("id", id).getResultList();
        // 取左差集，新增
        List<Long> roleIdList = list.parallelStream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Long> createList = roleIds.parallelStream().filter(item -> !roleIdList.contains(item)).collect(Collectors.toList());
        // 取右差集，删除
        List<UserRole> removeList = list.parallelStream().filter(item -> !roleIds.contains(item.getRoleId())).collect(Collectors.toList());

        createList.forEach(item -> {
            UserRole userRole = new UserRole(id, item);
            em.persist(userRole);
        });
        removeList.forEach(item -> em.remove(item));

        if ((createList.size() + removeList.size()) > 0) {
            SystemSessionManager.reloadAuthorizing(authService.getProfile(user));
        }
    }
}
