package com.clockify.service;

import com.clockify.EmptyResponse;
import com.clockify.Model.ActivityModel;
import com.clockify.Model.GetToken;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CreateActivity {
    @FormUrlEncoded
    @POST("timers/")
    Call<Void> createActivity(@Field("start_timer") Date start_timer, @Field("stop_timer") Date stop_timer, @Field("activity") String activity, @Field("location") String location);
}
