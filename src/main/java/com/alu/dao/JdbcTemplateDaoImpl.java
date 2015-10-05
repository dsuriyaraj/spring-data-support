package com.alu.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcTemplateDaoImpl extends JdbcDaoSupport {

    public int getCircleCount() {
        String sql = "select count(*) from test_circle";
        return this.getJdbcTemplate().queryForObject(sql, Integer.class);
    }

}
