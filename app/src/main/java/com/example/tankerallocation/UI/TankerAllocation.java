package com.example.tankerallocation.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tankerallocation.Adapter.PageAdapter;

import com.example.tankerallocation.R;
import com.example.tankerallocation.RetrofitService.APIService;
import com.example.tankerallocation.RetrofitService.RetrofitClient;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import org.greenrobot.greendao.database.Database;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TankerAllocation extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    PageAdapter pagepadapter;
    APIService apiService;
    RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tanker_allocation, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        setPagerAdapter();
        setTabLayout();

        return view;
    }


    private void setPagerAdapter() {

        pagepadapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        // pagepadapter = new PageAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagepadapter);
    }

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Approved");
        tabLayout.getTabAt(1).setText("Delivered");

    }
}