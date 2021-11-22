package com.technomaniacs.android.mypet;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private ListView mPetListView;
    private PetCursorAdapter mPetCursorAdapter;



    private long insertDummyData()
    {

       // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME,"dummy dog");
        values.put(PetEntry.COLUMN_PET_BREED,"dummy");
        values.put(PetEntry.COLUMN_PET_GENDER,0);
        values.put(PetEntry.COLUMN_PET_WEIGHT,32);

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
        getSupportLoaderManager().initLoader(PET_LOADER, null, this);
        mEmptyView=findViewById(R.id.empty_list);
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
                int row=getContentResolver().delete(PetContract.CONTENT_URI_ALL,null,null);
                mEmptyView.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"All data deleted : row count "+row,Toast.LENGTH_SHORT).show();
            }
        return super.onOptionsItemSelected(item);
    }

    /** cursor loader **/

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, @Nullable Bundle args) {
        PetDbHelper dbHelper=new PetDbHelper(this);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=null;
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
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
            mPetCursorAdapter.swapCursor(data);
        }
        else
        {
            mEmptyView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mPetCursorAdapter.swapCursor(null); // called when data need to be deleted.
    }
}