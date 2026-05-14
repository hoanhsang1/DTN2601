package org.example.utils;

import java.sql.*;

public class JDBCUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/dtn2601_buoi2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "S1a2n3g4@2006";

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Kết nối DB thất bại", e);
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}