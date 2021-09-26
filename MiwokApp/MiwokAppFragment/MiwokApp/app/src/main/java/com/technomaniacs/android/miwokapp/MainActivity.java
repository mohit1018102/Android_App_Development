package com.technomaniacs.android.miwokapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public static MiwokMediaPlayerController miwokMediaPlayerController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        miwokMediaPlayerController=new MiwokMediaPlayerController(this);
        ViewPager viewPager=findViewById(R.id.parent_view_pager);
        MiwokFragmentPagerAdapter miwokFragmentPagerAdapter=new MiwokFragmentPagerAdapter(this,getSupportFragmentManager(),0);
        viewPager.setAdapter(miwokFragmentPagerAdapter);

        TabLayout tabs=findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStop() {
        super.onStop();
       miwokMediaPlayerController.stop();
    }
}