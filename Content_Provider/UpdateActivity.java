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
import android.widget.Toast;

public class UpdateActivity extends LifeCycleLoggingActivity {

    private final String TAG=getClass().getSimpleName();

    public static Intent makeIntent(Context context)
    {
        return new Intent(context,UpdateActivity.class);
    }
  EditText id,grade;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        id=findViewById(R.id.id);
        grade=findViewById(R.id.grade);
        update=findViewById(R.id.update);
    }

    public void updateClick(View view)
    { String URL = "content://com.student.details";
        String ids=id.getText().toString();
        try{
            Integer.parseInt(ids);
            URL=URL+"/students/"+ids;

        }
        catch(Exception e)
        {
           URL=URL;
        }

        Log.d("ud",URL);
        Uri students = Uri.parse(URL);
        String grd=grade.getText().toString();
        ContentValues values=new ContentValues();
        values.put(StudentContentProvider.GRADE,grd);
        int count=getContentResolver().update(students,values,null,null);
        Toast.makeText(this,count+" row updated",Toast.LENGTH_LONG).show();
    }
}