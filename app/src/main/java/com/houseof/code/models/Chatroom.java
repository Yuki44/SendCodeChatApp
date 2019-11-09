package com.houseof.code.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Chatroom implements Parcelable {
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


   /* GETTER SETTER */

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

/* TO STRING */

    @Override
    public String toString() {
        return "Chatroom{" +
                "chatroomTitle='" + chatroomTitle + '\'' +
                ", chatroomDescription='" + chatroomDescription + '\'' +
                ", chatroomPhoto='" + chatroomPhoto + '\'' +
                '}';
    }

    /* PARCELABLE IMPLEMENTATION */

    protected Chatroom(Parcel in) {
        chatroomTitle = in.readString();
        chatroomDescription = in.readString();
        chatroomPhoto = in.readString();
    }

    public static final Creator<Chatroom> CREATOR = new Creator<Chatroom>() {
        @Override
        public Chatroom createFromParcel(Parcel in) {
            return new Chatroom(in);
        }

        @Override
        public Chatroom[] newArray(int size) {
            return new Chatroom[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chatroomTitle);
        dest.writeString(chatroomDescription);
        dest.writeString(chatroomPhoto);
    }
}
