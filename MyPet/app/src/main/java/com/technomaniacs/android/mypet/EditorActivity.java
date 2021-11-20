package com.technomaniacs.android.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.technomaniacs.android.mypet.db.PetContract;
import com.technomaniacs.android.mypet.db.PetContract.PetEntry;
import com.technomaniacs.android.mypet.db.PetDbHelper;

public class EditorActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context)
    {
        return new Intent(context,EditorActivity.class);
    }


    /** EditText field to enter the pet's name */
    private EditText mNameEditText;

    /** EditText field to enter the pet's breed */
    private EditText mBreedEditText;

    /** EditText field to enter the pet's weight */
    private EditText mWeightEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = PetEntry.GENDER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        initViews();
    }


    private void initViews()
    {
        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_add_pet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_dropdown_item_1line);

        // Specify dropdown layout style - simple list view with 1 item per line
       // genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        Context context=this;
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection=(String) parent.getItemAtPosition(position);
                if(selection.equals("Unknown"))
                {   mGender= PetEntry.GENDER_UNKNOWN;

                }
                else  if(selection.equals("Male"))
                    {   mGender= PetEntry.GENDER_MALE;

                     }
                else if(selection.equals("Female"))
                {   mGender=PetEntry.GENDER_FEMALE;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender= PetEntry.GENDER_UNKNOWN;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    private long  insertData()
    {
        //data
        String name= String.valueOf(mNameEditText.getText());
        String breed=String.valueOf(mNameEditText.getText());
        int gender=mGender;
        int weight;
        try {
            weight = Integer.parseInt(String.valueOf(mWeightEditText.getText()));
        }
        catch (Exception e)
        {
            weight=-1;
        }
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME,name);
        values.put(PetEntry.COLUMN_PET_BREED,breed);
        values.put(PetEntry.COLUMN_PET_GENDER,gender);
        values.put(PetEntry.COLUMN_PET_WEIGHT,weight);

        // Insert the new row, returning the primary key value of the new row
        //Uri made=Uri.withAppendedPath(PetContract.CONTENT_URI_ALL, String.valueOf(1));
        //int x=getContentResolver().update(made,values,null,null);
        //return x;
        Uri uri = getContentResolver().insert(PetContract.CONTENT_URI_ALL,values);//returns id otherwise -1
        return (uri==null)?-1: ContentUris.parseId(uri);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        int id=item.getItemId();
        if(id==R.id.action_save)
        {
            long rowId=insertData();

            if(rowId!=-1)
            {
                Toast.makeText(this,"Record Inserted !!!",Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(this,"Insertion Failed!!!",Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }


}