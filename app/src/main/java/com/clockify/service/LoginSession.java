package com.clockify.service;

import com.clockify.Model.GetToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginSession {
    @FormUrlEncoded
    @POST("users/login")
    Call<GetToken> login(@Field("email") String email, @Field("password") String password);
}
