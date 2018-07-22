package com.example.ina.nativepigdummy.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ina.nativepigdummy.Fragments.EditBreedingRecordFragment;
import com.example.ina.nativepigdummy.R;

public class SowLitterActivity extends AppCompatActivity {

    Toolbar tool_bar;
    TabLayout tab_layout;
    ViewPager view_pager;
    PagerAdapter page_adapter;
    private TabItem tab_view_sow_litter_record;
    private TabItem tab_offspring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sow_litter);

        tool_bar = findViewById(R.id.tool_bar);
        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        tab_view_sow_litter_record = findViewById(R.id.tab_view_sow_litter_record);
        tab_offspring = findViewById(R.id.tab_offspring);

        page_adapter = new SowLitterPageAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        view_pager.setAdapter(page_adapter);

        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_breeding_records = new Intent(SowLitterActivity.this, ViewBreedingActivity.class);
                startActivity(intent_breeding_records);
            }
        });

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    tool_bar.setBackgroundColor(ContextCompat.getColor(SowLitterActivity.this,
                            R.color.colorPrimary));
                    tab_layout.setBackgroundColor(ContextCompat.getColor(SowLitterActivity.this,
                            R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(SowLitterActivity.this,
                                R.color.colorPrimary));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

    }
}
