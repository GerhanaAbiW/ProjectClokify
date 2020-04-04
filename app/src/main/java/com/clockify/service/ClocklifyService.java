package com.clockify.service;

import com.clockify.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClocklifyService {
    public static Retrofit getRetrofit() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        OkHttpClient client = httpBuilder
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new ResponseInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static <T> T create(Class<T> className) {
        return getRetrofit().create(className);
    }
}
