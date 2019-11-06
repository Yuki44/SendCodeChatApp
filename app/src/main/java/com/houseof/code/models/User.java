package com.houseof.code.models;

public class User {
    private String username;
    private String userAvatar;

    public User(String username, String userAvatar) {
        this.username = username;
        this.userAvatar = userAvatar;
    }

    public User() {
    }

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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                '}';
    }
}
