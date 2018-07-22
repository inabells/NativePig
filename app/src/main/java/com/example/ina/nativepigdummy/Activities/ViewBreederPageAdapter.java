package com.example.ina.nativepigdummy.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ina.nativepigdummy.Fragments.BreederWeightRecordsFragment;
import com.example.ina.nativepigdummy.Fragments.GrossMorphologyFragment;
import com.example.ina.nativepigdummy.Fragments.MorphCharFragment;
import com.example.ina.nativepigdummy.Fragments.ViewBreederFragment;


public class ViewBreederPageAdapter extends FragmentPagerAdapter {

    private int num_of_tabs;

    ViewBreederPageAdapter(FragmentManager fm, int num_of_tabs) {
        super(fm);
        this.num_of_tabs = num_of_tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new ViewBreederFragment();
            case 1:
                return new GrossMorphologyFragment();
            case 2:
                return new MorphCharFragment();
            case 3:
                return new BreederWeightRecordsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num_of_tabs;
    }
}
