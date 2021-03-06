package com.example.tatmon.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.API.APIResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.Model.Consultation;
import com.example.tatmon.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorConsultationAdapter extends RecyclerView.Adapter<DoctorConsultationAdapter.ConsultationViewHolder> {

    Context mContext;
    List<Consultation> consultations;

    public DoctorConsultationAdapter(Context mContext, List<Consultation> consultations) {
        this.mContext = mContext;
        this.consultations = consultations;
    }

    @NonNull
    @Override
    public ConsultationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ConsultationViewHolder(inflater.inflate(R.layout.doctor_consultation_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultationViewHolder holder, int position) {
        Consultation consultation = consultations.get(position);
        holder.header.setText(consultation.getDate());
        holder.name.setText(consultation.getPatient_name());
        holder.cStatus.setText(consultation.getStatus());
    }

    @Override
    public int getItemCount() {
        return consultations.size();
    }

    class ConsultationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, header,cStatus;

        public ConsultationViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.name);
            header = itemView.findViewById(R.id.header);
            cStatus = itemView.findViewById(R.id.cStatus);
        }

        @Override
        public void onClick(View v) {
            if (!cStatus.getText().toString().equals("1"))
            new AlertDialog.Builder(mContext)
                    .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RetrofitClient.getInstance()
                                    .getApi()
                                    .updateConsultant(consultations.get(getAdapterPosition()).getId(), 1)
                                    .enqueue(new Callback<APIResponse.DefaultResponse>() {
                                        @Override
                                        public void onResponse(Call<APIResponse.DefaultResponse> call, Response<APIResponse.DefaultResponse> response) {
                                            if (response.code() == 201) {
                                                Toast.makeText(mContext, "???? ???????? ??????????????????", Toast.LENGTH_SHORT)
                                                        .show();
                                                cStatus.setTextColor(Color.GREEN);
                                            } else {
                                                Log.e("Error", response.code() + " " + response.body());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<APIResponse.DefaultResponse> call, Throwable t) {
                                            Log.e("onFailure", t.getMessage());
                                        }
                                    });
                        }
                    })
                    .setMessage("???????? ???? ?????? ????????????????????")
                    .create()
                    .show();
        }
    }
}
