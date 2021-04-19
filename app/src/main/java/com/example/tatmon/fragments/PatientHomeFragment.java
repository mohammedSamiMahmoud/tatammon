package com.example.tatmon.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.tatmon.API.DoctorResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.Adapter.PatientHomeAdapter;
import com.example.tatmon.Model.Doctor;
import com.example.tatmon.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientHomeFragment extends Fragment {
    View mView;
    FloatingActionButton searchBtn;
    EditText search;
    RecyclerView recyclerView;
    PatientHomeAdapter patientHomeAdapter;
    List<Doctor> doctors;
    RadioButton rad1, rad2, rad3;
    boolean flag1, flag2, flag3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.patient_home_fragment, container, false);
        searchBtn = mView.findViewById(R.id.searchBtn);
        search = mView.findViewById(R.id.search);
        recyclerView = mView.findViewById(R.id.patientHomeFragmentRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        rad1 = mView.findViewById(R.id.rad1);
        rad2 = mView.findViewById(R.id.rad2);
        rad3 = mView.findViewById(R.id.rad3);
        rad1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flag1 = true;
                    flag2 = false;
                    flag3 = false;
                }
            }
        });
        rad2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flag1 = false;
                    flag2 = true;
                    flag3 = false;
                }
            }
        });
        rad3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flag1 = false;
                    flag2 = false;
                    flag3 = true;
                }
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag1)
                    RetrofitClient
                            .getInstance()
                            .getApi()
                            .searchByName(search.getText().toString())
                            .enqueue(new Callback<DoctorResponse>() {
                                @Override
                                public void onResponse(Call<DoctorResponse> call, Response<DoctorResponse> response) {
                                    if (response.code() == 200) {
                                        recyclerView.setAdapter(new PatientHomeAdapter(mView.getContext(), response.body().getDoctors()));
                                    } else {
                                        Log.e("Error: ", response.code() + " " + response.body());
                                    }
                                }

                                @Override
                                public void onFailure(Call<DoctorResponse> call, Throwable t) {
                                    Log.e("onFailure", t.getMessage());
                                }
                            });
                else if (flag2)
                    RetrofitClient
                            .getInstance()
                            .getApi()
                            .searchBySpec(search.getText().toString())
                            .enqueue(new Callback<DoctorResponse>() {
                                @Override
                                public void onResponse(Call<DoctorResponse> call, Response<DoctorResponse> response) {
                                    if (response.code() == 200) {
                                        recyclerView.setAdapter(new PatientHomeAdapter(mView.getContext(), response.body().getDoctors()));
                                    } else {
                                        Log.e("Error: ", response.code() + " " + response.body());
                                    }
                                }

                                @Override
                                public void onFailure(Call<DoctorResponse> call, Throwable t) {
                                    Log.e("onFailure", t.getMessage());
                                }
                            });
                else if (flag3)
                    RetrofitClient
                            .getInstance()
                            .getApi()
                            .searchByAddre(search.getText().toString())
                            .enqueue(new Callback<DoctorResponse>() {
                                @Override
                                public void onResponse(Call<DoctorResponse> call, Response<DoctorResponse> response) {
                                    if (response.code() == 200) {
                                        recyclerView.setAdapter(new PatientHomeAdapter(mView.getContext(), response.body().getDoctors()));
                                    } else {
                                        Log.e("Error: ", response.code() + " " + response.body());
                                    }
                                }

                                @Override
                                public void onFailure(Call<DoctorResponse> call, Throwable t) {
                                    Log.e("onFailure", t.getMessage());
                                }
                            });
            }
        });
        RetrofitClient.getInstance()
                .getApi()
                .getDoctors()
                .enqueue(new Callback<DoctorResponse>() {
                    @Override
                    public void onResponse(Call<DoctorResponse> call, Response<DoctorResponse> response) {
                        if (response.code() == 200) {
                            recyclerView.setAdapter(new PatientHomeAdapter(mView.getContext(), response.body().getDoctors()));
                        } else {
                            Log.e("Error: ", response.code() + " " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<DoctorResponse> call, Throwable t) {
                        Log.e("onFailure", t.getMessage());
                    }
                });

        return mView;
    }
}
