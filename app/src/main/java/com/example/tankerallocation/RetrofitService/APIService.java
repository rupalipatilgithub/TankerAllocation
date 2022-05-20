package com.example.tankerallocation.RetrofitService;

import com.example.tankerallocation.Adapter.TankerList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("API/Login")
    Call<JsonObject> userlogin(@Body JsonObject jsonObject);

    @POST("ImportData/")
    Call<JsonObject> ImportData(@Body JsonObject jsonArray);

    @POST("ExportData")
    Call<JsonObject> ExportData(@Body JsonObject jsonObject);

    @POST("API/ForgotPassword")
    Call<JsonObject> ForgotPassword(@Body JsonObject jsonObject);

    @POST("API/SuccessData")
     Call<JsonObject> SuccessData(@Body JsonObject jsonObject);

    @GET("API/TankerList")
    Call<TankerList> TankerList();

   // Call<ArrayList<RecyclerData>> getAllCourses();

    @POST("API/DriverDetails")
    Call<JsonObject> Diverdetails(@Body JsonObject jsonObject);




}
