package com.example.tatmon.API;

import com.example.tatmon.Model.Doctor;
import com.example.tatmon.Model.P_C;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class P_CResponse {
    @SerializedName("error")
    private boolean err;
    @SerializedName("doctors")
    private List<P_C> doctors;

    public P_CResponse(boolean err, List<P_C> doctors) {
        this.err = err;
        this.doctors = doctors;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public List<P_C> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<P_C> doctors) {
        this.doctors = doctors;
    }
}

