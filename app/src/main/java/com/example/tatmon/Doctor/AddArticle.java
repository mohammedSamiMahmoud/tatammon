package com.example.tatmon.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.Adapter.DoctorWordAdapter;
import com.example.tatmon.R;

import java.util.ArrayList;
import java.util.List;

public class AddArticle extends AppCompatActivity {
    AlertDialog.Builder builder;
    AlertDialog dialog;
    View dialogView;
    ImageView back;
    String word;
    EditText wordE;
    Button addWordBtn;
    String wordObject;
    List<String> words;
    DoctorWordAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_add_article);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DoctorHome.class));
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        words = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        builder = new AlertDialog.Builder(this);
        builder.setTitle("اضافة كلمة مفتاحية");
        dialogView = getLayoutInflater().inflate(R.layout.doctor_word_dialog_box, null, false);
        builder.setView(dialogView);
        wordE = dialogView.findViewById(R.id.tName);
        addWordBtn = dialogView.findViewById(R.id.tAdd);
        addWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word = wordE.getText().toString();
                dialog.dismiss();
                words.add(word);
                adapter = new DoctorWordAdapter(getApplicationContext(), words);
                recyclerView.setAdapter(adapter);
            }
        });

        dialog = builder.create();
    }

    public void addNemWord(View view) {
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), DoctorHome.class));
        finish();
    }
}
