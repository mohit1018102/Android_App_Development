package com.technomaniacs.android.mypet;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.technomaniacs.android.mypet.Adapter.PetCursorAdapter;
import com.technomaniacs.android.mypet.db.PetContract;
import com.technomaniacs.android.mypet.db.PetContract.PetEntry;
import com.technomaniacs.android.mypet.db.PetDbHelper;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int PET_LOADER = 0;
    private FloatingActionButton mNewPet;
    private TextView mEmptyView;
    private TextView mEmptySubtitleText;
    private ListView mPetListView;
    private PetCursorAdapter mPetCursorAdapter;



    private long insertDummyData()
    {

       // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME,"Huppi");
        values.put(PetEntry.COLUMN_PET_BREED,"Husky");
        values.put(PetEntry.COLUMN_PET_GENDER,PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT,10);

        // Insert the new row, returning the primary key value of the new row
        Uri uri = getContentResolver().insert(PetContract.CONTENT_URI_ALL,values);//returns id otherwise -1
        return (uri==null)?-1: ContentUris.parseId(uri);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_add_pet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

    }

    private void init()
    {

        mEmptyView=findViewById(R.id.empty_img);
        mEmptySubtitleText=findViewById(R.id.empty_subtitle_text);
        mNewPet=findViewById(R.id.floatingActionButton);
        mNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=EditorActivity.makeIntent(CatalogActivity.this);
                startActivity(intent);
            }
        });

        mPetListView = (ListView) findViewById(R.id.pet_list_view);
        mPetCursorAdapter=new PetCursorAdapter(this,null);
        mPetListView.setAdapter(mPetCursorAdapter);
        LoaderManager.getInstance(this).initLoader(PET_LOADER, null, this);

        mPetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link PetEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.pets/pets/2"
                // if the pet with ID 2 was clicked on.
                Uri currentPetUri = ContentUris.withAppendedId(PetContract.CONTENT_URI_ALL, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPetUri);

                // Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Wipe_out_all_data);
        builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                int row=getContentResolver().delete(PetContract.CONTENT_URI_ALL,null,null);
                mEmptyView.setVisibility(View.VISIBLE);
                if(row!=0)
                Toast.makeText(getApplicationContext(),"All data deleted !!!",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Nothing to delete!!!",Toast.LENGTH_SHORT).show();

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.insert_dummy_data) {
            long rowId=insertDummyData();
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
               showDeleteConfirmationDialog();
            }
        return super.onOptionsItemSelected(item);
    }

    /** cursor loader **/

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
                        PetContract.CONTENT_URI_ALL, // Table to query
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
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data.getCount()>0)
        {   mEmptyView.setVisibility(View.GONE);
            mEmptySubtitleText.setVisibility(View.GONE);
            mPetCursorAdapter.swapCursor(data);
        }
        else
        {   mPetCursorAdapter.swapCursor(data);
            mEmptyView.setVisibility(View.VISIBLE);
            mEmptySubtitleText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mPetCursorAdapter.swapCursor(null); // called when data need to be deleted.
    }
}