package com.example.tatmon.Doctor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatmon.API.APIResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.API.SharedPrefManager;
import com.example.tatmon.Adapter.DoctorWordAdapter;
import com.example.tatmon.R;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddArticle extends AppCompatActivity {
    AlertDialog.Builder builder;
    AlertDialog dialog;
    View dialogView;
    ImageView back, articleImage;
    String word;
    EditText wordE, header, content;
    Button addWordBtn;
    String wordObject;
    List<String> words;
    DoctorWordAdapter adapter;
    RecyclerView recyclerView;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String ConvertImage;
    private int GALLERY = 1, CAMERA = 2;
    Bitmap FixBitmap;
    Context mContext;
    MaterialButton post;
    MaterialButton upLoadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_add_article);
        back = findViewById(R.id.back);
        upLoadImage = findViewById(R.id.upLoadImage);
        articleImage = findViewById(R.id.articleImage);
        upLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoFromGallary();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DoctorHome.class));
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        words = new ArrayList<>();
        mContext = getApplicationContext();
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
        post = findViewById(R.id.post);
        wordObject = "";
        header = findViewById(R.id.header);
        content = findViewById(R.id.content);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < words.size() - 1; i++)
                    wordObject += words.get(i) + " ";
                wordObject += words.get(words.size() - 1);
                UploadImageToServer();
                RetrofitClient.getInstance()
                        .getApi()
                        .addArticle(
                                SharedPrefManager.getInstance(AddArticle.this).getUser().getId(),
                                header.getText().toString(),
                                content.getText().toString(),
                                wordObject,
                                ConvertImage
                        )
                        .enqueue(new Callback<APIResponse.DefaultResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse.DefaultResponse> call, Response<APIResponse.DefaultResponse> response) {
                                if (response.code() == 201) {
                                    Toast.makeText(AddArticle.this, "تم إضافة المقالة بنجاح!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("error", response.code() + " : " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<APIResponse.DefaultResponse> call, Throwable t) {
                                Log.e("on Failure : ", t.fillInStackTrace() + " : " + t.getMessage());
                            }
                        });
            }
        });
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                System.out.println("Path: " + contentURI.getEncodedPath());
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                    FixBitmap = Bitmap.createScaledBitmap(FixBitmap, (int) (FixBitmap.getWidth() * 0.5),
                            (int) (FixBitmap.getHeight() * 0.5), true);
                    Toast.makeText(mContext, "Image Saved!", Toast.LENGTH_SHORT).show();
                    articleImage.setImageBitmap(FixBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void UploadImageToServer() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

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
