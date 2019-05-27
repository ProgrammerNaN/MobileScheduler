package com.example.teleg.programm.serverSide.Schedule;

import com.google.gson.annotations.SerializedName;

public class Settings {
    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("isNumerator")
    private Boolean isNumerator;

    public Settings(){

    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsNumerator() {
        return isNumerator;
    }


}
