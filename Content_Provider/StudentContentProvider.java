package com.e.studentlog;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class StudentContentProvider extends ContentProvider {

    public static final String AUTHORITY_NAME="com.student.details";
    public static final String URL="content://" + AUTHORITY_NAME+"/students";
    public static  final Uri CONTENT_URI=Uri.parse(URL);

    public static final String _ID="_id";
    public static final String NAME="name";
    public static  final String GRADE="grade";

    private SQLiteDatabase db;
    static final String DATABASE_NAME="College";
    static final String STUDENTS_TABLE_NAME="students";
    static final int DATABASE_VERSION=1;

    static final int STUDENTS=1;
    static  final int STUDENT_ID=2;
    static HashMap<String,String> STUDENTS_PROJECTION_MAP;
    static final UriMatcher uriMatcher;
    static{
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY_NAME,"students",STUDENTS);
        uriMatcher.addURI(AUTHORITY_NAME,"students/#",STUDENT_ID);

    }

    static final String CREATE_DB_TABLE= "CREATE TABLE "+STUDENTS_TABLE_NAME+
            "("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ NAME + " TEXT NOT NULL,"+GRADE+" TEXT NOT NULL);";

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS "+STUDENTS_TABLE_NAME);
            onCreate(db);

        }
    }




    @Override
    public boolean onCreate() {
        Context context =getContext();
        DatabaseHelper dbHelper=new DatabaseHelper(context);

        db=dbHelper.getWritableDatabase();
        return (db==null)?false:true;
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId=db.insert(STUDENTS_TABLE_NAME,"",values);
        if(rowId>0)
        {
            Uri _uri= ContentUris.withAppendedId(CONTENT_URI,rowId);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        qb.setTables(STUDENTS_TABLE_NAME);
        switch(uriMatcher.match(uri))
        {
            case STUDENTS:
                     qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                     break;
            case STUDENT_ID: qb.appendWhere(_ID+"="+uri.getPathSegments().get(1));
        }

        if(sortOrder==null)
        {
            sortOrder=NAME;
        }

        Cursor c=qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
       int count=0;
       switch(uriMatcher.match(uri))
       {
           case STUDENTS:
               count=db.update(STUDENTS_TABLE_NAME,values,selection,selectionArgs);
               break;
           case STUDENT_ID:
               String sel=_ID+"="+uri.getPathSegments().get(1)+(!TextUtils.isEmpty(selection)? "AND ("+selection+")" : "");
               count=db.update(STUDENTS_TABLE_NAME,values,sel,selectionArgs);
               break;

       }
       getContext().getContentResolver().notifyChange(uri,null);
       return count;

    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count=0;
        switch(uriMatcher.match(uri))
        {
            case STUDENTS:
                count=db.delete(STUDENTS_TABLE_NAME,selection,selectionArgs);
                break;
            case STUDENT_ID:
                String sel=_ID+"="+uri.getPathSegments().get(1)+(!TextUtils.isEmpty(selection)? "AND ("+selection+")" : "");
                count=db.delete(STUDENTS_TABLE_NAME,sel,selectionArgs);
                break;

        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;

    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri))
        {
            case STUDENTS:
                return "vnd.android.cursor.dir/vnd.example.students";
            case STUDENT_ID:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("UNSUPPORTED URI :"+uri);
        }

    }
}
