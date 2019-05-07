package com.example.teleg.programm;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.teleg.programm.MainCalendarClasses.CustomCalendar;
import com.example.teleg.programm.MainCalendarClasses.dao.EventData;
import com.example.teleg.programm.MainCalendarClasses.dao.dataAboutDate;
import com.example.teleg.programm.MainCalendarClasses.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Random;

import fragments.MainCalendarFragment;
import fragments.NewsFragment;
import fragments.ScheduleFragmentMain;
import fragments.SettingsFragment;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mTextMessage;
    private CustomCalendar customCalendar;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_schedule:
                        mTextMessage.setText(R.string.schedule);
                        loadFragment(ScheduleFragmentMain.newInstance());
                        return true;
                    case R.id.action_news:
                            mTextMessage.setText(R.string.news);
                            loadFragment(NewsFragment.newInstance());
                            return true;
                    case R.id.action_calender:
                        mTextMessage.setText(R.string.calender);
                        loadFragment(MainCalendarFragment.newInstance());
                        return true;
                    case R.id.action_tools:
                        mTextMessage.setText(R.string.tools);
                        loadFragment(SettingsFragment.newInstance());
                        return true;
                }
                return false;
            }
        };



    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (fragment.equals(MainCalendarFragment.newInstance())) {
            fragment.onDestroy();
            fragment.onDetach();
            fragment.onStart();
            ft.replace(R.id.fl_content, fragment);
            ft.commit();
        }else {
            ft.replace(R.id.fl_content, fragment);
            ft.commit();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadFragment(ScheduleFragmentMain.newInstance());

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG,"onConfigurationChanged: orientation = portrait");
        }else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.i(TAG,"onConfigurationChanged: orientation = landscape");
        }
    }
}
