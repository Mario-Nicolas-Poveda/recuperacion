package com.chathumal.smapp.util;

import com.chathumal.smapp.dto.UserDTO;

public class UserSession {
    private static UserSession instance;
    private String email;
    private UserDTO currentUser;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDTO currentUser) {
        this.currentUser = currentUser;
    }
}
