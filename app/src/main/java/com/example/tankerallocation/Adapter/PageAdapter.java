package com.example.tankerallocation.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.tankerallocation.UI.DeliverdStatus;
import com.example.tankerallocation.UI.PendingStatus;

public class PageAdapter extends FragmentPagerAdapter {
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DeliverdStatus();
            case 1:
                return new PendingStatus();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
