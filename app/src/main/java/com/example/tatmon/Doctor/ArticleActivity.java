package com.example.tatmon.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Adapter.DoctorWordAdapter;
import com.example.tatmon.Model.Article;
import com.example.tatmon.R;

import java.util.Collections;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {

    List<String> words;
    DoctorWordAdapter adapter;
    RecyclerView recyclerView;
    EditText content;
    TextView header, dName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Article a = SharedPrefManager.getInstance(ArticleActivity.this)
                .getArticle();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ArticleActivity.this));
        String[] wordS = a.getKeyWords().split(" ");
        Collections.addAll(words, wordS);
        adapter = new DoctorWordAdapter(getApplicationContext(), words);
        recyclerView.setAdapter(adapter);
        content = findViewById(R.id.content);
        content.setText(a.getContent());
        content.setEnabled(false);
        header = findViewById(R.id.header);
        header.setText(a.getHeader());
        dName = findViewById(R.id.dName);
        dName.setText(a.getDocName());


    }
}