package com.example.teleg.programm.CalendarClasses.utils;

import com.example.teleg.programm.CalendarClasses.model.CalendarEvent;

import java.util.Calendar;
import java.util.List;


public interface CalendarEventsPredicate {

    List<CalendarEvent> events(Calendar date);
}
