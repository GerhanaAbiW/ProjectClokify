package com.clockify.service;

import com.clockify.UserDefault;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    public String token;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder().method(original.method(),original.body());

        token = UserDefault.getInstance().getString(UserDefault.TOKEN_KEY);
        if (token != null)
            builder.addHeader("token", token);
        return chain.proceed(builder.build());
    }
}
