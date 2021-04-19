package com.example.tatmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tatmon.API.APIResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Doctor.DoctorHome;
import com.example.tatmon.Doctor.DoctorSignUp;
import com.example.tatmon.Model.User;
import com.example.tatmon.Patient.PatientHome;
import com.example.tatmon.Patient.PatientSignUp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity {
    Button login;
    RadioButton patientRadio, doctorRadio;
    EditText email, password;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = findViewById(R.id.login);

        patientRadio = findViewById(R.id.patientRadio);
        doctorRadio = findViewById(R.id.doctorRadio);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patientRadio.isChecked()) {
                    RetrofitClient
                            .getInstance()
                            .getApi()
                            .loginP(email.getText().toString(), password.getText().toString())
                            .enqueue(new Callback<APIResponse.LogInResponse>() {
                                @Override
                                public void onResponse(Call<APIResponse.LogInResponse> call, Response<APIResponse.LogInResponse> response) {
                                    if (response.code() == 201) {
                                        System.out.println("user email :" +response.body().getUser().getEmail());
                                        System.out.println("user id :" +response.body().getUser().getId());

                                        User u = new User(response.body().getUser().getId()
                                        ,response.body().getUser().getEmail());
                                        SharedPrefManager.getInstance(getApplicationContext())
                                                .saveUser(u);
                                        Intent intent = new Intent(LogIn.this, PatientHome.class);
                                        startActivity(intent);
                                    } else {
                                        Log.e("message else", response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<APIResponse.LogInResponse> call, Throwable t) {
                                    Log.e("failure", t.getMessage());
                                }
                            });


                } else if (doctorRadio.isChecked()) {
                    RetrofitClient
                            .getInstance()
                            .getApi()
                            .loginD(email.getText().toString(), password.getText().toString())
                            .enqueue(new Callback<APIResponse.LogInResponse>() {
                                @Override
                                public void onResponse(Call<APIResponse.LogInResponse> call, Response<APIResponse.LogInResponse> response) {
                                    if (response.code() == 201) {
                                        Intent intent = new Intent(LogIn.this, DoctorHome.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<APIResponse.LogInResponse> call, Throwable t) {

                                }
                            });

                }
            }
        });
        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patientRadio.isChecked()) {
                    Intent intent = new Intent(LogIn.this, PatientSignUp.class);
                    startActivity(intent);

                } else if (doctorRadio.isChecked()) {
                    Intent intent = new Intent(LogIn.this, DoctorSignUp.class);
                    startActivity(intent);
                }
            }
        });
    }
}
