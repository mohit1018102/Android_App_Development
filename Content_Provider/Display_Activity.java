package com.e.studentlog;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Display_Activity extends LifeCycleLoggingActivity {

    private final String TAG = getClass().getSimpleName();

    public static Intent makeIntent(Context context) {
        return new Intent(context, Display_Activity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_);
    }

    public void displayClick(View view) {
        Log.d("sd","starting step");
        String URL = "content://com.student.details";

        Uri students = Uri.parse(URL);
        Cursor c = getContentResolver().query(students, null, null, null, "name");
        Log.d("sd","cursor created");
        int id_index=c.getColumnIndex(StudentContentProvider._ID);
        int name_index=c.getColumnIndex(StudentContentProvider.NAME);
        int grade_index=c.getColumnIndex(StudentContentProvider.GRADE);
        Log.d("sd",id_index+","+name_index+","+grade_index);
        if (c.moveToFirst()) {
            do {
                Toast.makeText(this,
                        c.getString((int)id_index)+
                                ", " + c.getString((int)name_index) +
                                ", " + c.getString((int)grade_index),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }
}





