package fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.teleg.programm.R;
import com.example.teleg.programm.serverSide.Schedule.Lesson;
import com.example.teleg.programm.serverSide.Schedule.PostModel;
import com.example.teleg.programm.serverSide.Schedule.PostsAdapter;
import com.example.teleg.programm.serverSide.Schedule.RaspApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScheduleFragmentMain extends Fragment implements Callback<PostModel> {

    static final String BASE_URL = "http://www.rsreu.ru/";

    RecyclerView recyclerView;
    TextView typeWeek;
    PostModel posts;
    List<Lesson> numerator;
    List<Lesson> denominator;
    List<Lesson> currentListLessons;
    final Calendar startSem = new GregorianCalendar();

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

        startSem.set(Calendar.YEAR, 2019);
        startSem.set(Calendar.MONTH, 1);
        startSem.set(Calendar.DAY_OF_MONTH, 11);

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
        currentListLessons = new ArrayList<>();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RaspApi raspApi = retrofit.create(RaspApi.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String group = sharedPreferences.getString("key_gallery_name", "");

        Call<PostModel> call = raspApi.getRasp(group);
        call.enqueue(this);




        //horizontalCalendar.selectDate(defaultSelectedDate, false);
       // horizontalCalendar.goToday(false);


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                currentListLessons.clear();
                Log.d("Days", Integer.toString((int)(daysBetween(startSem, date)/7)));
                if (((int)((daysBetween(startSem, date) + 1) / 7)) % 2 == 0) {
                    typeWeek.setText("Числитель");
                    for (Lesson lesson : numerator
                            ) {

                        if (date.get(Calendar.DAY_OF_WEEK) == lesson.getWeekDay() + 1) {
                            currentListLessons.add(lesson);
                        }
                    }
                    Collections.sort(currentListLessons, Lesson.COMPARE_BY_COUNT);

                    PostsAdapter adapter = new PostsAdapter(currentListLessons);

                    recyclerView.setAdapter(adapter);
                } else {
                    typeWeek.setText("Знаменатель");
                    for (Lesson lesson : denominator
                            ) {

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

    @Override
    public void onResponse(Call<PostModel> call, Response<PostModel> response) {
        if (response.isSuccessful()) {
            posts = response.body();
            numerator.addAll(posts.getNumerator());
            denominator.addAll(posts.getDenominator());


            Calendar novaday = Calendar.getInstance();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            currentListLessons.clear();
            if (((int)((daysBetween(startSem, novaday) + 1) / 7)) % 2 == 0) {
                typeWeek.setText("Числитель");
                for (Lesson lesson : numerator
                        ) {

                    if (novaday.get(Calendar.DAY_OF_WEEK) == lesson.getWeekDay() + 1) {
                        currentListLessons.add(lesson);
                        Log.d("Logging", currentListLessons.toString());
                    }
                }
            } else {
                typeWeek.setText("Знаменатель");
                for (Lesson lesson : denominator
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

    @Override
    public void onFailure(Call<PostModel> call, Throwable t) {
        Toast.makeText(getActivity(),
                "an error",
                Toast.LENGTH_SHORT).show();
    }

    public static long daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }
}

