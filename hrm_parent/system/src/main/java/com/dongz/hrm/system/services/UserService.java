package com.dongz.hrm.system.services;

import com.dongz.hrm.common.services.BaseService;
import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.domain.system.User;
import com.dongz.hrm.domain.system.vos.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author dong
 * @date 2020/2/20 13:46
 * @desc
 */
@Service
@Transactional
public class UserService extends BaseService {

    @Autowired
    private IdWorker idWorker;

    /**
     * 新增
     * @param vo vo
     */
    public Long create(UserVO vo) {
        Assert.notNull(vo, "用户信息不能为空");
        Assert.hasText(vo.getUsername(), "用户名称不能为空");
        Assert.hasText(vo.getMobile(), "用户手机号码不能为空");

        long count = em.createQuery("select count(1) from User u where u.mobile = ?1 and u.isDeleted = false ", Long.class).setParameter(1, vo.getMobile()).getFirstResult();
        Assert.isTrue(count == 0, "用户手机号码重复， 新增失败");

        User user = new User();
        BeanUtils.copyProperties(vo, user);
        user.setId(idWorker.nextId());

        setCreate(user);
        em.persist(user);
        return user.getId();
    }

    /**
     * 修改
     * @param vo vo
     */
    public void update(UserVO vo) {
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
            Assert.isTrue((!first.isPresent()) || (first.get().getMobile().equals(vo.getMobile())), "用户手机号码重复， 新增失败");
            user.setMobile(vo.getMobile());
        }

        user.setUsername(vo.getUsername());

        setLastUpdate(user);
        em.merge(user);
    }

    /**
     * 删除
     * @param id id
     */
    public void delete(Long id) {
        Assert.notNull(id, "要删除的用户ID不能为空");

        User user = em.find(User.class, id);
        Assert.notNull(user, "用户信息不存在， 修改失败");
        Assert.isTrue(!user.isDeleted(), "用户信息已删除");

        setDelete(user);
        em.merge(user);
    }

    /**
     * 授权
     * @param id id
     */
    public void assignRoles(Long id,Long[] roleIds) {
        Assert.notNull(id, "要授权的用户ID不能为空");

        User user = em.find(User.class, id);
        Assert.notNull(user, "用户信息不存在， 修改失败");
        Assert.isTrue(!user.isDeleted(), "用户信息已删除");

    }
}
