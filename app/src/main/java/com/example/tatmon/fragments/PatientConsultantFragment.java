package com.example.tatmon.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.tatmon.API.P_CResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Adapter.DoctorConsultationAdapter;
import com.example.tatmon.Adapter.PatientHomeAdapter;
import com.example.tatmon.Adapter.UserAdapter;
import com.example.tatmon.Model.Doctor;
import com.example.tatmon.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientConsultantFragment extends Fragment {

    View mView;
    FloatingActionButton searchBtn;
    EditText search;
    RecyclerView recyclerView;
    RadioButton rad1, rad2, rad3;
    DoctorConsultationAdapter doctorConsultationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.patient_home_fragment, container, false);
        searchBtn = mView.findViewById(R.id.searchBtn);
        search = mView.findViewById(R.id.search);

        recyclerView = mView.findViewById(R.id.patientHomeFragmentRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        RetrofitClient.getInstance()
                .getApi()
                .getPConsultant(SharedPrefManager.getInstance(mView.getContext()).getUser().getId())
                .enqueue(new Callback<P_CResponse>() {
                    @Override
                    public void onResponse(Call<P_CResponse> call, Response<P_CResponse> response) {
                        if (response.code() == 200) {
                            recyclerView.setAdapter(new UserAdapter(mView.getContext(), response.body().getDoctors()));
                        } else {
                            Log.e("Error: ", response.code() + " " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<P_CResponse> call, Throwable t) {

                        Log.e("onFailure", t.getMessage());
                    }
                });
        rad1 = mView.findViewById(R.id.rad1);
        rad2 = mView.findViewById(R.id.rad2);
        rad3 = mView.findViewById(R.id.rad3);
        search.setVisibility(View.GONE);
        searchBtn.setVisibility(View.GONE);
        rad1.setVisibility(View.GONE);
        rad2.setVisibility(View.GONE);
        rad3.setVisibility(View.GONE);

        return mView;
    }

}
