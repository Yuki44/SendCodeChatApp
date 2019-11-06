package com.houseof.code.models;

public class Chatroom {
    private String chatName;
    private String chatDescription;
    private String chatPhoto;

    public Chatroom(String chatName, String chatDescription, String chatPhoto) {
        this.chatName = chatName;
        this.chatDescription = chatDescription;
        this.chatPhoto = chatPhoto;
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

    public String getchatPhoto() {
        return chatPhoto;
    }

    public void setchatPhoto(String chatPhoto) {
        this.chatPhoto = chatPhoto;
    }


    @Override
    public String toString() {
        return "Chatroom{" +
                "chatName='" + chatName + '\'' +
                ", chatDescription='" + chatDescription + '\'' +
                ", chatPhoto='" + chatPhoto + '\'' +
                '}';
    }
}
