package com.technomaniacs.android.mypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static Intent makeIntent(Context context)
    {
        return new Intent(context,EditorActivity.class);
    }


    private static final int PET_LOADER = 0;


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
    private Uri mCurrentPetUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        initViews();

        Intent receivedIntent=getIntent();
        mCurrentPetUri=receivedIntent.getData();

        if(mCurrentPetUri==null)
        {
            this.setTitle(R.string.editor_activity_add_new);
        }
        else
        {
            this.setTitle(R.string.editor_activity_edit_pet);
            LoaderManager.getInstance(this).initLoader(PET_LOADER,null,this);
        }

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




    private long  insertData()
    {
        //data
        String name= String.valueOf(mNameEditText.getText());
        String breed=String.valueOf(mBreedEditText.getText());
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
        Uri uri = getContentResolver().insert(PetContract.CONTENT_URI_ALL,values);//returns uri/id otherwise null
        return (uri==null)?-1: ContentUris.parseId(uri);
    }

    private long updateData()
    {

        //data
        String name= String.valueOf(mNameEditText.getText());
        String breed=String.valueOf(mBreedEditText.getText());
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


        long rowsAffected=getContentResolver().update(mCurrentPetUri,values,null,null);
        return rowsAffected;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentPetUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }


    private void showSaveAndUpdateDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(mCurrentPetUri==null) {
            builder.setMessage(R.string.save_dialog_msg);
        }
        else {
            builder.setMessage(R.string.update_dialog_msg);
        }
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                if(mCurrentPetUri==null)
                {
                    long rowId=insertData();

                    if(rowId!=-1)
                    {
                        Toast.makeText(getApplicationContext(),"Record Inserted !!!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Insertion Failed!!!",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                   long rowsAffected=updateData();
                    if(rowsAffected!=-1)
                    {
                        Toast.makeText(getApplicationContext(),"Record Updated !!!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Updation Failed!!!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        int id=item.getItemId();
        if(id==R.id.action_save)
        {
            showSaveAndUpdateDialog();

        }
        else if(id==R.id.action_delete)
        {
            showDeleteConfirmationDialog();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deletePet();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deletePet() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentPetUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentPetUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, @Nullable Bundle args) {
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_WEIGHT
        };
        switch (loaderID) {
            case PET_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        this,   // Parent activity context
                        mCurrentPetUri, // Table to query
                        projection,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String breed = cursor.getString(breedColumnIndex);
            int gender = cursor.getInt(genderColumnIndex);
            int weight = cursor.getInt(weightColumnIndex);

            // Update the views on the screen with the values from the database
            mNameEditText.setText(name);
            mBreedEditText.setText(breed);
            mWeightEditText.setText(Integer.toString(weight));

            // Gender is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 is Unknown, 1 is Male, 2 is Female).
            // Then call setSelection() so that option is displayed on screen as the current selection.
            switch (gender) {
                case PetEntry.GENDER_MALE:
                    mGenderSpinner.setSelection(1);
                    break;
                case PetEntry.GENDER_FEMALE:
                    mGenderSpinner.setSelection(2);
                    break;
                default:
                    mGenderSpinner.setSelection(0);
                    break;
            }
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        // If the loader is invalidated, clear out all the data from the input fields.
        mNameEditText.setText("");
        mBreedEditText.setText("");
        mWeightEditText.setText("");
        mGenderSpinner.setSelection(0); // Select "Unknown" gender

    }
}