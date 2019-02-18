package com.example.abhishek.weather;

import android.app.Activity;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private Context context;

    public static final String DB_NAME = "myDB.db";
    public static final int dbverstion = 1;

    //TABLE NAME
    public static final String LOGIN_TABLE ="Login";

    //LOGIN TABLE FIELDS
    public static final String LOGIN_ID ="LoginId";
    public static final String USERNAME="Name";
    public static final String EMAIL ="Email";
    public static final String PASSWORD ="Password";
    private SQLiteOpenHelper db;

    public DatabaseHandler(Context context)
    {
        super(context, DB_NAME, null, dbverstion);
        this.context = context;
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler)
    {
        super(context, DB_NAME, null, dbverstion, errorHandler);
    }

//    public DatabaseHandler(Context context, String name, int version, SQLiteDatabase.OpenParams openParams)
//    {
//        super(context, name, version, openParams);
//    }


    @Override

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE Login(LoginId INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Email TEXT, Password TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        android.util.Log.v("Constant","Upgrading Database");
        onCreate(db);
    }
}
