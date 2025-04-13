package com.elnurkarimli.javafx_ibb_ecodation.controller;

import com.elnurkarimli.javafx_ibb_ecodation.dao.UserDAO;
import com.elnurkarimli.javafx_ibb_ecodation.dto.UserDTO;
import com.elnurkarimli.javafx_ibb_ecodation.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Optional;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private UserDAO userDAO;

    public LoginController() {
        userDAO = new UserDAO();
    }

    @FXML
    public void initialize() {
        // Şifre ve kullanıcı adında enter tuşu ile giriş yapılabilmesi için event tanımlaması
        usernameField.setOnKeyPressed(this::specialOnEnterPressed);
        passwordField.setOnKeyPressed(this::specialOnEnterPressed);
    }

    @FXML
    private void specialOnEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleLogin(); // Enter tuşuna basıldığında giriş yapılacak
        }
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Kullanıcıyı veritabanından kontrol et
        Optional<UserDTO> optionalLoginUserDTO = userDAO.loginUser(username, password);

        if (optionalLoginUserDTO.isPresent()) {
            UserDTO userDTO = optionalLoginUserDTO.get();
            showAlert("Başarılı", "Giriş Başarılı: " + userDTO.getUsername(), Alert.AlertType.INFORMATION);
            errorLabel.setText("");  // Hata mesajını temizle

            // Oturum açan kullanıcıyı SessionManager ile kaydet
            SessionManager.setCurrentUser(userDTO);

            // Kullanıcı rolüne göre yönlendirme
            if (userDTO.getRole() == ERole.ADMIN) {
                openAdminPane(userDTO); // Admin ise admin paneline yönlendir
            } else {
                openUserHomePane(); // Kullanıcı ise ana sayfasına yönlendir
            }
        } else {
            errorLabel.setText("Giriş bilgileri hatalı!"); // Hatalı giriş durumu
        }
    }

    // Başarılı giriş sonrası kullanıcıyı admin paneline yönlendir
    private void openAdminPane(UserDTO user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.ADMIN));
            Parent parent = fxmlLoader.load();


            AdminController controller = fxmlLoader.getController();
            controller.setUser(user); // Admin kontrolcüsüne kullanıcıyı geçir

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle("Admin Panel");
            stage.show();
        } catch (Exception e) {
            System.out.println(SpecialColor.RED + "Admin Sayfasına yönlendirme başarısız" + SpecialColor.RESET);
            e.printStackTrace();
            showAlert("Hata", "Admin ekranı yüklenemedi", Alert.AlertType.ERROR);
        }
    }

    // Başarılı giriş sonrası kullanıcıyı ana sayfaya yönlendir
    private void openUserHomePane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.USER_HOME));
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle("Kullanıcı Paneli");
            stage.show();
        } catch (Exception e) {
            System.out.println(SpecialColor.RED + "Kullanıcı paneline yönlendirme başarısız" + SpecialColor.RESET);
            e.printStackTrace();
            showAlert("Hata", "Kullanıcı ekranı yüklenemedi", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void showRegisterForm() {
        try {
            // Kayıt ol sayfasına geçiş
            SceneHelper.switchScene(FXMLPath.REGISTER, usernameField, "Kayıt Ol");
        } catch (Exception e) {
            System.out.println(SpecialColor.RED + "Register Sayfasına yönlendirme başarısız" + SpecialColor.RESET);
            e.printStackTrace();
            showAlert("Hata", "Kayıt ekranı yüklenemedi", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void login(ActionEvent event) {
        handleLogin();
    }

    @FXML
    private void switchToRegister(ActionEvent event) {
        showRegisterForm();
    }
}