package com.smartisan.edward.listdemo.Provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by smartisan on 15-12-17.
 */
public class ListDemoSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "list.db";
    private static final String CREATE_DATABASE = "create table list(_id integer PRIMARY KEY AUTOINCREMENT,title varchar(100),author varchar(20),subject varchar(1000));";

    public ListDemoSQLiteOpenHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
