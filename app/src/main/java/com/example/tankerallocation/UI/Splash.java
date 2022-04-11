package com.example.tankerallocation.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tankerallocation.R;


public class Splash extends Fragment {

    private final int SPLASH_TIME_OUT = 1500;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        NavHost navHost = (NavHost) getActivity().getSupportFragmentManager().findFragmentById(R.id.splash);
        NavController navController = navHost.getNavController();
        new Handler().postDelayed(() -> navController.navigate(R.id.action_splash_to_login), SPLASH_TIME_OUT);
        return view;
    }
}