package com.studentdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LifeCycleLoggingActivity extends AppCompatActivity {
    private final String TAG=getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Activity intialization");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Activity becoming visible.");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG,"Activity ready for User Interaction");
    }

    @Override
    protected  void onPause()
    {
        super.onPause();
        Log.d(TAG,"Visible in background, unfocused Activity.");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG,"Activity is not longer visible.");
    }

    @Override
    protected  void onRestart()
    {
        super.onRestart();
        Log.d(TAG,"Activity comes to foreground after being stop.");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG,"Activity destroyed, resources are freed.");
    }
}
