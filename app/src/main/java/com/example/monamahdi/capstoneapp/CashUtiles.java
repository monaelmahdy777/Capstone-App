package com.example.monamahdi.capstoneapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mona.mahdi on 8/16/2018.
 */

public class CashUtiles {

    public static void setWeatherList(Context context, String weather) {
        try {
            System.out.println("Track : set the sessionID : " + weather);
            SharedPreferences.Editor Editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            Editor.putString("weather", weather).apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getWeatherList(Context context) {
        try {
            SharedPreferences Editor = PreferenceManager.getDefaultSharedPreferences(context);
            System.out.println("Track : ask for sessionID CashUtils : " + Editor.getString("sessionID", ""));
            return Editor.getString("weather", "");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static void setCityName(Context context, String city) {
        try {
            System.out.println("Track : set the sessionID : " + city);
            SharedPreferences.Editor Editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            Editor.putString("city", city).apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getCityName(Context context) {
        try {
            SharedPreferences Editor = PreferenceManager.getDefaultSharedPreferences(context);
            System.out.println("Track : ask for sessionID CashUtils : " + Editor.getString("sessionID", ""));
            return Editor.getString("city", "");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static void setTemp(Context context, String temp) {
        try {
            System.out.println("Track : set the sessionID : " + temp);
            SharedPreferences.Editor Editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            Editor.putString("temp", temp).apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getTemp(Context context) {
        try {
            SharedPreferences Editor = PreferenceManager.getDefaultSharedPreferences(context);
            System.out.println("Track : ask for sessionID CashUtils : " + Editor.getString("sessionID", ""));
            return Editor.getString("temp", "");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static void setRestaurantsList(Context context, String Restaurants) {
        try {
            System.out.println("Track : set the sessionID : " + Restaurants);
            SharedPreferences.Editor Editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            Editor.putString("Restaurants", Restaurants).apply();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getRestaurantsList(Context context) {
        try {
            SharedPreferences Editor = PreferenceManager.getDefaultSharedPreferences(context);
            System.out.println("Track : ask for sessionID CashUtils : " + Editor.getString("sessionID", ""));
            return Editor.getString("Restaurants", "");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
