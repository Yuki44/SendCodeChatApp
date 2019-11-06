package com.houseof.code.models;

public class Chatroom {
    private String chatroomTitle;
    private String chatroomDescription;
    private String chatroomPhoto;

    public Chatroom(String chatroomTitle, String chatroomDescription, String chatroomPhoto) {
        this.chatroomTitle = chatroomTitle;
        this.chatroomDescription = chatroomDescription;
        this.chatroomPhoto = chatroomPhoto;
    }

    public Chatroom() {
    }

    public String getChatroomTitle() {
        return chatroomTitle;
    }

    public void setChatroomTitle(String chatroomTitle) {
        this.chatroomTitle = chatroomTitle;
    }

    public String getChatroomDescription() {
        return chatroomDescription;
    }

    public void setChatroomDescription(String chatroomDescription) {
        this.chatroomDescription = chatroomDescription;
    }

    public String getChatroomPhoto() {
        return chatroomPhoto;
    }

    public void setChatroomPhoto(String chatroomPhoto) {
        this.chatroomPhoto = chatroomPhoto;
    }


    @Override
    public String toString() {
        return "Chatroom{" +
                "chatroomTitle='" + chatroomTitle + '\'' +
                ", chatroomDescription='" + chatroomDescription + '\'' +
                ", chatroomPhoto='" + chatroomPhoto + '\'' +
                '}';
    }
}
