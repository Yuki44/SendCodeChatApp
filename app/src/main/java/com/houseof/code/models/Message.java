package com.houseof.code.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Message {
    private String messageText;
    private String messageSender;
    private @ServerTimestamp Long messageTimestamp;
    private String senderAvatar;

    public Message() {
    }

    public Message(String messageText, String messageSender, Long messageTimestamp, String senderAvatar) {
        this.messageText = messageText;
        this.messageSender = messageSender;
        this.messageTimestamp = messageTimestamp;
        this.senderAvatar = senderAvatar;
    }

    /* GETTER SETTER */

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public Long getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(Long messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    /* TO STRING */

    @Override
    public String toString() {
        return "Message{" +
                "messageText='" + messageText + '\'' +
                ", messageSender='" + messageSender + '\'' +
                ", messageTimestamp=" + messageTimestamp +
                ", senderAvatar='" + senderAvatar + '\'' +
                '}';
    }
}
