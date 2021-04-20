package com.example.tatmon.API;

import com.google.gson.annotations.SerializedName;

public class DoctorRateResponse {
    @SerializedName("error")
    String error;
    @SerializedName("rate")
    String rate;

    public String getError() {
        return error;
    }

    public String getRate() {
        return rate;
    }
}
