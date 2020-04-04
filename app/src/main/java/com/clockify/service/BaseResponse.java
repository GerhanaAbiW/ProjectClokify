package com.clockify.service;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("msg")
    public String msg;

    @SerializedName("status")
    public int status;
}
