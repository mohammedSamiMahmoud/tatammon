package com.example.tatmon.API;

import com.example.tatmon.Model.Doctor;
import com.example.tatmon.Model.User;
import com.google.gson.annotations.SerializedName;

public class APIResponse {


    public class DefaultResponse {
        @SerializedName("error")
        boolean error;
        @SerializedName("message")
        private String message;

        public DefaultResponse(boolean error, String message) {
            this.error = error;
            this.message = message;
        }

        public boolean isError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }


    public class LogInResponse extends DefaultResponse{
        public User getUser() {
            return user;
        }

        @SerializedName("user")
        User user;

        public LogInResponse(boolean error, String message, User user) {
            super(error, message);
            this.user = user;
        }


    }

}
