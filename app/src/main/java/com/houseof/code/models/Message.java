package com.houseof.code.models;

public class Message {
    private String messageText;
    private String messageSender;
    private String timestamp;
    private String senderAvatar;

    public Message(String messageText, String messageSender, String timestamp, String senderAvatar) {
        this.messageText = messageText;
        this.messageSender = messageSender;
        this.timestamp = timestamp;
        this.senderAvatar = senderAvatar;
    }

    public Message() {
    }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageText='" + messageText + '\'' +
                ", messageSender='" + messageSender + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", senderAvatar='" + senderAvatar + '\'' +
                '}';
    }
}
