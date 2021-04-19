package com.example.tatmon.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tatmon.LogIn;
import com.example.tatmon.R;
import com.example.tatmon.fragments.PatientConsultantFragment;
import com.example.tatmon.fragments.PatientHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PatientHome extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.patientFrame
                                , new PatientHomeFragment()).commit();
                        return true;
                    }
                    case R.id.consultant: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.patientFrame
                                , new PatientConsultantFragment()).commit();
                        return true;
                    }
                    case R.id.logout:{
                        Intent intent = new Intent(PatientHome.this, LogIn.class);
                        startActivity(intent);
                        return true;
                    }

                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}
