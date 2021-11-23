package com.technomaniacs.android.mypet.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import com.technomaniacs.android.mypet.db.PetContract.PetEntry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.nio.ShortBuffer;

public class PetProvider extends ContentProvider {

    private PetDbHelper mPetDbHelper=null;

    /** URI matcher code for the content URI for the pets table */
    private static final int PETS = 100;

    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int PET_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetEntry.TABLE_NAME, PETS);
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetEntry.TABLE_NAME +"/#", PET_ID);
    }

    @Override
    public boolean onCreate() {
        mPetDbHelper=new PetDbHelper(getContext().getApplicationContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=mPetDbHelper.getReadableDatabase();
        Cursor cursor=null;
        int id=sUriMatcher.match(uri);
        switch (id)
        {
            case PETS:
                break;
            case PET_ID:
                selection=PetEntry._ID+"=?";
                selectionArgs=new String[] {String.valueOf(ContentUris.parseId(uri))};
                break;
            default:
        }

        cursor= db.query(PetEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    private boolean sanityCheck(ContentValues values)
    {
        String name = values.getAsString(PetEntry.COLUMN_PET_NAME);
        int gender = values.getAsInteger(PetEntry.COLUMN_PET_GENDER);
        int weight = values.getAsInteger(PetEntry.COLUMN_PET_WEIGHT);
        String result="";
        if(name.equals(""))
        {
            result+="name";
        }

        if(gender!=PetEntry.GENDER_FEMALE && gender!=PetEntry.GENDER_MALE &&  gender!=PetEntry.GENDER_UNKNOWN )
        {
            result+=", gender, ";
        }

        if(weight==-1)
        {
            result+=", weight";
        }

        if(result.length()!=0)
        {
            Toast.makeText(getContext().getApplicationContext(),"Invalid : "+result,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }


    private Uri insertPet(Uri uri,ContentValues contentValues)
    {
        SQLiteDatabase db = mPetDbHelper.getWritableDatabase();

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PetEntry.TABLE_NAME, null, contentValues);//returns id otherwise -1
        // Notify all listeners that the data has changed for the pet content URI
        if(newRowId>=0) getContext().getContentResolver().notifyChange(uri, null);
        return (newRowId<0)?null:Uri.withAppendedPath(uri, String.valueOf(newRowId));
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
         if(sanityCheck(values)==false) return null;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return insertPet(uri,values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db=mPetDbHelper.getWritableDatabase();
        int rowDeleted=0;
        int id=sUriMatcher.match(uri);
        switch (id)
        {
            case PETS:
                break;
            case PET_ID:
                selection=PetEntry._ID+"=?";
                selectionArgs=new String[] {String.valueOf(ContentUris.parseId(uri))};
                break;
            default:
        }
        rowDeleted=db.delete(PetEntry.TABLE_NAME,selection,selectionArgs);

        if(rowDeleted!=0){
            getContext().getContentResolver().notifyChange(uri, null);

        }
        return rowDeleted;
    }




    private int updateData(ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mPetDbHelper.getWritableDatabase();

        return db.update(PetEntry.TABLE_NAME,values,selection,selectionArgs);

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (sanityCheck(values)==false) return -1;
        int rowAffected=0;
        int id= sUriMatcher.match(uri);
        switch (id)
        {
            case PET_ID:
                selection=PetEntry._ID+"=?";
                selectionArgs=new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowAffected=updateData(values,selection,selectionArgs);
                if(rowAffected>0) getContext().getContentResolver().notifyChange(uri, null);
                break;
            default: return -1;
        }
        return rowAffected;
    }

}
