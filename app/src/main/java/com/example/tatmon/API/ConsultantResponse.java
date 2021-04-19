package com.example.tatmon.API;

import com.example.tatmon.Model.Consultation;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConsultantResponse {
    @SerializedName("error")
    boolean error;
    @SerializedName("consultants")
    List<Consultation> consultations;

    public ConsultantResponse(boolean error, List<Consultation> consultations) {
        this.error = error;
        this.consultations = consultations;
    }

    public boolean isError() {
        return error;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }
}
