package com.example.teleg.programm.MainCalendarClasses.utils;

import java.text.SimpleDateFormat;

public class CalendarUtils {
    private static final String TAG = CalendarUtils.class.getSimpleName();
    private static final String CALENDAR_DB_FORMAT = "yyyy-MM-dd";
    private static final String CALENDAR_DATE_FORMAT = "MMM dd yyyy";
    private static final String CALENDAR_MONTH_TITLE_FORMAT = "MMMM yyyy";
    private static final String[] NAMES = { "Денис", "Евгений"};
    private static final String[] EVENTS = {"Задание", "День рождения", "Событие"};
    private static final String[] EVENTS_DESCRIPTION = {"Доделать приложение", "Поздравить кого-нибудь с ДР", "Поехать на курсы"};

    public static SimpleDateFormat getCalendarDBFormat() {
        return new SimpleDateFormat(CALENDAR_DB_FORMAT);
    }

    public static SimpleDateFormat getCalendarDateFormat() {
        return new SimpleDateFormat(CALENDAR_DATE_FORMAT);
    }

    public static String getCalendarMonthTitleFormat(){
        return CALENDAR_MONTH_TITLE_FORMAT;
    }

    public static String[] getNAMES() {
        return NAMES;
    }

    public static String[] getEVENTS() {
        return EVENTS;
    }

    public static String[] getEventsDescription() {
        return EVENTS_DESCRIPTION;
    }
}
