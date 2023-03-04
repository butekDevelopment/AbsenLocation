package com.example.absenlocation.Rest;

import com.example.absenlocation.Model.DataTraceModel;
import com.example.absenlocation.Model.InsertTraceModel;
import com.example.absenlocation.Model.LoginModel;
import com.example.absenlocation.Model.Profile.ProfileModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EndPoint {

    /* Login */
    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> doLogin(@Field("email") String email, @Field("password") String password);

    /* Tracing */
    @FormUrlEncoded
    @POST("insert_traces")
    Call<InsertTraceModel> insertTrace(@Header("Authorization") String Authorization, @Field("nis") String nis, @Field("date") String date,@Field("trace") ArrayList<DataTraceModel> trace);

    /* Profile */
    @GET("profile")
    Call<ProfileModel> getProfile(@Header("Authorization") String Authorization);

}
