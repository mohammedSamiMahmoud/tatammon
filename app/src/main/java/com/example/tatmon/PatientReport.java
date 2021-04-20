package com.example.tatmon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.tatmon.API.ReportResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.Adapter.PatientReportAdapter;
import com.example.tatmon.Model.Report;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientReport extends AppCompatActivity {

    List<Report> reports;
    RecyclerView patientReportRecycler;
    PatientReportAdapter patientReportAdapter;
    String p_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_report);

        p_id = new Intent().getStringExtra("p_id");
        patientReportRecycler = findViewById(R.id.patientReportRecycler);
        patientReportRecycler.setHasFixedSize(true);
        patientReportRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        reports = new ArrayList<>();

        RetrofitClient.getInstance().getApi()
                .getPatientReports(p_id)
                .enqueue(new Callback<ReportResponse>() {
                    @Override
                    public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                        if (response.code() == 200) {
                            reports = response.body().getReports();
                            patientReportAdapter = new PatientReportAdapter(getApplicationContext(),
                                    reports);
                            patientReportRecycler.setAdapter(patientReportAdapter);
                        } else {
                            Log.e("error", response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReportResponse> call, Throwable t) {
                        Log.e("failure", t.getMessage());
                    }
                });

    }
}
