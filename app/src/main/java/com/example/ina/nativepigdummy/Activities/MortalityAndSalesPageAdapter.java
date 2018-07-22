package com.example.ina.nativepigdummy.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ina.nativepigdummy.Fragments.MortalityFragment;
import com.example.ina.nativepigdummy.Fragments.OthersFragment;
import com.example.ina.nativepigdummy.Fragments.SalesFragment;


public class MortalityAndSalesPageAdapter extends FragmentPagerAdapter {

    private int num_of_tabs;

    MortalityAndSalesPageAdapter(FragmentManager fm, int num_of_tabs) {
        super(fm);
        this.num_of_tabs = num_of_tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new MortalityFragment();
            case 1:
                return new SalesFragment();
            case 2:
                return new OthersFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num_of_tabs;
    }
}
