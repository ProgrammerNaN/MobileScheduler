package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.teleg.programm.MainCalendarClasses.CustomCalendar;
import com.example.teleg.programm.MainCalendarClasses.Singleton;
import com.example.teleg.programm.MainCalendarClasses.dao.EventData;
import com.example.teleg.programm.MainCalendarClasses.dao.dataAboutDate;
import com.example.teleg.programm.MainCalendarClasses.utils.CalendarUtils;
import com.example.teleg.programm.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainCalendarFragment extends Fragment {

    private ViewGroup rootView;
    private CustomCalendar customCalendar;


    public MainCalendarFragment() {
    }

    public static MainCalendarFragment newInstance() {
        return new MainCalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calender, container, false);
        customCalendar = (CustomCalendar) rootView.findViewById(R.id.customCalendar);


        String[] arr = {"2019-05-06", "2019-05-04"};
        for (String anArr : arr) {
            int eventCount = 4;
            customCalendar.addAnEvent(anArr, eventCount, getEventDataList(eventCount));
        }



        return rootView;
    }


    public ArrayList getEventDataList(int count) {
        ArrayList eventDataList = new ArrayList();

        for (int i = 0; i < count; i++) {
            EventData dateData = new EventData();
            ArrayList dataAboutDates = new ArrayList();



            dateData.setSection(CalendarUtils.getNAMES()[new Random().nextInt(CalendarUtils.getNAMES().length)]);
            dataAboutDate dataAboutDate = new dataAboutDate();

            int index = new Random().nextInt(CalendarUtils.getEVENTS().length);

            dataAboutDate.setTitle(CalendarUtils.getEVENTS()[index]);
            dataAboutDate.setSubject(CalendarUtils.getEventsDescription()[index]);
            dataAboutDates.add(dataAboutDate);

            dateData.setData(dataAboutDates);
            eventDataList.add(dateData);
        }
        return eventDataList;
    }
}