package com.dongz.hrm.system.services;

import com.dongz.hrm.domain.system.PermissionMenu;
import com.dongz.hrm.domain.system.vos.PermissionVO;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


/**
 * @author dong
 * @date 2020/2/21 17:44
 * @desc
 */
@Transactional
public class PermissionMenuService {
    public void create(Object vo, EntityManager em) {
        PermissionVO permissionVO = (PermissionVO) vo;
        PermissionMenu menu = new PermissionMenu();

        menu.setId(permissionVO.getId());
        menu.setMenuIcon(permissionVO.getMenuIcon());
        menu.setMenuOrder(permissionVO.getMenuOrder());

        em.persist(menu);
    }

    public void delete(Object id, EntityManager em) {
        Long permissionId = (Long) id;
        em.remove(em.getReference(PermissionMenu.class, permissionId));
    }
}