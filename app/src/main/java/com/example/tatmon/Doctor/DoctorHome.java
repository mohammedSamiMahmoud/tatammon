package com.example.tatmon.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tatmon.API.APIResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.LogIn;
import com.example.tatmon.R;
import com.example.tatmon.fragments.DoctorArticlesFragment;
import com.example.tatmon.fragments.DoctorConsultationFragment;
import com.example.tatmon.fragments.DoctorPatientsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorHome extends AppCompatActivity {

    BottomNavigationView navigationView;
    RadioButton on, off, hasPatient;
    String d_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home);

        navigationView = findViewById(R.id.navigation);

        on = findViewById(R.id.on);
        off = findViewById(R.id.off);
        hasPatient = findViewById(R.id.hasPatient);


        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (on.isChecked()) {
                    d_status = "متاح";
                    updateStatus();
                }
            }
        });
        hasPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPatient.isChecked()) {
                    d_status = "يوجد لديه مريض";
                    updateStatus();
                }
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (off.isChecked()) {
                    d_status = "غير متاح";
                    updateStatus();
                }
            }
        });


        navigationView.setItemIconTintList(null);

        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch ((item.getItemId())) {
                            case R.id.coun:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.main_frag, new DoctorConsultationFragment()).commit();
                                return true;
                            case R.id.chat:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.main_frag, new DoctorPatientsFragment()).commit();
                                return true;
                            case R.id.news:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.main_frag, new DoctorArticlesFragment()).commit();
                                return true;
                            case R.id.logout: {
                                Intent intent = new Intent(DoctorHome.this, LogIn.class);
                                startActivity(intent);
                                return true;
                            }
                        }
                        return false;
                    }
                });
        navigationView.setSelectedItemId(R.id.news);
    }

    public void updateStatus() {
        RetrofitClient.getInstance().getApi()
                .changeStatus(SharedPrefManager.getInstance(DoctorHome.this)
                        .getUser().getId(), d_status)
                .enqueue(new Callback<APIResponse.DefaultResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse.DefaultResponse> call, Response<APIResponse.DefaultResponse> response) {
                        if (response.code() == 201)
                            Toast.makeText(getApplicationContext()
                                    , "تم تغيير الحالة", Toast.LENGTH_SHORT).show();
                        else
                            Log.e("reeor", response.code() + " : " + response.message());
                    }

                    @Override
                    public void onFailure(Call<APIResponse.DefaultResponse> call, Throwable t) {
                        Log.e("failure ", t.getMessage());
                    }
                });
    }
}
