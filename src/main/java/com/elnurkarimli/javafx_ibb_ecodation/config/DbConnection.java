package com.elnurkarimli.javafx_ibb_ecodation.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:postgresql://localhost:1111/user_management";
    private static final String USER = "postgres";
    private static final String PASSWORD = "secret";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}