package com.example.teleg.programm;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.teleg.programm.DBControllers.NewsDB;
import com.example.teleg.programm.DBControllers.ScheduleDB;
import com.example.teleg.programm.serverSide.Api.ClientJson;
import com.example.teleg.programm.serverSide.Api.RsreuApi;
import com.example.teleg.programm.serverSide.Schedule.Lesson;
import com.example.teleg.programm.serverSide.Schedule.TimeCalculated;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class Users_Activity extends AppCompatActivity {
    private ScheduleDB scheduleDB;


    final Context context = this;
    private TextView final_text;
    SharedPreferences mSettings;

    private List<Lesson> numerator;
    private List<Lesson> denominator;
    private List<Lesson> currentListLessons;
    private final Calendar startSem = new GregorianCalendar();
    private Date date;
    private Calendar novaday;

    private RsreuApi api = ClientJson.getInstance().getApi();
    SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");


    private static final String TAG = "User_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);

        scheduleDB = new ScheduleDB(this);

        numerator = new ArrayList<>();
        denominator = new ArrayList<>();

        mSettings = getSharedPreferences("key_gallery_name", Context.MODE_PRIVATE);
    }



    public void active(View view) {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Users_Activity.this);
        String group = sharedPreferences.getString("key_gallery_name", "");

        assert group != null;
        if(group.length()<1) {
            LayoutInflater li = LayoutInflater.from(context);

            View promptsView = li.inflate(R.layout.dialog_group, null);

            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
            mDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
            mDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences.Editor edit = sharedPreferences.edit();
                                    edit.putString("key_gallery_name", String.valueOf(userInput.getText()));
                                    edit.apply();

                                    loadSchedule(String.valueOf(userInput.getText()));


                                    Intent intent = new Intent(Users_Activity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("Отмена",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = mDialogBuilder.create();
            alertDialog.show();
       } else
       {
            Intent intent = new Intent(Users_Activity.this, MainActivity.class);
            startActivity(intent);
       }
    }


    public void registration(View view) {
        Intent intent = new Intent(Users_Activity.this, Registration.class);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG,"onConfigurationChanged: orientation = portrait");
        }else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.i(TAG,"onConfigurationChanged: orientation = landscape");
        }
    }


    public void loadSchedule(String str)
    {
        SQLiteDatabase database = scheduleDB.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ScheduleDB.KEY_GROUPITEM, str);
        database.insert(ScheduleDB.TABLE_GROUPS, null, cv);
        cv.clear();


        api.getSettings()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(exception -> System.err.println("The error message is: " +  exception.getMessage()))
                .subscribe(settings -> {
                    ContentValues cv2 = new ContentValues();
                    cv2.clear();
                    //startSem.setTime(sdf.parse(settings.getStartDate()));
                    cv2.put(ScheduleDB.KEY_DATESTART, settings.getStartDate());
                    database.insert(ScheduleDB.TABLE_START_DATE, null, cv2);
                    cv2.clear();
                });


        api.getRasp(str)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(posts -> {
                    ContentValues cv3 = new ContentValues();
                    cv3.clear();
                    numerator.addAll(posts.getNumerator());
                    for (Lesson item : numerator) {
                        cv3.put(ScheduleDB.KEY_TITLE, item.getTitle());
                        cv3.put(ScheduleDB.KEY_TYPE, item.getType());
                        cv3.put(ScheduleDB.KEY_LOCATION, item.getRoom());
                        cv3.put(ScheduleDB.KEY_TEACHER, item.getTeachers());
                        cv3.put(ScheduleDB.KEY_DATE, item.getWeekDay());
                        cv3.put(ScheduleDB.KEY_TIME, item.getTimeId());
                        cv3.put(ScheduleDB.KEY_GROUP, str);
                        database.insert(ScheduleDB.TABLE_SCHEDULE_NU, null, cv3);
                    }
                    Log.d("Числитель", numerator.toString());
                    numerator.clear();
                    denominator.addAll(posts.getDenominator());
                    cv3.clear();
                    for (Lesson item : denominator) {
                        cv3.put(ScheduleDB.KEY_TITLE, item.getTitle());
                        cv3.put(ScheduleDB.KEY_TYPE, item.getType());
                        cv3.put(ScheduleDB.KEY_LOCATION, item.getRoom());
                        cv3.put(ScheduleDB.KEY_TEACHER, item.getTeachers());
                        cv3.put(ScheduleDB.KEY_DATE, item.getWeekDay());
                        cv3.put(ScheduleDB.KEY_TIME, item.getTimeId());
                        cv3.put(ScheduleDB.KEY_GROUP, str);
                        database.insert(ScheduleDB.TABLE_SCHEDULE_DE, null, cv3);
                    }
                    Log.d("Знаменатель", denominator.toString());
                    denominator.clear();
                });
    }
}
