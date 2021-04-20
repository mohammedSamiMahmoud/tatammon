package com.example.tatmon.API;

import com.example.tatmon.Model.Consultation;
import com.example.tatmon.Model.Patient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PatientResponse {


        @SerializedName("error")
        boolean error;
        @SerializedName("patients")
        List<Patient> patients;

        public PatientResponse(boolean error, List<Patient> patients) {
            this.error = error;
            this.patients = patients;
        }

        public boolean isError() {
            return error;
        }

        public List<Patient> getPatients() {
            return patients;
        }


}
