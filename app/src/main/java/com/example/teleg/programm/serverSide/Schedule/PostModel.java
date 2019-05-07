package com.example.teleg.programm.serverSide.Schedule;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostModel {
    @SerializedName("numerator")
    private List<Lesson> numerator = null;

    @SerializedName("denominator")
    private List<Lesson> denominator = null;

    public List<Lesson> getNumerator() {
        return numerator;
    }

    public void setNumerator(List<Lesson> numerator) {
        this.numerator = numerator;
    }

    public List<Lesson> getDenominator() {
        return denominator;
    }

    public void setDenominator(List<Lesson> denominator) {
        this.denominator = denominator;
    }
}
