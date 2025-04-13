package com.elnurkarimli.javafx_ibb_ecodation.dao;

import java.util.Optional;

public interface ILogin <T> {

    // Login
    Optional<T> loginUser(String username, String password);
}