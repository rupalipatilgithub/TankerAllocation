package com.example.tankerallocation.RetrofitService;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private Context context;
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private static final String BASE_URL = "https://tws.elintwater.com/api/";
    //private static String BASE_URL = "https://d9e3699b-4cc0-4180-8125-c5d082db2d42.mock.pstmn.io/";

    private RetrofitClient(Context context) {
        this.context = context;
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        Response{protocol=h2, code=404, message=, url=https://d9e3699b-4cc0-4180-8125-c5d082db2d42.mock.pstmn.io/Login?mobileno=0987654321&otp=1111}
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                                    .build();

        retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(okHttpClient)
                                .build();
    }

    public static synchronized RetrofitClient getInstance(FragmentActivity context) {
        if (mInstance == null) {
            mInstance = new RetrofitClient(context);
        }
        return mInstance;
    }

    public APIService getApi() {
        return retrofit.create(APIService.class);
    }
}













