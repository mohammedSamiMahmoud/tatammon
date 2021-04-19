package com.example.tatmon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.API.ConsultantResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Adapter.DoctorConsultationAdapter;
import com.example.tatmon.Model.Consultation;
import com.example.tatmon.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorConsultationFragment extends Fragment {

    View mView;
    FloatingActionButton add;
    RecyclerView recyclerView;
    List<Consultation> consultations;
    DoctorConsultationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.doctor_recycler_view_fragment, container, false);
        add = mView.findViewById(R.id.add);
        recyclerView = mView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        consultations = new ArrayList<>();
        RetrofitClient.getInstance()
                .getApi()
                .getConsultant(SharedPrefManager.getInstance(mView.getContext())
                        .getUser().getId())
                .enqueue(new Callback<ConsultantResponse>() {
                    @Override
                    public void onResponse(Call<ConsultantResponse> call, Response<ConsultantResponse> response) {
                        if (response.code() == 200) {
                            consultations = response.body().getConsultations();
                            adapter = new DoctorConsultationAdapter(mView.getContext(), consultations);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ConsultantResponse> call, Throwable t) {

                    }
                });
        add.setVisibility(View.GONE);
        return mView;
    }
}
