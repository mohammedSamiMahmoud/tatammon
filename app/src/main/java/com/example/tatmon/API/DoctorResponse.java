package com.example.tatmon.API;

import com.example.tatmon.Model.Doctor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorResponse {
    @SerializedName("error")
    private boolean err;
    @SerializedName("doctors")
    private List<Doctor> doctors;

    public DoctorResponse(boolean err, List<Doctor> doctors) {
        this.err = err;
        this.doctors = doctors;
    }

    public boolean isErr() {
        return err;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }
}

