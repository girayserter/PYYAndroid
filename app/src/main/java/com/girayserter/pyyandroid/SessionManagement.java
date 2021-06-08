package com.girayserter.pyyandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.girayserter.pyyandroid.models.Kullanici;

import java.util.HashMap;

public class SessionManagement {
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "KullaniciPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String PLAKA_SECIM="Secildi";
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "isim";

    // Phone address (make variable public to access from outside)
    public static final String KEY_PHONE = "telefon";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Pass address (make variable public to access from outside)
    public static final String KEY_PASS = "password";

    public static final String KEY_PLAKA= "plaka";


    public static final String Id= "id";
    public static final String Kullanici_adi="kullanici_adi";
    public static final String Ad= "ad";
    public static final String Soyad="soyad";
    public static final String Pozisyon="pozisyon";
    public static final String Yetki="yetki";

    public static Kullanici kullanici=new Kullanici();

    // Constructor
    public SessionManagement(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(Kullanici kullanici){
        // Storing login value as TRUE


        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(Kullanici_adi, kullanici.kullanici_adi);

        // Storing email in pref
        editor.putString(Ad, kullanici.ad);

        // Storing email in pref
        editor.putString(Soyad, kullanici.soyad);

        // Storing email in pref
        editor.putString(Pozisyon, kullanici.pozisyon);

        // Storing email in pref
        editor.putString(Yetki, kullanici.yetki);

        // Storing email in pref
        editor.putInt(Id, kullanici.id);

        // commit changes
        editor.commit();
    }


    /**
     * Get stored session data
     * */
    public Kullanici getUser(){

        kullanici.id=pref.getInt(Id,0);
        kullanici.kullanici_adi=pref.getString(Kullanici_adi,null);
        kullanici.ad=pref.getString(Ad,null);
        kullanici.soyad=pref.getString(Soyad,null);
        kullanici.pozisyon=pref.getString(Pozisyon,null);
        kullanici.yetki=pref.getString(Yetki,null);


        // return user
        return kullanici;
    }



    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, KullaniciGirisActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
            return false;
        }
        return true;

    }
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean plakaSecildiMi(){
        return pref.getBoolean(PLAKA_SECIM,false);
    }

    public boolean checkPlakaSecildi(){
        if(!this.plakaSecildiMi()){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, KullaniciGirisActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }
}
