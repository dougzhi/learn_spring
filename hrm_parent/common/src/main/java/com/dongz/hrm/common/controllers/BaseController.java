package com.dongz.hrm.common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author dong
 * @date 2020/1/3 01:00
 * @desc
 */
public abstract class BaseController {

    @Autowired
    protected JdbcTemplate jdbcTemplate;
}