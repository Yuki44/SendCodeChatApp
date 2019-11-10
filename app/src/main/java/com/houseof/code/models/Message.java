package com.houseof.code.models;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
public class Message {
    private String messageText;
    private String messageSender;
    private String messageUsername;
    private @ServerTimestamp Date messageTimestamp;
    private String senderAvatar;
    public String messageId;

    public Message() {
    }

    public Message(String messageText, String messageSender, String messageUsername, Date messageTimestamp, String senderAvatar, String messageId) {
        this.messageText = messageText;
        this.messageSender = messageSender;
        this.messageUsername = messageUsername;
        this.messageTimestamp = messageTimestamp;
        this.senderAvatar = senderAvatar;
        this.messageId = messageId;
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

    public String getMessageUsername() {
        return messageUsername;
    }

    public void setMessageUsername(String messageUsername) {
        this.messageUsername = messageUsername;
    }

    public Date getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(Date messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    /* TO STRING */

    @Override
    public String toString() {
        return "Message{" +
                "messageText='" + messageText + '\'' +
                ", messageSender='" + messageSender + '\'' +
                ", messageUsername='" + messageUsername + '\'' +
                ", messageTimestamp=" + messageTimestamp +
                ", senderAvatar='" + senderAvatar + '\'' +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
