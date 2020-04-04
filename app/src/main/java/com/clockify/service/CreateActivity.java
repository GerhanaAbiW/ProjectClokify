package com.clockify.service;

import com.clockify.Model.GetToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CreateActivity {
    @FormUrlEncoded
    @POST("timers/")
    Call<GetToken> createActivity(@Field("start_timer") String start_timer, @Field("activity") String activity, @Field("location") String location);
}
