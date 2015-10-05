package com.alu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.alu.model.Circle;

@Component
public class JdbcDaoImpl {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // private SimpleJdbcTemplate simpleJdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource
     * @description assigning the datasource object to the template object.
     */
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * @return circle count
     */
    public int getCircleCount() {
        String sql = "select count(*) from test_circle";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * @param circleID
     * @return circle name
     */
    public String getCircleName(int circleID) {
        String sql = "select name from test_circle where id=?";
        return jdbcTemplate.queryForObject(sql, new Object[] { circleID }, String.class);
    }

    /**
     * @param circleId
     * @return Circle.
     * @description get the circle from db and creates a circle object with the help of custom rowmapper class.
     */
    public Circle getCircleforId(int circleId) {
        String sql = "select * from test_circle where id=?";
        return jdbcTemplate.queryForObject(sql, new Object[] { circleId }, new CircleMapper());
    }

    /**
     * @return List<circle>
     * @description get all the circle(result set) and with the help of circle mapper it will create a circle object to each record and return it as list object of type circle.
     */
    public List<Circle> getAllCircles() {
        String sql = "select * from test_circle";
        return jdbcTemplate.query(sql, new CircleMapper());
    }

    /**
     * @param circle
     *            .
     * @description method will insert the circle object in to db.
     */
    /*
     * public void insertCircle(Circle circle) { String sql = "insert into test_circle(id,name) values(?,?)"; jdbcTemplate.update(sql, new Object[] { circle.getId(),
     * circle.getName() }); }
     */

    /**
     * @param circle
     * @description method will insert the circle object in to db using named param JDBCTemplate.
     */
    public void insertCircle(Circle circle) {
        String sql = "insert into test_circle(id,name) values(:id,:name)";
        /*
         * since SqlParameterSource is an interface we can't create new object from that one, so we need to create it from one of the implementation class and MapSql.. is one among
         * them
         */
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", circle.getId()).addValue("name", circle.getName());
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    /**
     * @description create table in the database.
     */
    public void createTriangleTable() {
        String sql = "create table test_triangle(id integer, name varchar(50))";
        jdbcTemplate.execute(sql);
        System.out.println("Traingle table created in the database");
    }

    /**
     * @description CircleMapper class implements RowMapper Interface
     * 
     */
    private static final class CircleMapper implements RowMapper<Circle> {

        /*
         * @return circle
         * 
         * @description This Method is getting called by JdbcTemplate for every record that the result set has provided if it has corresponding row mapper mapped to it.
         */
        @Override
        public Circle mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Circle circle = new Circle();
            circle.setId(resultSet.getInt("ID"));
            circle.setName(resultSet.getString("NAME"));
            return circle;
        }

    }

}
