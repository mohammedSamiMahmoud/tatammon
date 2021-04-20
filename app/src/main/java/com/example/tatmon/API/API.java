package com.example.tatmon.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

    /* @Headers("Content-Type: application/json")
     @POST("admin/create.php")
     Call<APIResponse.DefaultResponse> createAdmin(@Body RequestBody data);*/
    @FormUrlEncoded
    @POST("doctor/create")
    Call<APIResponse.DefaultResponse> singupD(
            //'name', 'email', 'password', 'phone', 'photo', 'specialization', 'state', 'work_hour','address'
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("photo") String photo,
            @Field("content") String content,
            @Field("specialization") String specialization,
            @Field("state") String state,
            @Field("work_hour") String work_hour,
            @Field("address") String address);

    @FormUrlEncoded
    @POST("doctor/logIn")
    Call<APIResponse.LogInResponse> loginD(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("article/add")
    Call<APIResponse.DefaultResponse> addArticle(
            //'doctor_id', 'link', 'name'
            @Field("doctor_id") String doctor_id,
            @Field("header") String header,
            @Field("content") String content,
            @Field("keywords") String keywords,
            @Field("imgData") String imgData
    );


    @FormUrlEncoded
    @POST("consultant/update")
    Call<APIResponse.DefaultResponse> updateConsultant(
            @Field("id") String id,
            @Field("status") int status
    );

    //patient
    @FormUrlEncoded
    @POST("patient/create")
    Call<APIResponse.DefaultResponse> singupP(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("credit_card_num") String credit_card_num,
            @Field("location") String location,
            @Field("disease") String disease,
            @Field("surgery") String surger
    );

    @FormUrlEncoded
    @POST("patient/logIn")
    Call<APIResponse.LogInResponse> loginP(@Field("email") String email,
                                             @Field("password") String pass);


    // get

    @GET("doctor/{id}/consultant/get")
    Call<ConsultantResponse> getConsultant(@Path("id") String id);

    @GET("doctor/{id}/articles")
    Call<ArticlesResponse> getDArticles(@Path("id") String id);

    @GET("doctor/search/name/{query}")
    Call<DoctorResponse> searchByName(@Path("query") String q);

    @GET("doctor/search/address/{query}")
    Call<DoctorResponse> searchByAddre(@Path("query") String q);

    @GET("doctor/search/specialization/{query}")
    Call<DoctorResponse> searchBySpec(@Path("query") String q);


    @GET("doctor/get")
    Call<DoctorResponse> getDoctors();

    @GET("doctor/get")
    Call<DoctorResponse> getArticles();

    @FormUrlEncoded
    @POST("consultant/add")
    Call<APIResponse.DefaultResponse> addCons(
            @Field("doctor_id") String doctor_id,
            @Field("patient_id") String patient_id,
            @Field("date") String date,
            @Field("time") String time
    );

    @GET("{senderId}/{reciverId}/getAllmessages")
    Call<ChatResponse> getAllMessages(@Path("senderId") String senderId, @Path("reciverId") String reciverId);

    @FormUrlEncoded
    @POST("send")
    Call<APIResponse.DefaultResponse> send(@Field("senderId") String senderId,
                                           @Field("reciverId") String reciverId,
                                           @Field("message") String message);

    @FormUrlEncoded
    @POST("report/add")
    Call<APIResponse.DefaultResponse> addReport(
            @Field("patient_id") String p_id,
            @Field("report") String report
    );

    @FormUrlEncoded
    @POST("rate/add")
    Call<APIResponse.DefaultResponse> rateAdd(
            @Field("value") String value,
            @Field("doctor_id") String d_id
    );

    @GET("doctor/{id}/rate/get")
    Call<DoctorRateResponse> getAvgRate(@Path("id") String d_id);

    @GET("patient/{id}/consultant/get")
    Call<P_CResponse> getPConsultant(@Path("id") String p_id);

    @GET("/doctor/{id}/getAllPatient")
    Call<PatientResponse> getDoctorPatient(@Path("id")String d_id);

    @GET("patient/{id}/getAllReports")
    Call<ReportResponse> getPatientReports(@Path("id") String p_id);
}
