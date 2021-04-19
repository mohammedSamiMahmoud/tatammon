package com.example.tatmon.API;

import com.example.tatmon.Model.Chat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatResponse {

    @SerializedName("error")
    private boolean err;
    @SerializedName("chat")
    private List<Chat> chats;

    public ChatResponse(boolean err, List<Chat> chats) {
        this.err = err;
        this.chats = chats;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
