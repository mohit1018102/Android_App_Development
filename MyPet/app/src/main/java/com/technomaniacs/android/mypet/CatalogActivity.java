package com.technomaniacs.android.mypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.technomaniacs.android.mypet.db.PetContract.PetEntry;
import com.technomaniacs.android.mypet.db.PetDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private FloatingActionButton mNewPet;


    private void displayDatabaseInfo()
    {
        PetDbHelper dbHelper=new PetDbHelper(this);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=null;
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER
        };


    try{
        cursor = db.query(
                PetEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

           int nameid=cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);

          cursor.moveToLast();

            Toast.makeText(this,cursor.getString(nameid)+" "+cursor.getCount(),Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"failed!!!",Toast.LENGTH_LONG).show();

        }
        finally {
            cursor.close();
            db.close();
        }

    }

    private long insertData()
    {    PetDbHelper dbHelper=new PetDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

       // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME,"dummy dog");
        values.put(PetEntry.COLUMN_PET_BREED,"dummy");
        values.put(PetEntry.COLUMN_PET_GENDER,0);
        values.put(PetEntry.COLUMN_PET_WEIGHT,32);



        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PetEntry.TABLE_NAME, null, values);//returns id otherwise -1
        return newRowId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_add_pet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        //insertData();
        displayDatabaseInfo();
    }

    private void init()
    {

        mNewPet=findViewById(R.id.floatingActionButton);
        mNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=EditorActivity.makeIntent(CatalogActivity.this);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.insert_dummy_data) {
            long rowId=insertData();
            if(rowId!=-1)
            {
                Toast.makeText(this,"Dummy Record Inserted !!!",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(this,"Insertion Failed!!!",Toast.LENGTH_SHORT).show();
            }

        }
        else
            if(id==R.id.delete_all)
            {

            }
        return super.onOptionsItemSelected(item);
    }
}