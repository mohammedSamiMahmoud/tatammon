package com.example.tatmon.API;

import com.example.tatmon.Model.Report;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportResponse {
    @SerializedName("erroe")
    String error;
    @SerializedName("reports")
    List<Report> reports;

    public String getError() {
        return error;
    }

    public List<Report> getReports() {
        return reports;
    }
}
