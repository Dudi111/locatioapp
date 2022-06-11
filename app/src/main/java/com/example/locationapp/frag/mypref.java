package com.example.locationapp.frag;

import android.content.Context;
import android.content.SharedPreferences;

public class mypref {

    private static final String PREF_NAME = "details";
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private final int PRIVATE_MODE;
    private final Context _context;


    public mypref(Context context) {

        PRIVATE_MODE = 0;
        _context = context;

        if (_context != null) {
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();

        }

    }



    public Boolean isLoggedIn() {
        return pref.getBoolean("is_login", false);
    }

    public void loginInfo(Boolean login){
        editor.putBoolean("is_login", login);
        editor.commit();
    }

    public void setname(String mFlag) {
        editor.putString("is_login", mFlag);
        editor.commit();

    }

    public String getname() {
        return pref.getString("user_address", "");
    }

    public void setnumber(String mFlag) {
        editor.putString("is_login", mFlag);
        editor.commit();

    }

    public String getnumber() {
        return pref.getString("user_address", "");
    }


}
