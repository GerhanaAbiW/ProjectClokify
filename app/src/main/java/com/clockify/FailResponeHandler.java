package com.clockify;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Response;
import com.google.gson.Gson;

import okhttp3.ResponseBody;

public class FailResponeHandler {
    public static void handleRespone(Context context, ResponseBody responseBody){
        if(responseBody != null){
            String errorString = "";
            try{
                errorString = responseBody.string();
                ErrorRespone errorRespone = new Gson().fromJson(errorString,ErrorRespone.class);
                Toast.makeText(context,errorRespone.message, Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
                if(responseBody.contentType().type().equals("text")&& responseBody.contentType().subtype().equals("plain")){
                    Toast.makeText(context,errorString,Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            if(context != null){
                Toast.makeText(context,"Server not Available, Please try again in a few moment.",Toast.LENGTH_LONG).show();
            }
        }
    }
    public static void  handlerErrorRespone(Context context, Throwable t){
        if(t.getCause()!=null){
            if(t.getCause().toString().contains("Network is unreachable")){
                Toast.makeText(context,"You are currently offline",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Error Server not Available, Please try again latter",Toast.LENGTH_LONG).show();
            }
        }else if(t.getLocalizedMessage()!=null){
            Log.e("localizedMessage",t.getLocalizedMessage());
        }else{
            if(context!=null){
                Toast.makeText(context,"Server not available, Please try again latter",Toast.LENGTH_LONG).show();
            }
        }
    }
}
