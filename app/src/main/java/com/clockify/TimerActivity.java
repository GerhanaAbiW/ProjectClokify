package com.clockify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class TimerActivity extends AppCompatActivity {

    private FragmentManager fmanager = getSupportFragmentManager();
    private ViewPager viewPager;
    private TabLayout tab;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ScreenSlidePagerAdapter adapter;
    String[] titles = {"Timer", "Activity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        viewPager = findViewById(R.id.pageView);
        tab = findViewById(R.id.tab);

        initViews();
    }

    public TimerFragment newFragment1() {
        TimerFragment timer = new TimerFragment();
        return timer;
    }

    public ActivityFragment newFragment2() {
        ActivityFragment activity = new ActivityFragment();
        return activity;
    }

    private void initViews() {

        fragments.add(newFragment1());
        fragments.add(newFragment2());
        adapter = new ScreenSlidePagerAdapter(fmanager);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);


    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


}

