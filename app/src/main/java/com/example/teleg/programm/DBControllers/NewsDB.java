package com.example.teleg.programm.DBControllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsDB extends SQLiteOpenHelper {

    public  static final int DATABASE_VERSION = 1;
    public  static final String DATABASE_NAME = "NewsDB";


    public  static final String TABLE_NEWS = "News";

    public  static final String KEY_ID = "_id";
    public  static final String KEY_TITLE = "Title";
    public  static final String KEY_DATE = "Date";
    public  static final String KEY_DESCRIPTION = "Description";




    public NewsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NEWS + "(" + KEY_ID
                + " integer primary key," + KEY_TITLE + " text,"
                + KEY_DATE + " text,"
                + KEY_DESCRIPTION + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NEWS);
        onCreate(db);
    }
}
