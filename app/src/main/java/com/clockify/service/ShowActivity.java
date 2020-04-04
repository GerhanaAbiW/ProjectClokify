package com.clockify.service;

import com.clockify.Model.ActivityModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface ShowActivity {
    @FormUrlEncoded
    @GET("timers/")
    Call<List<ActivityModel>> ActivityList();
}
