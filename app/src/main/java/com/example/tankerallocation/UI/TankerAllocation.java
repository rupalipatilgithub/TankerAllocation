package com.example.tankerallocation.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tankerallocation.Adapter.PageAdapter;
import com.example.tankerallocation.R;
import com.google.android.material.tabs.TabLayout;


public class TankerAllocation extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    PageAdapter pagepadapter;

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
    private void setPagerAdapter(){
        pagepadapter = new PageAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagepadapter);
    }

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Deliverd");
        tabLayout.getTabAt(1).setText("Pending");

    }
}