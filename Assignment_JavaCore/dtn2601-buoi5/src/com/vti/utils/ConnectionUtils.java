package com.vti.utils;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/dtn2601_buoi2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "S1a2n3g4@2006";

    public static java.sql.Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Kết nối DB thất bại", e);
        }
    }
}