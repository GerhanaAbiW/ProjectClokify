package com.clockify.service;

import com.clockify.Model.ActivityModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Update {
    @FormUrlEncoded
    @PUT("timers/")
    Call<ActivityModel> updateUser(@Path("id") int id,
                                   @Field("activity") String activity, @Field("location") String location);
}
