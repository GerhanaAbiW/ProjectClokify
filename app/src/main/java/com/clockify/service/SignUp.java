package com.clockify.service;

import com.clockify.Model.GetToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SignUp {
    @FormUrlEncoded
    @POST("users/register")
    Call<GetToken> createAcc(@Field("name") String name,@Field("email") String email, @Field("password") String password);
}
