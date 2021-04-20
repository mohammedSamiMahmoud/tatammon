package com.example.tatmon.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.API.APIResponse;
import com.example.tatmon.API.DoctorRateResponse;
import com.example.tatmon.API.DownloadImage;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Model.Doctor;
import com.example.tatmon.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientHomeAdapter extends RecyclerView.Adapter<PatientHomeAdapter.PatientHomeViewHolder> {

    Context mContext;
    List<Doctor> doctors;
    float rateValue;
    public PatientHomeAdapter(Context mContext, List<Doctor> doctors) {
        this.mContext = mContext;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public PatientHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new PatientHomeViewHolder(inflater.inflate(R.layout.patient_home_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PatientHomeViewHolder holder, int position) {

        Doctor doctor = doctors.get(position);
        holder.doctorName.setText(doctor.getName());
        holder.doctorAddress.setText(doctor.getAddress());
        holder.doctorSpecialization.setText(doctor.getSpecialization());
        holder.status.setText(doctor.getState());

        RetrofitClient.getInstance().getApi()
                .getAvgRate(doctor.getId())
                .enqueue(new Callback<DoctorRateResponse>() {
                    @Override
                    public void onResponse(Call<DoctorRateResponse> call, Response<DoctorRateResponse> response) {
                        if (response.code() == 200)
                            rateValue = Float.parseFloat(response.body().getRate());
                    }

                    @Override
                    public void onFailure(Call<DoctorRateResponse> call, Throwable t) {

                    }
                });

        holder.rate.setRating(rateValue);
        new DownloadImage(holder.doctorPhoto).execute(doctor.getPhoto());

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    class PatientHomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView doctorName, doctorAddress, doctorSpecialization, status;
        ImageView doctorPhoto;
        RatingBar rate;

        public PatientHomeViewHolder(@NonNull View view) {
            super(view);
            itemView.setOnClickListener(this);
            doctorName = view.findViewById(R.id.doctorName);
            doctorAddress = view.findViewById(R.id.doctorAddress);
            doctorSpecialization = view.findViewById(R.id.doctorSpecialization);
            doctorPhoto = view.findViewById(R.id.doctorPhoto);
            status = view.findViewById(R.id.doctorStatus);
            rate = view.findViewById(R.id.doctorRate);
        }

        @Override
        public void onClick(View v) {
            if (status.equals("متاح")) {
                new AlertDialog.Builder(mContext)
                        .setMessage("هل انت متأكد من طلب الاستشارة؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault())
                                        .format(new Date());
                                String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                                        .format(new Date());
                                System.out.println("date :" + date);
                                System.out.println("time :" + time);
                                System.out.println("doctor id :" + doctors.get(getAdapterPosition()).getId());
                                System.out.println("patient id :" + SharedPrefManager.getInstance(mContext)
                                        .getUser().getId());

                                RetrofitClient
                                        .getInstance()
                                        .getApi()
                                        .addCons(doctors.get(getAdapterPosition()).getId(), SharedPrefManager.getInstance(mContext)
                                                .getUser().getId(), date, time)
                                        .enqueue(new Callback<APIResponse.DefaultResponse>() {
                                            @Override
                                            public void onResponse(Call<APIResponse.DefaultResponse> call, Response<APIResponse.DefaultResponse> response) {
                                                if (response.code() == 201) {
                                                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Log.e("Error", response.code() + " " + response.body());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<APIResponse.DefaultResponse> call, Throwable t) {
                                                Log.e("onFaliure", t.getMessage());
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("كلا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            } else {
                new AlertDialog.Builder(mContext)
                        .setMessage("هذا الطبيب غير متاح حالياً")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        }
    }


}
