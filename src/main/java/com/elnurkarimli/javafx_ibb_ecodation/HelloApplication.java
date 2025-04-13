package com.elnurkarimli.javafx_ibb_ecodation;

// Eğer config.properties dosyasıyla çalışıyorsan yukarıdakini aşağıdakiyle değiştir:
// import com.elnurkarimli.javafx_ibb_ecodation.database.SingletonPropertiesDBConnection;

// Test: usertable tablosunu oluştur ve örnek veri ekle

import com.elnurkarimli.javafx_ibb_ecodation.database.SingletonPropertiesDBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException {

        // Örnek Veri
        dataSet();


        // Başlangıç ekranı: Login
        // view/admin.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/elnurkarimli/javafx_ibb_ecodation/view/login.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setTitle("Kullanıcı Yönetimi Login Sayfası");
        stage.setScene(new Scene(parent));
        stage.show();
    }


    public static void dataSet() throws SQLException {
        Connection connection = SingletonPropertiesDBConnection.getInstance().getConnection();

        // Tablo oluşturma
        try (Statement stmt = connection.createStatement()) {
            String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS usertable (
                id SERIAL PRIMARY KEY,
                username VARCHAR(50) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(100) NOT NULL UNIQUE,
                role VARCHAR(50) DEFAULT 'USER'
            );
        """;
            stmt.execute(createUserTableSQL);

            // KDV tablosu
            String createKdvTableSQL = """
    CREATE TABLE IF NOT EXISTS kdv_table (
        id SERIAL PRIMARY KEY,
        amount DOUBLE PRECISION NOT NULL,
        kdvRate DOUBLE PRECISION NOT NULL,
        kdvAmount DOUBLE PRECISION NOT NULL,
        totalAmount DOUBLE PRECISION NOT NULL,
        receiptNumber VARCHAR(100) NOT NULL,
        transactionDate DATE NOT NULL,
        description VARCHAR(255),
        exportFormat VARCHAR(50)
    );
""";
            stmt.execute(createKdvTableSQL);
        }


        // Kullanıcı ekleme
        String insertSQL = """
    INSERT INTO usertable (username, password, email, role)
    VALUES (?, ?, ?, ?)
    ON CONFLICT (username)
    DO UPDATE SET
        password = EXCLUDED.password,
        email = EXCLUDED.email,
        role = EXCLUDED.role;
""";

        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            // 1. kullanıcı
            ps.setString(1, "elnur");
            ps.setString(2, BCrypt.hashpw("elnur1", BCrypt.gensalt()));
            ps.setString(3, "elnur@gmail.com");
            ps.setString(4, "USER");
            ps.executeUpdate();

            // 2. kullanıcı
            ps.setString(1, "admin");
            ps.setString(2, BCrypt.hashpw("root", BCrypt.gensalt()));
            ps.setString(3, "hamitmizrak@gmail.com");
            ps.setString(4, "ADMIN");
            ps.executeUpdate();

            // 3. kullanıcı
            ps.setString(1, "root");
            ps.setString(2, BCrypt.hashpw("root", BCrypt.gensalt()));
            ps.setString(3, "elnurkarimli");
            ps.setString(4, "ADMIN");
            ps.executeUpdate();
        }

        System.out.println("✅ BCrypt ile şifrelenmiş ve roller atanmış kullanıcılar başarıyla eklendi.");
    }
    // Uygulama girişi
    public static void main(String[] args) {
        launch();
    }
}