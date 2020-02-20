package com.dongz.hrm.common.controllers;

import com.dongz.hrm.common.entities.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author dong
 * @date 2020/1/3 01:00
 * @desc
 */
public abstract class BaseController {

    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;

    public <T> List<T> queryForList(String sql, Map<String,Object> params,Class<T> t) {
        return this.jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(t));
    }

    public List<Map<String, Object>> queryForList(String sql, Map<String, Object> params) {
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public <T> PageResult<T> queryForPagination(StringBuilder sql, Map<String, Object> params, long currPage, long pageSize,Class<T> t) {
        if (pageSize <= 0) {
            pageSize = 10;
        }

        long totalRow = this.jdbcTemplate.queryForObject("select count(*) from (" + sql + ") as T", params, Long.class);
        long pageCount = totalRow / pageSize;
        if (totalRow % pageSize > 0) {
            pageCount++;
        }
        if (currPage <= 0 || currPage > pageCount) {
            currPage = 1;
        }

        long offset = (currPage - 1) * pageSize;

        List<T> data = this.jdbcTemplate
                .query("select * from (" + sql.toString() + ") as T limit " + offset + "," + pageSize, params, new BeanPropertyRowMapper<>(t));
        return new PageResult<>(totalRow, pageCount, currPage, pageSize, data);
    }

    public PageResult<Map<String, Object>> queryForPagination(StringBuilder sql, Map<String, Object> params, long currPage, long pageSize) {
        if (pageSize <= 0) {
            pageSize = 10;
        }

        long totalRow = this.jdbcTemplate.queryForObject("select count(*) from (" + sql + ") as T", params, Long.class);
        long pageCount = totalRow / pageSize;
        if (totalRow % pageSize > 0) {
            pageCount++;
        }
        if (currPage <= 0 || currPage > pageCount) {
            currPage = 1;
        }

        long offset = (currPage - 1) * pageSize;

        List<Map<String, Object>> data = this.jdbcTemplate
                .queryForList("select * from (" + sql.toString() + ") as T limit " + offset + "," + pageSize, params);
        return new PageResult<>(totalRow, pageCount, currPage, pageSize, data);
    }
}
