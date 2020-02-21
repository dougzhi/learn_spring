package com.dongz.hrm.common.controllers;

import com.dongz.hrm.common.entities.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

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

    public Map<String,Object> queryForObject(String sql, Map<String, Object> params) {
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, params);
        Assert.isTrue(list.size() == 1, "查询条数异常");
        return list.get(0);
    }

    public List<Map<String, Object>> queryForList(String sql, Map<String, Object> params) {
        return this.jdbcTemplate.queryForList(sql, params);
    }

    public <T> PageResult<T> queryForPagination(StringBuilder sql, Map<String, Object> params, long page, long size,Class<T> t) {
        if (size <= 0) {
            size = 10;
        }

        long totalRow = this.jdbcTemplate.queryForObject("select count(*) from (" + sql + ") as T", params, Long.class);
        long pageCount = totalRow / size;
        if (totalRow % size > 0) {
            pageCount++;
        }
        if (page <= 0 || page > pageCount) {
            page = 1;
        }

        long offset = (page - 1) * size;

        List<T> data = this.jdbcTemplate
                .query("select * from (" + sql.toString() + ") as T limit " + offset + "," + size, params, new BeanPropertyRowMapper<>(t));
        return new PageResult<>(totalRow, pageCount, page, size, data);
    }

    public PageResult<Map<String, Object>> queryForPagination(StringBuilder sql, Map<String, Object> params, long page, long size) {
        if (size <= 0) {
            size = 10;
        }

        long totalRow = this.jdbcTemplate.queryForObject("select count(*) from (" + sql + ") as T", params, Long.class);
        long pageCount = totalRow / size;
        if (totalRow % size > 0) {
            pageCount++;
        }
        if (page <= 0 || page > pageCount) {
            page = 1;
        }

        long offset = (page - 1) * size;

        List<Map<String, Object>> data = this.jdbcTemplate
                .queryForList("select * from (" + sql.toString() + ") as T limit " + offset + "," + size, params);
        return new PageResult<>(totalRow, pageCount, page, size, data);
    }
}
