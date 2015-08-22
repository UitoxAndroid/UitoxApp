package com.uitox.asapapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by babyandy on 2015/7/28.
 */
public class MyJsonObj {
    @SerializedName("chanel")
    private String chanelStr;

    @SerializedName("week")
    private String weekStr;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("DJ")
    private String djStr;

    public String getChanelStr() {
        return chanelStr;
    }

    public void setChanelStr(String chanelStr) {
        this.chanelStr = chanelStr;
    }

    public String getWeekStr() {
        return weekStr;
    }

    public void setWeekStr(String weekStr) {
        this.weekStr = weekStr;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDjStr() {
        return djStr;
    }

    public void setDjStr(String djStr) {
        this.djStr = djStr;
    }
}
