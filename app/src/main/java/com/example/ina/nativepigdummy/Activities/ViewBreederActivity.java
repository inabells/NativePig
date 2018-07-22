package com.example.ina.nativepigdummy.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.ina.nativepigdummy.R;

public class ViewBreederActivity extends AppCompatActivity {

    Toolbar tool_bar;
    TabLayout tab_layout;
    ViewPager view_pager;
    PagerAdapter page_adapter;
    private TabItem tab_view_breeder;
    private TabItem tab_gross_morphology;
    private TabItem tab_morphometric_char;
    private TabItem tab_breeder_weight_records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_breeder);

        tool_bar = findViewById(R.id.tool_bar);
        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        tab_view_breeder = findViewById(R.id.tab_view_breeder);
        tab_gross_morphology = findViewById(R.id.tab_gross_morphology);
        tab_morphometric_char = findViewById(R.id.tab_morphometric_char);
        tab_breeder_weight_records = findViewById(R.id.tab_breeder_weight_records);

        page_adapter = new ViewBreederPageAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        view_pager.setAdapter(page_adapter);

        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_breeder_records = new Intent(ViewBreederActivity.this, BreederRecordsActivity.class);
                startActivity(intent_breeder_records);
            }
        });

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    tool_bar.setBackgroundColor(ContextCompat.getColor(ViewBreederActivity.this,
                            R.color.colorPrimary));
                    tab_layout.setBackgroundColor(ContextCompat.getColor(ViewBreederActivity.this,
                            R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(ViewBreederActivity.this,
                                R.color.colorPrimary));
                    }
                } else if (tab.getPosition() == 2) {
                    tool_bar.setBackgroundColor(ContextCompat.getColor(ViewBreederActivity.this,
                            R.color.colorPrimary));
                    tab_layout.setBackgroundColor(ContextCompat.getColor(ViewBreederActivity.this,
                            R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(ViewBreederActivity.this,
                                R.color.colorPrimary));
                    }
                } else {
                    tool_bar.setBackgroundColor(ContextCompat.getColor(ViewBreederActivity.this,
                            R.color.colorPrimary));
                    tab_layout.setBackgroundColor(ContextCompat.getColor(ViewBreederActivity.this,
                            R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(ViewBreederActivity.this,
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
