package com.example.tatmon.Model;

public class Report {
    String id, patient_id, report;

    public Report(String id, String patient_id, String report) {
        this.id = id;
        this.patient_id = patient_id;
        this.report = report;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
