package com.houseof.code.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class User {
    private String username;
    private String userAvatar;
    private @ServerTimestamp Long userCreated;
    private String userId;

    public User() {
    }

    public User(String username, String userAvatar, Long userCreated, String userId) {
        this.username = username;
        this.userAvatar = userAvatar;
        this.userCreated = userCreated;
        this.userId = userId;
    }

    /* GETTER SETTER */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /* TO STRING */

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userCreated=" + userCreated +
                ", userId='" + userId + '\'' +
                '}';
    }
}
