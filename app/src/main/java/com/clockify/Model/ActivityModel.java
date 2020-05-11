package com.clockify.Model;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ActivityModel{

    @SerializedName("id")
    public int id;
    @SerializedName("user_id")
    public  int user_id;
    @SerializedName("start_timer")
    public String start_timer;
    @SerializedName("stop_timer")
    public String stop_timer;
    @SerializedName("activity")
    public String activity;
    @SerializedName("location")
    public String location;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("updatedAt")
    public String updatedAt;

    public String getStopwatch() {
        return stopwatch;
    }

    public void setStopwatch(String stopwatch) {
        this.stopwatch = stopwatch;
    }

    String stopwatch;

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStart_timer() {
        return start_timer;
    }

    public void setStart_timer(String start_timer) {
        this.start_timer = start_timer;
    }

    public String getStop_timer() {
        return stop_timer;
    }

    public void setStop_timer(String stop_timer) {
        this.stop_timer = stop_timer;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static Comparator<ActivityModel> tanggal = new Comparator<ActivityModel>() {
        private SimpleDateFormat parser;
        private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyy", Locale.getDefault());
        public Date stringDateFormatter(String dateString) {
            Date date = null;
            if (parser == null) {
                parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            }
            parser.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
            try {
                date = parser.parse(dateString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return date;
        }
        @Override
        public int compare(ActivityModel o1, ActivityModel o2) {
            return dateFormat.format(stringDateFormatter(o1.getCreatedAt())).compareTo(dateFormat.format(stringDateFormatter(o2.getCreatedAt())));
            //o1.getCreatedAt().compareTo(o2.getCreatedAt());
        }
    };

}
