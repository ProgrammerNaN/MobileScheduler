package com.example.teleg.programm.MainCalendarClasses.dao;

import java.util.ArrayList;

public class Event {
    private String date;
    private String count;
    private ArrayList<EventData> eventData;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<EventData> getEventData() {
        return eventData;
    }

    public void setEventData(ArrayList<EventData> eventData) {
        this.eventData = eventData;
    }
}
