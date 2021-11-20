package com.technomaniacs.android.mypet.db;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;
import java.util.stream.BaseStream;

public final class PetContract {

    public static final String SQL_CREATE_ENTRIES ="CREATE TABLE "+ PetEntry.TABLE_NAME+
            "("+ PetEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
              +PetEntry.COLUMN_PET_NAME +" TEXT NOT NULL,"
              +PetEntry.COLUMN_PET_BREED+ " TEXT,"
              +PetEntry.COLUMN_PET_GENDER+ " INTEGER NOT NULL,"
              +PetEntry.COLUMN_PET_WEIGHT+ " INTEGER NOT NULL DEFAULT 0"
            +")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PetEntry.TABLE_NAME;


    private PetContract()
    {
    }

    public static final String CONTENT_AUTHORITY = "com.technomaniacs.android.mypet";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri CONTENT_URI_ALL = Uri.withAppendedPath(BASE_CONTENT_URI, PetEntry.TABLE_NAME);

    public static class PetEntry implements BaseColumns
    {
        //********table*************
        public static final String TABLE_NAME="pets";
        public static final String _ID=BaseColumns._ID;
        public static final String COLUMN_PET_NAME="name";
        public static final String COLUMN_PET_BREED="breed";
        public static final String COLUMN_PET_GENDER="gender";
        public static final String COLUMN_PET_WEIGHT="weight";

        //*********gender**********
        public static final int GENDER_UNKNOWN=0;
        public static final int GENDER_MALE=1;
        public static final int GENDER_FEMALE=2;

    }
}
