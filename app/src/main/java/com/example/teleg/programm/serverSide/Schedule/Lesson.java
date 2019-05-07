package com.example.teleg.programm.serverSide.Schedule;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Lesson {
    @SerializedName("weekDay")
    private int weekDay;

    @SerializedName("timeId")
    private int timeId;

    @SerializedName("duration")
    private int duration;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private String type;

    @SerializedName("optional")
    private int optional;

    @SerializedName("teachers")
    private String teachers;

    @SerializedName("room")
    private String room;

    @SerializedName("build")
    private String build;

    @SerializedName("dates")
    private String dates;

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOptional() {
        return optional;
    }

    public void setOptional(int optional) {
        this.optional = optional;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public static final Comparator<Lesson> COMPARE_BY_COUNT = new Comparator<Lesson>() {
        @Override
        public int compare(Lesson lhs, Lesson rhs) {
            return lhs.getTimeId() - rhs.getTimeId();
        }
    };

}
