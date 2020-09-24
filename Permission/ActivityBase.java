package com.studentdetails;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

public class ActivityBase extends LifeCycleLoggingActivity {
    //PERMISSION STRINGS
    protected final String WRITE="android.permission.READ_EXTERNAL_STORAGE";
    protected final String READ="android.permission.WRITE_EXTERNAL_STORAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processPermission();

    }

    public void processPermission()
    {
        //..PERMISSION STRING ARRAY AND REQUESTCODE
        String[] perms={WRITE,READ};
        final int requestCode=200;

        //RUNTIME PERMISSION FOR DEVICE VERSION M AND ABOVE
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {

            if(checkSelfPermission(WRITE)==PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(READ)==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Permission Already granted",Toast.LENGTH_SHORT).show();
            }
            else {
                requestPermissions(perms, requestCode);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,String[] permissions,int[] grantResults)
    {   boolean w_r=false;
        switch(permsRequestCode)
        {
            case 200:
                w_r= grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(w_r)
        {
            Toast.makeText(this,"Permission granted successfully",Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this,"Permission Denied: read/write operations are disabled",Toast.LENGTH_SHORT).show();
        }

    }
}
