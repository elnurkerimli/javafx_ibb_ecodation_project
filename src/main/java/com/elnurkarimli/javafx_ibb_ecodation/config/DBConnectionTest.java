package com.elnurkarimli.javafx_ibb_ecodation.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConnectionTest {
    public static void main(String[] args) {
        try (InputStream input = DBConnectionTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Bağlantı başarılı!");
            conn.close();
        } catch (Exception e) {
            System.err.println("❌ Hata: " + e.getMessage());
        }
    }
}