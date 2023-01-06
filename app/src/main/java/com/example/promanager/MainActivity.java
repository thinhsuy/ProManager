package com.example.promanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public Query db;
    FragmentPagerAdapter adapterViewPager;
    public TabLayout tabLayout;
    public String userId;
    private static Context context;
    public static Context getAppContext() {
        return MainActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
        db = ((GlobalVar)this.getApplication()).getLocalQuery();
        userId = tryToSetUserId();
        tabLayout = (TabLayout) findViewById(R.id.pager_tablayout);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), userId);
        vpPager.setAdapter(adapterViewPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(MainActivity.this,
//                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });


        ((ImageView)findViewById(R.id.notification_icon)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            }
        });
    }

    private String tryToSetUserId(){
        try {
            Bundle bundle = getIntent().getExtras();
            String userId = bundle.getString("username");
            ((GlobalVar)this.getApplication()).setUserId(userId);
            Log.e("Set userId", userId);
        } catch (Exception ex){Log.e("Set userId", "Failed!");}
        return ((GlobalVar)this.getApplication()).getUserId();
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 4;
        private String userId = "none";

        public MyPagerAdapter(FragmentManager fragmentManager, String userId) {
            super(fragmentManager);
            this.userId = userId;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            View view = findViewById(R.id.user_bubble_textview).getRootView();
            return MyFragment.newInstance(position, this.userId, view);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            String[] titles = {"Activity", "Infor", "Own", "Seek"};
            return titles[position];
        }
    }
}
