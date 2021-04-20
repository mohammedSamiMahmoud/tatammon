package com.example.tatmon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.API.APIResponse;
import com.example.tatmon.API.ChatResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Adapter.MessageListAdapter;
import com.example.tatmon.Model.Chat;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter adapter;
    String userId1, userId2;
    Button send;
    EditText message;
    TextView name;
    ImageView addRate, showReport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        send = findViewById(R.id.button_gchat_send);
        message = findViewById(R.id.edit_gchat_message);
        name = findViewById(R.id.name);
        addRate = findViewById(R.id.addRate);
        showReport = findViewById(R.id.showReport);

        addRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });

        showReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, PatientReport.class);
                intent.putExtra("p_id", userId1);
                startActivity(intent);
            }
        });

        name.setText(getIntent().getStringExtra("name"));
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(message.getText().toString())) {
                    message.setError("Enter message");
                    message.requestFocus();
                    return;
                }
                RetrofitClient.getInstance()
                        .getApi()
                        .send(userId1, userId2, message.getText().toString())
                        .enqueue(new Callback<APIResponse.DefaultResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse.DefaultResponse> call, Response<APIResponse.DefaultResponse> response) {
                                if (response.code() == 201) {
                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG)
                                            .show();
                                    message.setText("");
                                    getMessages();
                                } else {
                                    Log.e("Error", "Code: " + response.code() + "\n body: " + response.errorBody());
                                }
                            }

                            @Override
                            public void onFailure(Call<APIResponse.DefaultResponse> call, Throwable t) {
                                Log.e("OnFailure", t.getMessage());
                            }
                        });
            }
        });
        userId1 = SharedPrefManager.getInstance(ChatActivity.this)
                .getUser().getId();
        userId2 = getIntent().getStringExtra("userId2");
        System.out.println("id1: " + userId1);
        System.out.println("id2: " + userId2);
        //todo get message list
        mMessageRecycler.setHasFixedSize(true);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        getMessages();

    }


    private void getMessages() {
        try {
            RetrofitClient.getInstance()
                    .getApi()
                    .getAllMessages(userId1, userId2)
                    .enqueue(new Callback<ChatResponse>() {
                        @Override
                        public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                            if (response.code() == 200) {
                                adapter = new MessageListAdapter(ChatActivity.this, response.body().getChats());
                                mMessageRecycler.setAdapter(adapter);
                            } else {
                                Log.e("Error", "Code: " + response.code() + "\n body: " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<ChatResponse> call, Throwable t) {
                            Log.e("OnFailure", t.getMessage());
                        }
                    });
        } catch (Exception e) {

        }
    }

    public void ShowDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final RatingBar rating = new RatingBar(this);

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("تقييم الطبيب!! ");
        popDialog.setMessage("هل أعجبتك الخدمة ؟؟");


        popDialog.setPositiveButton("نعم",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        RetrofitClient.getInstance().getApi()
                                .rateAdd("5", userId2)
                                .enqueue(new Callback<APIResponse.DefaultResponse>() {
                                    @Override
                                    public void onResponse(Call<APIResponse.DefaultResponse> call, Response<APIResponse.DefaultResponse> response) {
                                        if (response.code() == 201) {
                                            Toast.makeText(getApplicationContext()
                                                    , response.message(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.e("error", response.code() + "");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<APIResponse.DefaultResponse> call, Throwable t) {
                                        Log.e("failure", t.getMessage());
                                    }
                                });
                        dialog.dismiss();
                    }
                }).setNegativeButton("لا",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RetrofitClient.getInstance().getApi()
                                .rateAdd("2.5", userId2)
                                .enqueue(new Callback<APIResponse.DefaultResponse>() {
                                    @Override
                                    public void onResponse(Call<APIResponse.DefaultResponse> call, Response<APIResponse.DefaultResponse> response) {
                                        if (response.code() == 201) {
                                            Toast.makeText(getApplicationContext()
                                                    , response.message(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.e("error", response.code() + "");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<APIResponse.DefaultResponse> call, Throwable t) {
                                        Log.e("failure", t.getMessage());
                                    }
                                });
                        dialog.cancel();
                    }
                });
        popDialog.create();
        popDialog.show();
    }
}
