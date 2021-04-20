package com.example.tatmon.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tatmon.API.APIResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.Doctor.DoctorSignUp;
import com.example.tatmon.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LocationListener {
    EditText name, email, password, phone, cridetCardNum, surgeryName;
    RadioButton yesDisease, noDisease, yesSurgery, noSurgery;
    ImageButton location;
    Spinner statusSpinner;
    String status, sss;
    String[] s = {"قلب", "ضغط", "سكري", "امراض تنفيسة", "اخرى"};
    Button pcreate;
    double locationLat, locationLng;
    private static final int PICK_Loc = 1;

    @Override
    public void onLocationChanged(Location location) {

        locationLat = location.getLatitude();
        locationLng = location.getLongitude();
        System.out.println("Lat: " + locationLat + "\n" + "lan: " + locationLng);
    }

    public void getLocation() {

        try {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, PICK_Loc);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);

        } catch (SecurityException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_sign_up);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Patient SignUp");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        location = findViewById(R.id.location);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        cridetCardNum = findViewById(R.id.creditCardNum);
        surgeryName = findViewById(R.id.surgeryName);
        pcreate = findViewById(R.id.pcreate);

        yesDisease = findViewById(R.id.yesDisease);
        noDisease = findViewById(R.id.noDisease);
        yesSurgery = findViewById(R.id.yesSurgery);
        noSurgery = findViewById(R.id.noSurgery);

        statusSpinner = findViewById(R.id.statusSpinner);
        statusSpinner.setOnItemSelectedListener(PatientSignUp.this);
        ArrayAdapter ad = new ArrayAdapter(this
                , R.layout.support_simple_spinner_dropdown_item
                , s);
        statusSpinner.setAdapter(ad);
        statusSpinner.setOnItemSelectedListener(new PatientOnItemSelectListener());

        yesDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesDisease.isChecked()) {
                    statusSpinner.setVisibility(View.VISIBLE);
                }
            }
        });
        noDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noDisease.isChecked()) {
                    statusSpinner.setVisibility(View.GONE);
                }
            }
        });

        yesSurgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesSurgery.isChecked()) {
                    surgeryName.setVisibility(View.VISIBLE);
                }
            }
        });
        noSurgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noSurgery.isChecked()) {
                    surgeryName.setVisibility(View.GONE);
                    sss = "لا توجد عمليات سابقة!";
                }
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        //  getLocation();

        pcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ss = surgeryName.getText().toString() == null ||
                        surgeryName.getText().toString() == "" ? "لا توجد عمليات جراحية" : surgeryName.getText().toString();
                if (DataValidation())
                    System.out.println(name.getText().toString() + "\n"
                            + email.getText().toString() + "\n"
                            + password.getText().toString() + "\n"
                            + phone.getText().toString() + "\n"
                            + cridetCardNum.getText().toString() + "\n"
                            + locationLat + " " + locationLng + "\n"
                            + status + "\n"
                            + ss);
                RetrofitClient.getInstance().getApi()
                        .singupP(name.getText().toString()
                                , email.getText().toString()
                                , password.getText().toString()
                                , phone.getText().toString()
                                , cridetCardNum.getText().toString()
                                , locationLat + " " + locationLng,
                                status,
                                ss)
                        .enqueue(new Callback<APIResponse.DefaultResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse.DefaultResponse> call, Response<APIResponse.DefaultResponse> response) {
                                if (response.code() == 201) {
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT)
                                            .show();
                                    finish();
                                } else {
                                    Log.e("Error", response.code() + " " + response.body());
                                }
                            }

                            @Override
                            public void onFailure(Call<APIResponse.DefaultResponse> call, Throwable t) {
                                String s = t.getLocalizedMessage();
                                Log.e("onFailure", s);
                            }
                        });
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class PatientOnItemSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            status = statusSpinner.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private boolean DataValidation() {
        System.out.println("Validate Data:");
        if (TextUtils.isEmpty(name.getText().toString())) {
            name.setError("Name is required!");
            name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Email is required!");
            email.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("password is required!");
            password.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            phone.setError("phone number is required!");
            phone.requestFocus();
            return false;
        }

        /*if (TextUtils.isEmpty(surgeryName.getText().toString())) {
            surgeryName.setError("surgeryName is required!");
            surgeryName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(workHour.getText().toString())) {
            workHour.setError("work Hour is required!");
            workHour.requestFocus();
            return false;
        }

        if (volunteer.isChecked()) {
            type = "1";
        } else if (supporter.isChecked()) {
            type = "-1";
        }
        else {
            rGroup.requestFocus();
        }*/

        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Enter a valid email!");
            email.requestFocus();
            return false;
        }
        return true;
    }
}
