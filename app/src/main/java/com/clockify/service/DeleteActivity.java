package com.clockify.service;

import com.clockify.EmptyResponse;
import com.clockify.Model.ActivityModel;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Path;

public interface DeleteActivity {

    @DELETE("timers/{id}")
    Call<Void> delData(@Path("id") int id);
}
