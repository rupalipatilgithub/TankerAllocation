package com.example.tankerallocation.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tankerallocation.Adapter.PageAdapter;
import com.example.tankerallocation.Dao.DaoMaster;
import com.example.tankerallocation.Dao.DaoSession;
import com.example.tankerallocation.Dao.UserDetails;
import com.example.tankerallocation.R;
import com.example.tankerallocation.RetrofitService.APIService;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import org.greenrobot.greendao.database.Database;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TankerAllocation extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    PageAdapter pagepadapter;
    APIService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tanker_allocation, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        setPagerAdapter();
        setTabLayout();
      //  GetData();
        return view;
    }

    private void GetData() {

        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        JsonObject postParam = new JsonObject();
        try {
            postParam.addProperty("email_id", "");
            postParam.addProperty("pass", "pass");
            postParam.addProperty("device_id", android_id);
        } finally {

        }

        Call<JsonObject> call = apiService.ImportData(postParam);
        call.enqueue(
                new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("response", "Getting response from server: " + response);

                        JSONObject movieObject = null;
                        try {
                            movieObject = new JSONObject(String.valueOf(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String userId = movieObject.getString("user_id");
                            String zone = movieObject.getString("zone_id");
                            String status = movieObject.getString("status");

                            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "users.db");
                            Database database = devOpenHelper.getWritableDb();
                            DaoSession daoSession = new DaoMaster(database).newSession();

                            UserDetails userDetails = new UserDetails();
                            userDetails.setUserid(userId);
                            userDetails.setZone(zone);
                            userDetails.setStatus(status);
                            daoSession.insert(userDetails);

                            // return true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("response", "Getting response from server: " + t);

                    }
                }
        );

    }

    private void setPagerAdapter(){
        pagepadapter = new PageAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagepadapter);
    }

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Approved");
        tabLayout.getTabAt(1).setText("Delivered");

    }
}