package com.smartisan.edward.listdemo.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class ListDemoProvider extends ContentProvider {


    public static final String AUTHORITY = "com.smartisan.edward.listdemo.provider";
    private static final String TABLE_NAME ="list";

    private SQLiteOpenHelper listDemoSQLiteOpenHelper;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        listDemoSQLiteOpenHelper = new ListDemoSQLiteOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        mDb = listDemoSQLiteOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        Log.d("Test","helper");
        if(uri != null) {
            queryBuilder.setTables(TABLE_NAME);
        }

        return queryBuilder.query(mDb,projection,selection,selectionArgs,null,null,sortOrder);

    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        mDb= listDemoSQLiteOpenHelper.getWritableDatabase();

            Log.d("Test",selection);
            int count = mDb.delete(TABLE_NAME,selection,null);
            this.getContext().getContentResolver().notifyChange(uri, null);
            return count;
}

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        mDb= listDemoSQLiteOpenHelper.getReadableDatabase();
        long rowID = mDb.insert(TABLE_NAME,null,values);
        if(rowID>0){
            this.getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(Uri.parse("content://"+AUTHORITY+"/list"),rowID);
        }
        return null;

    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        mDb= listDemoSQLiteOpenHelper.getReadableDatabase();
        int count = mDb.update(TABLE_NAME,values,selection,null);
        this.getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
