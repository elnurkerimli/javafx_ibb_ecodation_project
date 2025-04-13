package com.elnurkarimli.javafx_ibb_ecodation.database;

import org.h2.tools.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class SingletonPropertiesDBConnection {

    // Veritabanı bilgileri
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    private static SingletonPropertiesDBConnection instance;
    private Connection connection;

    // Constructor
    private SingletonPropertiesDBConnection() {
        try {
            loadDatabaseConfig(); // config.properties dosyasını oku
            Class.forName("org.h2.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✅ Veritabanı bağlantısı başarılı.");

            // H2DB
            H2DbStarting();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Veritabanı bağlantısı başarısız!");
        }
    }

    // H2DB
    // H2 Web Konsolunu başlatmak için
    private void H2DbStarting() {
        try {
            Server server = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8080").start();
            System.out.println("H2 Web Console is running at: http://localhost:8080");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // config.properties dosyasını oku
    private static void loadDatabaseConfig() {
        String configFile = "src/main/resources/config.properties";
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);

            URL = properties.getProperty("db.url", "jdbc:postgresql://localhost:1111/user_management");
            USERNAME = properties.getProperty("db.username", "postgres");
            PASSWORD = properties.getProperty("db.password", "secret");

            System.out.println("✅ Veritabanı yapılandırması başarıyla yüklendi.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("❌ config.properties dosyası bulunamadı: " + configFile, e);
        } catch (IOException e) {
            throw new RuntimeException("❌ Veritabanı yapılandırması okunurken hata oluştu!", e);
        }
    }

    // Singleton instance
    public static synchronized SingletonPropertiesDBConnection getInstance() {
        if (instance == null) {
            instance = new SingletonPropertiesDBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    // Bağlantıyı kapat
    public static void closeConnection() {
        if (instance != null && instance.connection != null) {
            try {
                instance.connection.close();
                System.out.println("🔒 Veritabanı bağlantısı kapatıldı.");
            } catch (SQLException e) {
                throw new RuntimeException("❌ Bağlantı kapatılırken hata oluştu!", e);
            }
        }
    }

    // main test
    public static void main(String[] args) throws SQLException {
        //dataSet();
    }
}