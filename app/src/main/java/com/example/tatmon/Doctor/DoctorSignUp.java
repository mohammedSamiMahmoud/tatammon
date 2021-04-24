package com.example.tatmon.Doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatmon.API.APIResponse;
import com.example.tatmon.API.RetrofitClient;
import com.example.tatmon.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView doctorImage;
    EditText name, email, password, phone, specialization, workHour, address;
    Spinner statusSpinner;
    String status;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String ConvertImage;
    private int GALLERY = 1, CAMERA = 2;
    Bitmap FixBitmap;
    Context mContext;
    String[] s = {"متاح", "غير متاح", "يوجد لديه مريض"};
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_sign_up);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Doctor SignUp");
        mContext = getApplicationContext();
        doctorImage = findViewById(R.id.doctorImage);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        specialization = findViewById(R.id.specialization);
        workHour = findViewById(R.id.workHour);
        address = findViewById(R.id.address);
        create = findViewById(R.id.create);
        doctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoFromGallary();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImageToServer();
                RetrofitClient.getInstance().getApi()
                        .singupD(name.getText().toString()
                                , email.getText().toString()
                                , password.getText().toString()
                                , phone.getText().toString()
                                , name.getText().toString() + "_"
                                        + email.getText().toString(),
                                ConvertImage
                                , specialization.getText().toString()
                                , status, workHour.getText().toString()
                                , address.getText().toString())
                        .enqueue(new Callback<APIResponse.DefaultResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse.DefaultResponse> call, Response<APIResponse.DefaultResponse> response) {
                                if (response.code() == 201) {
                                    Toast.makeText(getApplicationContext()
                                            ,"تم انشاء الحساب", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Log.e("message", response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<APIResponse.DefaultResponse> call, Throwable t) {
                                Log.e("failure", t.getMessage());
                            }
                        });
            }
        });


        statusSpinner = findViewById(R.id.statusSpinner);
        statusSpinner.setOnItemSelectedListener(DoctorSignUp.this);
        ArrayAdapter ad = new ArrayAdapter(this
                , R.layout.support_simple_spinner_dropdown_item
                , s);
        statusSpinner.setAdapter(ad);
        statusSpinner.setOnItemSelectedListener(new DoctorOnItemSelectListener());

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
                    FixBitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), contentURI);
                    FixBitmap = Bitmap.createScaledBitmap(FixBitmap, (int) (FixBitmap.getWidth() * 0.5),
                            (int) (FixBitmap.getHeight() * 0.5), true);
                    Toast.makeText(mContext, "Image Saved!", Toast.LENGTH_SHORT).show();
                    doctorImage.setImageBitmap(FixBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            FixBitmap = Bitmap.createScaledBitmap(FixBitmap, (int) (FixBitmap.getWidth() * 0.5),
                    (int) (FixBitmap.getHeight() * 0.5), true);
            doctorImage.setImageBitmap(FixBitmap);
        }
    }

    public void UploadImageToServer() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

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

    class DoctorOnItemSelectListener implements AdapterView.OnItemSelectedListener {

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

        if (TextUtils.isEmpty(specialization.getText().toString())) {
            specialization.setError("specialization is required!");
            specialization.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(workHour.getText().toString())) {
            workHour.setError("work Hour is required!");
            workHour.requestFocus();
            return false;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Enter a valid email!");
            email.requestFocus();
            return false;
        }
        return true;
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }
}
