package com.e.studentlog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteActivity extends LifeCycleLoggingActivity {

    private final String TAG=getClass().getSimpleName();
    EditText _id;
    Button delete;


    public static Intent makeIntent(Context context)
    {
        return new Intent(context,DeleteActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        _id=findViewById(R.id.id);
        delete=findViewById(R.id.delete);
    }

    public void deleteClick(View view)
    {
        String ids=_id.getText().toString();
        String URL = "content://com.student.details";

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
        int count=getContentResolver().delete(students,null,null);
        Toast.makeText(this,count+" row deleted",Toast.LENGTH_LONG).show();
    }
}