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

public class ViewBreedingActivity extends AppCompatActivity {

    Toolbar tool_bar;
    TabLayout tab_layout;
    ViewPager view_pager;
    PagerAdapter page_adapter;
    private TabItem tab_view_breeding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_breeding);

        tool_bar = findViewById(R.id.tool_bar);
        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        tab_view_breeding = findViewById(R.id.tab_view_breeding);

        page_adapter = new ViewBreedingPageAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        view_pager.setAdapter(page_adapter);

        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_breeding_records = new Intent(ViewBreedingActivity.this, BreedingRecordsActivity.class);
                startActivity(intent_breeding_records);
            }
        });

        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

    }
}
