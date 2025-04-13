package com.elnurkarimli.javafx_ibb_ecodation.utils;

import com.elnurkarimli.javafx_ibb_ecodation.dto.UserDTO;

public class SessionManager {


    public static UserDTO currentUser;

    public static void setCurrentUser(UserDTO user) {
        currentUser = user;
    }

    public static UserDTO getCurrentUser() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}