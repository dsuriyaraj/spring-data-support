package com.alu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alu.dao.JdbcTemplateDaoImpl;

/**
 * @author sdhanase
 * JdbcDaoSupport: using this we can eliminate multiple steps for assigning the data source.
 * compare JdbcDaoImpl and JdbcTemplateDaoImpl(this uses DAO Support class)
 *
 */
public class JdbcDemo {

    /**
     * @param args
     * @description performing DML(update) and DDL(execute) operations in db using named parameter JDBC Template
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplateDaoImpl dao = context.getBean("jdbcTemplateDaoImpl", JdbcTemplateDaoImpl.class);
        /*
         * System.out.println("List of All Circle, count: " + dao.getAllCircles().size());
         */
        // dao.createTriangleTable();
        System.out.println("Circle Count: " + dao.getCircleCount());
    }

}
