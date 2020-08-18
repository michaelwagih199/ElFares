package com.polimigo.elfares.service;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrenceHelper {

    private String PREF_NAME = "prefs";
    private SharedPreferences getPrefs(Context context)  {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

   public  void setReview(Context context ,boolean input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean("AppReview", input);
        editor.commit();
    }

    public boolean getReview(Context context) {
        return getPrefs(context).getBoolean("AppReview", false);
    }

}


