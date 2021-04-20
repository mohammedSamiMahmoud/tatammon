package com.example.tatmon.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tatmon.API.PatientResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Adapter.DoctorPatientAdapter;
import com.example.tatmon.Model.Patient;
import com.example.tatmon.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorPatientsFragment extends Fragment {
    View mView;
    RecyclerView doctorPatientRecycler;
    List<Patient> patients;
    DoctorPatientAdapter doctorPatientAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.doctor_patients_fragment,
                container,false);
        doctorPatientRecycler = mView.findViewById(R.id.patientHomeFragmentRecycler);
        doctorPatientRecycler.setHasFixedSize(true);
        doctorPatientRecycler.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        patients = new ArrayList<>();
        RetrofitClient.getInstance().getApi()
                .getDoctorPatient(SharedPrefManager.getInstance(mView.getContext())
                .getUser().getId())
                .enqueue(new Callback<PatientResponse>() {
                    @Override
                    public void onResponse(Call<PatientResponse> call, Response<PatientResponse> response) {
                        if (response.code() == 200){
                            patients = response.body().getPatients();
                            doctorPatientAdapter = new DoctorPatientAdapter(mView.getContext(),
                            patients);
                            doctorPatientRecycler.setAdapter(doctorPatientAdapter);
                        }else {
                            Log.d("error",response.code()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<PatientResponse> call, Throwable t) {
                        Log.d("failure",t.getMessage());
                    }
                });


        return  mView;
    }
}
