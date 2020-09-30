package com.e.studentlog;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddActivity extends LifeCycleLoggingActivity {

    private final String TAG=getClass().getSimpleName();

    private EditText name,grade;
    private ImageButton add;

    public static Intent makeIntent(Context context)
    {
        return new Intent(context,AddActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Log.d(TAG,"ADD ACTIVITY INITIALIZATION");
        intialize();

    }

    private void intialize()
    { name=findViewById(R.id.name);
      grade=findViewById(R.id.grade);
      add=findViewById(R.id.insert);
    }

    public void addData(View view)
    {
        ContentValues values=new ContentValues();
        values.put(StudentContentProvider.NAME,name.getText().toString());
        values.put(StudentContentProvider.GRADE,grade.getText().toString());
        Uri uri=getContentResolver().insert(StudentContentProvider.CONTENT_URI,values);

        Toast.makeText(getBaseContext(),uri.toString(),Toast.LENGTH_LONG).show();

    }
}