package com.example.tankerallocation.RetrofitService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("Login/")
    Call<JsonObject> userlogin(@Body JsonObject jsonObject);
    Call<JsonObject> ImportData(@Body JsonObject jsonObject);

    @POST("ForgotPass")
    Call<JsonObject> ForgotPassword(@Body JsonObject jsonObject);




}
