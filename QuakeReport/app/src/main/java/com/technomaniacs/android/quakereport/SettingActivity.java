package com.technomaniacs.android.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    private int magnitude=1;

    TextView mMagView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mMagView=findViewById(R.id.magnitude);

        SharedPreferences sharedPrefs = getSharedPreferences("app",MODE_PRIVATE);
        String minMagnitude = sharedPrefs.getString(
                "minmag",
                "5");
        magnitude=Integer.parseInt(minMagnitude);
        mMagView.setText(magnitude+"");

    }

    public void addMag(View view) {

        if(magnitude < 7)
        {
            magnitude+=1;
            mMagView.setText(magnitude+"");
        }

    }

    public void subMag(View view) {

        if(magnitude > 1)
        {
            magnitude-=1;
            mMagView.setText(magnitude+"");
        }

    }

    public void updatePref(View view) {

        SharedPreferences sharedPreferences= getSharedPreferences("app",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("minmag",magnitude+"");
        editor.apply();
    }


}