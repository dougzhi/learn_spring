package com.dongz.hrm.system.services;

import com.dongz.hrm.domain.system.PermissionPoint;
import com.dongz.hrm.domain.system.vos.PermissionVO;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * @author dong
 * @date 2020/2/21 17:45
 * @desc
 */
@Transactional
public class PermissionPointService {
    public void create(Object vo, EntityManager em) {
        PermissionVO permissionVO = (PermissionVO) vo;
        PermissionPoint point = new PermissionPoint();

        point.setId(permissionVO.getId());
        point.setPointClass(permissionVO.getPointClass());
        point.setPointIcon(permissionVO.getPointIcon());
        point.setPointStatus(permissionVO.getPointStatus());

        em.persist(point);
    }

    public void delete(Object id, EntityManager em) {
        Long permissionId = (Long) id;
        em.remove(em.getReference(PermissionPoint.class, permissionId));
    }
}
