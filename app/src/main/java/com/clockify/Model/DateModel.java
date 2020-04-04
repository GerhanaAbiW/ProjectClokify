package com.clockify.Model;

import com.google.gson.annotations.SerializedName;

public class DateModel implements ActivityContent {

    String Date;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public int getType() {
        return Type_Date;
    }
}
