package com.dongz.hrm.system.services;

import com.dongz.hrm.domain.system.PermissionApi;
import com.dongz.hrm.domain.system.vos.PermissionVO;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * @author dong
 * @date 2020/2/21 17:43
 * @desc
 */
@Transactional
public class PermissionApiService {
    public void create(Object vo, EntityManager em) {
        PermissionVO permissionVO = (PermissionVO) vo;
        PermissionApi api = new PermissionApi();

        api.setId(permissionVO.getId());
        api.setApiUrl(permissionVO.getApiUrl());
        api.setApiMethod(permissionVO.getApiMethod());
        api.setApiLevel(permissionVO.getApiLevel());

        em.persist(api);
    }

    public void delete(Object id, EntityManager em) {
        Long permissionId = (Long) id;
        em.remove(em.getReference(PermissionApi.class, permissionId));
    }
}