package com.smartisan.edward.listdemo.Provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by smartisan on 15-12-17.
 */
public class ListDemoSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "list.db";
    private static final String CREATE_DATABASE = "create table list(_id integer PRIMARY KEY AUTOINCREMENT,title varchar(100),author varchar(20),subject varchar(1000));";
    private static final String CREATE_DATA = "insert into list values(null,'the first item','wcwj','this is for test');";
    private static final String CREATE_DATA1 = "insert into list values(null,'the second item','wj','this is for test2')";
    public ListDemoSQLiteOpenHelper(Context context){

        super(context,DATABASE_NAME,null,1);
        Log.d("Test","database");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Test","database is create");
        db.execSQL(CREATE_DATABASE);
        db.execSQL(CREATE_DATA);
        db.execSQL(CREATE_DATA1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS list");
        onCreate(db);
    }
}
