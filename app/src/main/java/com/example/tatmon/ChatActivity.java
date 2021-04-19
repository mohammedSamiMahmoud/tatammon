package com.example.tatmon;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.API.APIResponse;
import com.example.tatmon.API.ChatResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Adapter.MessageListAdapter;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        send = findViewById(R.id.button_gchat_send);
        message = findViewById(R.id.edit_gchat_message);
        name = findViewById(R.id.name);
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
}
