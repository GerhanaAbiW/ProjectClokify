package com.clockify.Model;

import com.google.gson.annotations.SerializedName;
// model untuk menampung data offline(tanpa internet)
public class ModelOffline {

    public int id;

    public  int user_id;

    public String start_timer;

    public String stop_timer;

    public String activity;

    public String location;

    public String createdAt;

    public String updatedAt;

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
}
