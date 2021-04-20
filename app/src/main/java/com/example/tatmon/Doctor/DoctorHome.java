package com.example.tatmon.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tatmon.LogIn;
import com.example.tatmon.R;
import com.example.tatmon.fragments.DoctorArticlesFragment;
import com.example.tatmon.fragments.DoctorConsultationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DoctorHome extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home);

        navigationView = findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);

        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ((item.getItemId())) {
                    case R.id.coun:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frag, new DoctorConsultationFragment()).commit();
                        return  true;
                        case R.id.chat:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frag, new DoctorConsultationFragment()).commit();
                        return  true;
                    case R.id.news:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frag, new DoctorArticlesFragment()).commit();
                        return  true;
                    case R.id.logout:{
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
}
