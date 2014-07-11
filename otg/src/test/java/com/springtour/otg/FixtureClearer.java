/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author 001595
 */
public class FixtureClearer extends JdbcDaoSupport {

    private static final String EMPTY_STR = "";
    private static final String EQUALS = "=";
    private static final String PARAMETER = "?";
    private static final String DELETE_FROM = "delete from ";
    private static final String WHERE = " where ";
    private static final String AND = " and ";

    public void clear(String tableName, String[] where, final String[] args) {
        System.out.println(args.length);
        getJdbcTemplate().update(DELETE_FROM + tableName + assembleWhereClause(where), new PreparedStatementSetter() {
            
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                System.out.println(args.length);
                for (int i = 1; i <= args.length; i++) {
                    ps.setString(i, args[i-1]);
                }
            }
        });
    }

    private String assembleWhereClause(String[] where) {
        if (where == null || where.length == 0) {
            return EMPTY_STR;
        } else {
            StringBuilder whereClauseBuilder = new StringBuilder(WHERE);
            for (int i = 0; i < where.length; i++) {
                if (i >= 1) {
                    whereClauseBuilder.append(AND);
                }
                whereClauseBuilder.append(where[i]).append(EQUALS).append(PARAMETER);
            }
            return whereClauseBuilder.toString();
        }
    }
}
