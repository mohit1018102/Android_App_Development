package com.technomaniacs.android.miwokapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private MiwokMediaPlayerController mMiwokMediaPlayerController;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MiwokMediaPlayerControllerSingleton.init(getApplicationContext());
        mMiwokMediaPlayerController = MiwokMediaPlayerControllerSingleton.getmMiwokMediaPlayerController();
        viewPager = findViewById(R.id.parent_view_pager);
        MiwokFragmentPagerAdapter miwokFragmentPagerAdapter = new MiwokFragmentPagerAdapter(this, getSupportFragmentManager(), 0);
        viewPager.setAdapter(miwokFragmentPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMiwokMediaPlayerController.stop();
    }


    /*PagerAdapter should call the destroyItem method not only when it surpasses the offLimitScreenPageLimit but also when a screen rotation occurs, but it doesn't, so it has to be forced to do so... to achieve it,
       you just have to set to null the adapter on the onStop or onDestroy method of the activity.
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMiwokMediaPlayerController = null;
        MiwokMediaPlayerControllerSingleton.removemMiwokMediaPlayerController();
        viewPager.setAdapter(null);
    }
}