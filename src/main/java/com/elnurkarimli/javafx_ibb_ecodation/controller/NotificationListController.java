package com.elnurkarimli.javafx_ibb_ecodation.controller;

import com.elnurkarimli.javafx_ibb_ecodation.dto.UserDTO;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;  // ✅ Doğru olan bu

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NotificationListController {

    private UserDTO user;

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @FXML
    private TextArea notificationArea;

    public void setNotificationArea(TextArea notificationArea) {
        this.notificationArea = notificationArea;
    }

    private final String FILE_PATH = "bildirimler.txt";

    @FXML
    public void initialize() {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }
            notificationArea.setText(builder.toString());
        } catch (IOException e) {
            notificationArea.setText("Dosya okunamadı.");
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) notificationArea.getScene().getWindow();
        stage.close();
    }
}