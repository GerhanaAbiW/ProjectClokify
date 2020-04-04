package com.clockify.service;

import com.clockify.Model.ActivityModel;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Path;

public interface DeleteActivity {
    @FormUrlEncoded
    @DELETE("timers/")
    Call<ActivityModel> delData(@Path("id") String id);
}
