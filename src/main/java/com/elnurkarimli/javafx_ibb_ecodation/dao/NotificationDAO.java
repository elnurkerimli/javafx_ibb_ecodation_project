package com.elnurkarimli.javafx_ibb_ecodation.dao;

import com.elnurkarimli.javafx_ibb_ecodation.dto.Notification;
import com.elnurkarimli.javafx_ibb_ecodation.enums.NotificationType;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public void addNotification(String message, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setNotificationType(notificationType);
        notification.setTimestamp(LocalDateTime.now());
        notification.saveToFile(message);
        notifications.add(notification);

    }
    private List<Notification> notifications = new ArrayList<>();

    public List<Notification> getAllNotifications() {
        return notifications;
    }
}