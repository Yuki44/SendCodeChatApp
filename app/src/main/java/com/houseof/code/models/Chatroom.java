package com.houseof.code.models;


import android.os.Parcel;
import android.os.Parcelable;


public class Chatroom implements Parcelable {
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
    private String chatroomTitle;
    private String chatroomDescription;
    private String chatroomPhoto;
    private String chatroomId;

    public Chatroom(String chatroomTitle, String chatroomDescription, String chatroomPhoto, String chatroomId) {
        this.chatroomTitle = chatroomTitle;
        this.chatroomDescription = chatroomDescription;
        this.chatroomPhoto = chatroomPhoto;
        this.chatroomId = chatroomId;
    }

    public Chatroom() {
    }

    public Chatroom(Parcel in) {
        chatroomTitle = in.readString();
        chatroomDescription = in.readString();
        chatroomPhoto = in.readString();
        chatroomId = in.readString();
    }

    public String getChatroomTitle() {
        return chatroomTitle;
    }

    public String getChatroomDescription() {
        return chatroomDescription;
    }

    public String getChatroomPhoto() {
        return chatroomPhoto;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    /* TO STRING */

    @Override
    public String toString() {
        return "Chatroom{" +
                "chatroomTitle='" + chatroomTitle + '\'' +
                ", chatroomDescription='" + chatroomDescription + '\'' +
                ", chatroomPhoto='" + chatroomPhoto + '\'' +
                ", chatroomId='" + chatroomId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chatroomTitle);
        dest.writeString(chatroomDescription);
        dest.writeString(chatroomPhoto);
        dest.writeString(chatroomId);
    }


}
