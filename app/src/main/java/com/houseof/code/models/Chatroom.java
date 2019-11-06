package com.houseof.code.models;

public class Chatroom {
    private String chatName;
    private String chatDescription;
    private String chatIcon;
    private String chatCreatedDate;

    public Chatroom(String chatName, String chatDescription, String chatIcon, String chatCreatedDate) {
        this.chatName = chatName;
        this.chatDescription = chatDescription;
        this.chatIcon = chatIcon;
        this.chatCreatedDate = chatCreatedDate;
    }

    public Chatroom() {
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatDescription() {
        return chatDescription;
    }

    public void setChatDescription(String chatDescription) {
        this.chatDescription = chatDescription;
    }

    public String getChatIcon() {
        return chatIcon;
    }

    public void setChatIcon(String chatIcon) {
        this.chatIcon = chatIcon;
    }

    public String getChatCreatedDate() {
        return chatCreatedDate;
    }

    public void setChatCreatedDate(String chatCreatedDate) {
        this.chatCreatedDate = chatCreatedDate;
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "chatName='" + chatName + '\'' +
                ", chatDescription='" + chatDescription + '\'' +
                ", chatIcon='" + chatIcon + '\'' +
                ", chatCreatedDate='" + chatCreatedDate + '\'' +
                '}';
    }
}
