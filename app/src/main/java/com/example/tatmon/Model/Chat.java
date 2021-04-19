package com.example.tatmon.Model;

public class Chat {

    private String senderId, reciverId, message;

    public Chat(String senderId, String reciverId, String message) {
        this.senderId = senderId;
        this.reciverId = reciverId;
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReciverId() {
        return reciverId;
    }

    public void setReciverId(String reciverId) {
        this.reciverId = reciverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
