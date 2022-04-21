package com.example.tankerallocation.RetrofitService;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.Body;

public interface APIService {


    Call<JsonObject> userlogin(@Body JsonObject jsonObject);
    Call<JsonObject> ImportData(@Body JsonObject jsonObject);


}
