package com.example.teleg.programm;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.teleg.programm.DBControllers.NewsDB;
import com.example.teleg.programm.DBControllers.ScheduleDB;
import com.example.teleg.programm.serverSide.Api.ClientXml;
import com.example.teleg.programm.serverSide.Api.RsreuApi;
import com.example.teleg.programm.serverSide.News.ItemNews;
import com.example.teleg.programm.serverSide.News.RVAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class Boot_Activity extends Activity {

    private NewsDB newsDb;

    private RsreuApi api = ClientXml.getInstance().getApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boot);


        newsDb = new NewsDB(this);

        if ( !isOnline() ) {
            Toast toastY = Toast.makeText(getApplicationContext(),
                    "Нет соединения с интернетом!" +
                            "Данные могут быть устаревшими",Toast.LENGTH_LONG);
            toastY.setGravity(Gravity.TOP, 0,0);
            toastY.show();
        }
        else
        {
            Toast toastN = Toast.makeText(getApplicationContext(),
                    "Все произойдет, если только человек будет ждать и надеяться.     " +
                            " – Дизраэли Б.",Toast.LENGTH_LONG);
            toastN.setGravity(Gravity.TOP, 0,0);
            toastN.show();

            int SDK_INT = android.os.Build.VERSION.SDK_INT;
             if (SDK_INT > 8)
             {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
                StrictMode.setThreadPolicy(policy);

                dropNews();
                loadNews();
             }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Boot_Activity.this, Users_Activity.class);
                startActivity(i);
                finish();
            }
        }, (3000));
    }

    public boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        }
        else
        {
            return true;
        }
    }

    public  void dropNews() {

        SQLiteDatabase database = newsDb.getWritableDatabase();
        database.delete(NewsDB.TABLE_NEWS, null, null);
    }

    public void loadNews() {
        api.getItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feed -> {
                    List<ItemNews> news = feed.getChannel().getNewsList();
                    SQLiteDatabase database = newsDb.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    for (ItemNews item : news) {
                        cv.put(NewsDB.KEY_TITLE, item.getTitle() );
                        cv.put(NewsDB.KEY_DATE, item.getPubDate() );
                        cv.put(NewsDB.KEY_DESCRIPTION, item.getDescription() );
                        database.replace(NewsDB.TABLE_NEWS, null, cv);
                    }
                }, Throwable::printStackTrace);
    }
}