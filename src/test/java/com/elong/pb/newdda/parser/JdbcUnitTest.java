package com.elong.pb.newdda.parser;

import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.sql.*;

/**
 * jdbc 单元测试
 * User: zhangyong
 * Date: 2016/3/27
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
public class JdbcUnitTest {

    @Test
    public void testJdbcConnect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/wiki";
        String username = "root";
        String password = "ilxw";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = null;
            rs = null;
            statement = connection.prepareStatement("select * from test where id = ?");
            statement.setInt(1, 1);
            rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("id"));
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }


}

