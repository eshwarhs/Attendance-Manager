package com.example.attendancemanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

public class timetable extends AppCompatActivity {

    private static final String TAG ="TimeTable";

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Log.d(TAG,"onCreate: Starting.");

        home = (Button)findViewById(R.id.tick_tt);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(timetable.this,HomeScreen.class);
                startActivity(i);
                finish();

            }
        });
        mSectionsPageAdapter=new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager=(ViewPager)findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout=(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager (ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(),"MON");
        adapter.addFragment(new Tab2Fragment(),"TUE");
        adapter.addFragment(new Tab3Fragment(),"WED");
        adapter.addFragment(new Tab4Fragment(),"THU");
        adapter.addFragment(new Tab5Fragment(),"FRI");
        adapter.addFragment(new Tab6Fragment(),"SAT");
        adapter.addFragment(new Tab7Fragment(),"SUN");
        viewPager.setAdapter(adapter);
    }



}