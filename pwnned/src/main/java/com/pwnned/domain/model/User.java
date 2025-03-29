package com.pwnned.domain.model;

import com.pwnned.domain.enums.UserType;

import java.util.UUID;

public class User {
    private UUID userId;
    private String email;
    private String password;
    private String username;
    private UserType userType;

    public User(UUID userId, String email, String password, String username, UserType userType) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.userType = userType;
    }

    public User() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

