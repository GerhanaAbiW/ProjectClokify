package com.clockify;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserDefault {
    private static  UserDefault userDefaultInstance;
    private SharedPreferences pref;
    private  SharedPreferences.Editor editor;

    public  static  final  String TOKEN_KEY = "token";

    public  static  UserDefault getInstance(){
        if(userDefaultInstance == null){
            userDefaultInstance = new UserDefault();
        }
        return userDefaultInstance;
    }

    private UserDefault() {
        pref = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getAppContext());
        editor = pref.edit();
    }
    public  void setString(String key, String val){
        editor.putString(key, val);
        editor.apply();
    }
    public  void setInt(String key, int val){
        editor.putInt(key, val);
        editor.apply();
    }

    public String getString(String key){
        return pref.getString(key,null);
    }
//    public String getInt(int key){
//        return pref.getInt();
//    }

    public void remove(String key){
        editor.remove(key);
        editor.apply();
    }

    public void clear(){
        pref.edit().clear().apply();
    }
}
