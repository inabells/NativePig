package com.example.ina.nativepigdummy.Activities;

import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ina.nativepigdummy.R;

public class ViewGrowerActivity extends AppCompatActivity {

    Toolbar tool_bar;
    TabLayout tab_layout;
    ViewPager view_pager;
    PagerAdapter page_adapter;
    private TabItem tab_view_grower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grower);

        tool_bar = findViewById(R.id.tool_bar);
        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        tab_view_grower = findViewById(R.id.tab_view_grower);

        page_adapter = new ViewGrowerPageAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        view_pager.setAdapter(page_adapter);

        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_grower_records = new Intent(ViewGrowerActivity.this, GrowerRecordsActivity.class);
                startActivity(intent_grower_records);
            }
        });

        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
    }
}
