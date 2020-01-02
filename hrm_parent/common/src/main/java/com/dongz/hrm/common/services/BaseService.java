package com.dongz.hrm.common.services;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * @author dong
 * @date 2020/1/3 00:01
 * @desc
 */
public abstract class BaseService {

    @Autowired
    protected EntityManager entityManager;
}
