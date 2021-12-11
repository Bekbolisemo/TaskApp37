package com.example.taskapp37;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

public class Prefs {
    private SharedPreferences preferences;


    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

    }

    public void saveBoardState() {
        preferences.edit().putBoolean("isShown", true).apply();
    }
    public boolean isBoardShown(){
        return preferences.getBoolean("isShown",false);
    }
    public void saveUserName(String name) {
        preferences.edit().putString("isEmpty", name).apply();
    }

    public String getUserName() {
        return preferences.getString("isEmpty", null);
    }
    public void saveImageUser(Uri image){
        preferences.edit().putString("isEmptyImage", image.toString()).apply();
    }

    public String getImageUser(){
        return  preferences.getString("isEmptyImage","" );
    }


}
