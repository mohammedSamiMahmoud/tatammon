package com.example.tatmon.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.ImageDecoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.Model.Patient;
import com.example.tatmon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.zip.Inflater;

public class DoctorPatientAdapter extends RecyclerView.Adapter<DoctorPatientAdapter.DoctorPatientHolder> {

    Context context;
    List<Patient> patients;
    String lat, lng;
    MapView mapView;
    GoogleMap map;
    Dialog mapDialog;

    public DoctorPatientAdapter(Context context, List<Patient> patients) {
        this.context = context;
        this.patients = patients;
    }

    @NonNull
    @Override
    public DoctorPatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new DoctorPatientHolder(inflater
                .inflate(R.layout.doctor_patients_fragment_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorPatientHolder holder, int position) {
        final Patient patient = patients.get(position);
        holder.patientEmail.setText(patient.getP_email());
        holder.patientName.setText(patient.getP_name());

        String loc = patient.getP_location();
        int spaceIndex = loc.indexOf(" ");
        lat = loc.substring(0,spaceIndex);
        lng = loc.substring(spaceIndex);

        mapDialog = new Dialog(context);
        mapDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mapDialog.setContentView(R.layout.map_dialog);
        mapView = mapDialog.findViewById(R.id.map);

        mapView.onCreate(mapDialog.onSaveInstanceState());
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                LatLng areaName = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
                map.addMarker(new MarkerOptions()
                        .position(areaName)
                        .title(patient.getP_name()));
                map.moveCamera(CameraUpdateFactory.newLatLng(areaName));
                map.animateCamera(CameraUpdateFactory.zoomTo(10.0f));

            }
        });

        holder.patientLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapDialog.show();
                mapView.onResume();
            }
        });

    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public class DoctorPatientHolder extends RecyclerView.ViewHolder {
        EditText patientName , patientEmail;
        ImageView patientLocation;

        public DoctorPatientHolder(@NonNull View itemView) {
            super(itemView);
            patientEmail = itemView.findViewById(R.id.patientEmail);
            patientName = itemView.findViewById(R.id.patientName);
            patientLocation = itemView.findViewById(R.id.patientLocation);
        }
    }
}
