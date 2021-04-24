package com.example.tatmon.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText name, email, password, phone, cridetCardNum, surgeryName;
    RadioButton yesDisease, noDisease, yesSurgery, noSurgery;
    ImageButton location;
    Spinner statusSpinner;
    String status, sss;
    String[] s = {"قلب", "ضغط", "سكري", "امراض تنفيسة", "اخرى"};
    Button pcreate;

    private static final int PICK_Loc = 1;

    MapView mapView;
    GoogleMap map;
    Dialog mapDialog;
    Double lat, lan;


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


        final Dialog mapDialog = new Dialog(PatientSignUp.this);
        mapDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mapDialog.setContentView(R.layout.map_dialog);
        mapView = mapDialog.findViewById(R.id.map);


        mapView.onCreate(mapDialog.onSaveInstanceState());
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                lat = 21.54238;
                lan = 39.19797;
                LatLng jeddah = new LatLng(lat, lan);
                map.addMarker(new MarkerOptions()
                        .position(jeddah)
                        .title("Jeddah"));
                map.moveCamera(CameraUpdateFactory.newLatLng(jeddah));
                map.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
                map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        map.clear();
                        lat = latLng.latitude;
                        lan = latLng.longitude;
                        System.out.print(lat + " ******  " + lan);
                        map.addMarker(new MarkerOptions()
                                .position(latLng)
                        );
                        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                });
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapDialog.show();
                mapView.onResume();
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
                            + lat + " " + lan + "\n"
                            + status + "\n"
                            + ss);
                RetrofitClient.getInstance().getApi()
                        .singupP(name.getText().toString()
                                , email.getText().toString()
                                , password.getText().toString()
                                , phone.getText().toString()
                                , cridetCardNum.getText().toString()
                                , lat + " " + lan,
                                status,
                                ss)
                        .enqueue(new Callback<APIResponse.DefaultResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse.DefaultResponse> call, Response<APIResponse.DefaultResponse> response) {
                                if (response.code() == 201) {
                                    Toast.makeText(getApplicationContext(),"تم انشاء الحساب", Toast.LENGTH_SHORT)
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
