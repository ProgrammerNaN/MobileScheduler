package com.example.teleg.programm.DBControllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScheduleDB extends SQLiteOpenHelper {

    public  static final int DATABASE_VERSION = 1;
    public  static final String DATABASE_NAME = "SchedulesDB";


    public  static final String TABLE_SCHEDULE_NU  = "Numerator";
    public  static final String TABLE_SCHEDULE_DE  = "Denominator";
    public  static final String TABLE_START_DATE  = "StartDate";
    public  static final String TABLE_GROUPS = "Groups";

    public  static final String KEY_DATESTART= "DateStart";

    public  static final String KEY_ID = "_id";
    public  static final String KEY_TITLE = "Title";
    public  static final String KEY_TYPE = "Type";
    public  static final String KEY_TIME = "Time";
    public  static final String KEY_DATE = "Date";
    public  static final String KEY_LOCATION = "Location";
    public  static final String KEY_TEACHER = "Teacher";
    public  static final String KEY_GROUP = "MyGroup";


    public static final String KEY_GROUPITEM = "GroupItem";



    public ScheduleDB (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

   @Override
   public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_SCHEDULE_NU  + "(" + KEY_ID
               + " integer primary key," + KEY_TITLE + " text,"
                + KEY_TYPE + " text,"
                + KEY_LOCATION + " text,"
                + KEY_TIME + " text,"
                + KEY_DATE  + " text,"
                + KEY_GROUP + " text,"
                + KEY_TEACHER+ " text" + ")");

       db.execSQL("create table " + TABLE_SCHEDULE_DE  + "(" + KEY_ID
               + " integer primary key," + KEY_TITLE + " text,"
               + KEY_TYPE + " text,"
               + KEY_LOCATION + " text,"
               + KEY_TIME + " text,"
               + KEY_DATE  + " text,"
               + KEY_GROUP + " text,"
               + KEY_TEACHER+ " text" + ")");

       db.execSQL("create table " + TABLE_START_DATE  + "(" + KEY_ID
               + " integer primary key," + KEY_DATESTART + " text"+ ")");

       db.execSQL("create table " + TABLE_GROUPS + "(" + KEY_ID
               + " integer primary key," + KEY_GROUPITEM + " text"+ ")");
  }

   @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_SCHEDULE_NU);
       onCreate(db);

       db.execSQL("drop table if exists " + TABLE_SCHEDULE_DE);
       onCreate(db);

       db.execSQL("drop table if exists " + TABLE_START_DATE);
       onCreate(db);

       db.execSQL("drop table if exists " + TABLE_GROUPS);
       onCreate(db);
   }
}
