package com.example.tatmon.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.API.ArticlesResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Adapter.DoctorArticlesAdapter;
import com.example.tatmon.Doctor.AddArticle;
import com.example.tatmon.Model.Article;
import com.example.tatmon.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorArticlesFragment extends Fragment {
    View mView;
    FloatingActionButton add;
    RecyclerView recyclerView;
    DoctorArticlesAdapter adapter;
    List<Article> articles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.doctor_recycler_view_fragment, container, false);
        add = mView.findViewById(R.id.add);
        recyclerView = mView.findViewById(R.id.recyclerView);
        System.out.print("d_id : "+SharedPrefManager.getInstance(mView.getContext()).getUser().getId());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        RetrofitClient
                .getInstance()
                .getApi()
                .getDArticles()
                .enqueue(new Callback<ArticlesResponse>() {
                    @Override
                    public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                        if (response.code() == 200) {
                            articles = response.body().getArticles();
                            adapter = new DoctorArticlesAdapter(mView.getContext(), articles);
                            recyclerView.setAdapter(adapter);

                        } else {
                            Log.e("Error", response.code() + " " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                        Log.e("onFailure", t.getMessage());
                    }
                });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mView.getContext(), AddArticle.class));
                getActivity().finish();
            }
        });

        return mView;
    }
}
