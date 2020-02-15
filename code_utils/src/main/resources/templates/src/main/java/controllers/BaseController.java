package com.dongz.hrm.common.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author ${author}
 * @date ${currTime}
 * @desc
 */
public abstract class BaseController {

    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;
}
