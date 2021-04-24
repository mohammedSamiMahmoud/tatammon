package com.example.tatmon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.API.DownloadImage;
import com.example.tatmon.Model.Report;
import com.example.tatmon.R;

import java.util.List;

public class PatientReportAdapter extends RecyclerView.Adapter<PatientReportAdapter.PatientReportHolder> {
    Context context;
    List<Report> reports;

    public PatientReportAdapter(Context context, List<Report> reports) {
        this.context = context;
        this.reports = reports;
    }

    @NonNull
    @Override
    public PatientReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PatientReportHolder(LayoutInflater.from(context).inflate(
                R.layout.patient_reports_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PatientReportHolder holder, int position) {
        Report report = reports.get(position);
        System.out.print("Ireport url :"+report.getReport());
        new DownloadImage(holder.reportPhoto).execute(report.getReport());
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class PatientReportHolder extends RecyclerView.ViewHolder {
        ImageView reportPhoto;

        public PatientReportHolder(@NonNull View itemView) {
            super(itemView);

            reportPhoto = itemView.findViewById(R.id.reportPhoto);
        }
    }
}
