package com.example.ina.nativepigdummy.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ina.nativepigdummy.Fragments.BoarsFragment;
import com.example.ina.nativepigdummy.Fragments.FemaleGrowersFragment;
import com.example.ina.nativepigdummy.Fragments.MaleGrowerFragment;
import com.example.ina.nativepigdummy.Fragments.SowsFragment;

public class GrowerRecordsPageAdapter extends FragmentPagerAdapter {

    private int num_of_tabs;

    GrowerRecordsPageAdapter(FragmentManager fm, int num_of_tabs) {
        super(fm);
        this.num_of_tabs = num_of_tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new FemaleGrowersFragment();
            case 1:
                return new MaleGrowerFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num_of_tabs;
    }
}
