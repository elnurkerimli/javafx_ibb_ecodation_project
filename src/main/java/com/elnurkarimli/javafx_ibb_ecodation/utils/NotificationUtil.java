package com.elnurkarimli.javafx_ibb_ecodation.utils;

import com.elnurkarimli.javafx_ibb_ecodation.dao.NotificationDAO;
import com.elnurkarimli.javafx_ibb_ecodation.dto.Notification;
import com.elnurkarimli.javafx_ibb_ecodation.enums.NotificationType;
import javafx.scene.control.Alert;

import java.util.List;

public class NotificationUtil {

    private static final NotificationDAO notificationDAO;

    static {
        notificationDAO = new NotificationDAO();
    }

    public static void showNotification(String message, NotificationType type) {
        // DAO'ya bildirimi ekle
        notificationDAO.addNotification(message, type);

        // Pop-up göster
        Alert alert;
        switch (type) {
            case SUCCESS -> alert = new Alert(Alert.AlertType.INFORMATION);
            case ERROR -> alert = new Alert(Alert.AlertType.ERROR);
            case WARNING -> alert = new Alert(Alert.AlertType.WARNING);
            default -> alert = new Alert(Alert.AlertType.NONE);
        }

        alert.setTitle("Bildirim");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static List<Notification> getAll() {
        return notificationDAO.getAllNotifications();
    }
}