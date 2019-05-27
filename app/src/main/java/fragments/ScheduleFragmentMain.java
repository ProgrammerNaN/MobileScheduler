package fragments;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teleg.programm.CalendarClasses.HorizontalCalendar;
import com.example.teleg.programm.CalendarClasses.utils.HorizontalCalendarListener;
import com.example.teleg.programm.DBControllers.NewsDB;
import com.example.teleg.programm.DBControllers.ScheduleDB;
import com.example.teleg.programm.R;
import com.example.teleg.programm.serverSide.Api.ClientJson;
import com.example.teleg.programm.serverSide.Api.RsreuApi;
import com.example.teleg.programm.serverSide.Schedule.Lesson;
import com.example.teleg.programm.serverSide.Schedule.PostModel;
import com.example.teleg.programm.serverSide.Schedule.PostsAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ScheduleFragmentMain extends Fragment {

    static final String BASE_URL = "http://www.rsreu.ru/";

    private ScheduleDB scheduleDB;

    RecyclerView recyclerView;
    TextView typeWeek;
    PostModel posts;
    List<Lesson> numerator;
    List<Lesson> denominator;
    List<Lesson> numeratorBD;
    List<Lesson> denominatorBD;
    List<Lesson> currentListLessons;
    final Calendar startSem = new GregorianCalendar();
    private Date date;
    private Calendar novaday;
    Cursor cursorDe;
    Cursor cursorNu;
    Cursor cursor;
    Cursor cursorGr;

    private RsreuApi api = ClientJson.getInstance().getApi();
    SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");

    //final Calendar defaultSelectedDate = Calendar.getInstance();


    public ScheduleFragmentMain() {
    }

    public static ScheduleFragmentMain newInstance() {
        return new ScheduleFragmentMain();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule_main, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.schedule_content_recyclerView);
        typeWeek = (TextView) rootView.findViewById(R.id.type_week);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        date = new Date();

        scheduleDB = new ScheduleDB(getContext());

        novaday = new GregorianCalendar();
        novaday.setTime(date);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);



        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .textSize(10f, 14f, 10f)
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .end()
                .defaultSelectedDate(Calendar.getInstance())
                .build();

        posts = new PostModel();
        numerator = new ArrayList<>();
        denominator = new ArrayList<>();
        numeratorBD = new ArrayList<>();
        denominatorBD = new ArrayList<>();
        currentListLessons = new ArrayList<>();


        SQLiteDatabase database = scheduleDB.getWritableDatabase();
        cursorDe = database.query(ScheduleDB.TABLE_SCHEDULE_DE, null, null, null, null, null, null);
        cursorNu = database.query(ScheduleDB.TABLE_SCHEDULE_NU, null, null, null, null, null, null);
        cursor = database.query(ScheduleDB.TABLE_START_DATE, null, null, null, null, null, null);
        cursorGr = database.query(ScheduleDB.TABLE_GROUPS, null, null, null, null, null, null);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String group = sharedPreferences.getString("key_gallery_name", "");




        Boolean checkDate = false;
        if (cursorGr.moveToFirst()) {
            int groupIndex = cursorGr.getColumnIndex(ScheduleDB.KEY_GROUPITEM);
            do{
                Log.d("Группа", cursorGr.getString(groupIndex));
                Log.d("Группа выбранная", group);
                if(cursorGr.getString(groupIndex).equals(group)) {
                    checkDate = true;
                    Log.d("Проверка условия", "Проверка успешна");
                break;
                }
                else {
                    Log.d("Проверка условия", "Проверка провалена");
                    //cursorGr.moveToNext();
                }
            }while (cursorGr.moveToNext());
        }

        if (!checkDate) {
            Log.d("Проверка условия", "Загрузка расписания");
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Log.d("Начало перед загрузкой", new Date().toString());

                loadSchedule(group);
                Log.d("Загрузка пройдена", new Date().toString());
            }
        } else {
            setListRasp(group);
        }

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {

            @Override
            public void onDateSelected(Calendar date, int position) {
                currentListLessons.clear();

                if (isNumerator(startSem, date)) {
                    typeWeek.setText("Числитель");
                    for (Lesson lesson : numeratorBD){
                        if (date.get(Calendar.DAY_OF_WEEK) == lesson.getWeekDay() + 1) {
                            currentListLessons.add(lesson);
                        }
                    }
                    Collections.sort(currentListLessons, Lesson.COMPARE_BY_COUNT);
                    //Log.d("Неделя", currentListLessons.toString());
                    PostsAdapter adapter = new PostsAdapter(currentListLessons);

                    recyclerView.setAdapter(adapter);
                } else {
                    typeWeek.setText("Знаменатель");
                    for (Lesson lesson : denominatorBD) {
                        if (date.get(Calendar.DAY_OF_WEEK) == lesson.getWeekDay() + 1) {
                            currentListLessons.add(lesson);
                        }
                    }
                    Collections.sort(currentListLessons, Lesson.COMPARE_BY_COUNT);
                    PostsAdapter adapter = new PostsAdapter(currentListLessons);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        return rootView;
    }

    public void loadSchedule(String str)
    {

        SQLiteDatabase database = scheduleDB.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ScheduleDB.KEY_GROUPITEM, str);
        database.insert(ScheduleDB.TABLE_GROUPS, null, cv);

        api.getSettings()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(exception -> System.err.println("The error message is: " +  exception.getMessage()))
                .subscribe(settings -> {
                    ContentValues cv2 = new ContentValues();
                    //startSem.setTime(sdf.parse(settings.getStartDate()));
                    cv2.put(ScheduleDB.KEY_DATESTART, settings.getStartDate());
                    database.insert(ScheduleDB.TABLE_START_DATE, null, cv2);
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
                        },

                        e -> {
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                setListRasp(str);
                            }
                        });
        Log.d("Метод LoadS", new Date().toString());
    }

    public boolean isNumerator(Calendar startSem, Calendar novaday) {
        return (novaday.get(Calendar.WEEK_OF_YEAR) - startSem.get(Calendar.WEEK_OF_YEAR)) % 2 == 0;
    }

    private void setListRasp(String group){
        String dateStart="";
        if(cursor.moveToFirst())
        {
            int startIndex = cursor.getColumnIndex(ScheduleDB.KEY_DATESTART);
            dateStart = cursor.getString(startIndex);
            Log.d("Начало семака", dateStart);
        }
        try{
            startSem.setTime(sdf.parse(dateStart));

        }
        catch (Exception e){
            e.printStackTrace();
        }

        Log.d("Начало отбора", new Date().toString());
        if (cursorNu.moveToFirst()) {
            int titleIndex = cursorNu.getColumnIndex(ScheduleDB.KEY_TITLE);
            int typeIndex = cursorNu.getColumnIndex(ScheduleDB.KEY_TYPE);
            int timeIndex = cursorNu.getColumnIndex(ScheduleDB.KEY_TIME);
            int locationIndex = cursorNu.getColumnIndex(ScheduleDB.KEY_LOCATION);
            int teacherIndex = cursorNu.getColumnIndex(ScheduleDB.KEY_TEACHER);
            int dateIndex = cursorNu.getColumnIndex(ScheduleDB.KEY_DATE);
            int groupIndex = cursorNu.getColumnIndex(ScheduleDB.KEY_GROUP);
            do {
                if(cursorNu.getString(groupIndex).equals(group)) {
                    Lesson item = new Lesson();
                    item.setTitle(cursorNu.getString(titleIndex));
                    item.setType(cursorNu.getString(typeIndex));
                    item.setTimeId(cursorNu.getString(timeIndex));
                    item.setType(cursorNu.getString(typeIndex));
                    item.setRoom(cursorNu.getString(locationIndex));
                    item.setTeachers(cursorNu.getString(teacherIndex));
                    item.setWeekDay(Integer.parseInt(cursorNu.getString(dateIndex)));
                    numeratorBD.add(item);
                }
            }
            while (cursorNu.moveToNext());
            Log.d("Числитель базы данных", numeratorBD.toString());
        } else {
            Log.d("База данных", "Пустотааааа");
        }

        if (cursorDe.moveToFirst()) {
            int titleIndex = cursorDe.getColumnIndex(ScheduleDB.KEY_TITLE);
            int typeIndex = cursorDe.getColumnIndex(ScheduleDB.KEY_TYPE);
            int timeIndex = cursorDe.getColumnIndex(ScheduleDB.KEY_TIME);
            int locationIndex = cursorDe.getColumnIndex(ScheduleDB.KEY_LOCATION);
            int teacherIndex = cursorDe.getColumnIndex(ScheduleDB.KEY_TEACHER);
            int dateIndex = cursorDe.getColumnIndex(ScheduleDB.KEY_DATE);
            int groupIndex = cursorDe.getColumnIndex(ScheduleDB.KEY_GROUP);
            do {
                Log.d("Группа пары", cursorDe.getString(groupIndex));
                if(cursorDe.getString(groupIndex).equals(group)) {
                    Lesson item = new Lesson();
                    item.setTitle(cursorDe.getString(titleIndex));
                    item.setType(cursorDe.getString(typeIndex));
                    item.setTimeId(cursorDe.getString(timeIndex));
                    item.setType(cursorDe.getString(typeIndex));
                    item.setRoom(cursorDe.getString(locationIndex));
                    item.setTeachers(cursorDe.getString(teacherIndex));
                    item.setWeekDay(Integer.parseInt(cursorDe.getString(dateIndex)));
                    denominatorBD.add(item);
                }
            }
            while (cursorDe.moveToNext());
            Log.d("Знаменатель базы данных", denominatorBD.toString());
        }
        else
        {
            Log.d("База данных", "Пустотааааа");
        }

        Calendar novaday = Calendar.getInstance();
        currentListLessons.clear();
        if (isNumerator(startSem, novaday)) {
            typeWeek.setText("Числитель");
            for (Lesson lesson : numeratorBD
            ) {

                if (novaday.get(Calendar.DAY_OF_WEEK) == lesson.getWeekDay() + 1) {
                    currentListLessons.add(lesson);
                    Log.d("Logging", currentListLessons.toString());
                }
            }
        } else {
            typeWeek.setText("Знаменатель");
            for (Lesson lesson : denominatorBD
            ) {

                if (novaday.get(Calendar.DAY_OF_WEEK) == lesson.getWeekDay() + 1) {
                    currentListLessons.add(lesson);
                    Log.d("Logging", currentListLessons.toString());
                }
            }
        }
        Collections.sort(currentListLessons, Lesson.COMPARE_BY_COUNT);
        PostsAdapter adapterNow = new PostsAdapter(currentListLessons);
        recyclerView.setAdapter(adapterNow);
    }
}

