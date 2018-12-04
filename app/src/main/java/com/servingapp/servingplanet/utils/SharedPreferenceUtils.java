package com.servingapp.servingplanet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Abhjit on 30/11/2017.
 */

public class SharedPreferenceUtils {

    static String PREFERENCE_NAME = "coinOcloudPref";
    static String USER_ID = "userId";
    static String USER_NAME = "user_name";
    static String PASSWORD = "password";
    static String LOGIN_ID = "login_id";
    static String TOKEN = "token";
    static String SECURITY_PIN = "security_pin";
    static String DASHBOARD_OBJECT = "dashboard_object";

    public static void storeUserID(Context context, String userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit();
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public static String getUserId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return prefs.getString(USER_ID, null);
    }

    public static void storeUserName(Context context, String userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit();
        editor.putString(USER_NAME, userId);
        editor.commit();
    }

    public static String getUserName(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return prefs.getString(USER_NAME, null);
    }

    public static void clearAllPreferences(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit();
        editor.putString(USER_ID, null);
        editor.putString(LOGIN_ID, null);
        editor.putString(TOKEN, null);
        editor.putString(SECURITY_PIN, null);
        editor.commit();
    }

    public static void storePassword(Context context, String password) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit();
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public static String getPassword(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return prefs.getString(PASSWORD, null);
    }

    public static void storeLoginId(Context context, String loginId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit();
        editor.putString(LOGIN_ID, loginId);
        editor.commit();
    }

    public static String getLoginId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return prefs.getString(LOGIN_ID, null);
    }

    public static void storeToken(Context context, String loginId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit();
        editor.putString(TOKEN, loginId);
        editor.commit();
    }

    public static String getToken(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return prefs.getString(TOKEN, null);
    }

    public static void storeSecurityPin(Context context, String pin) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit();
        editor.putString(SECURITY_PIN, pin);
        editor.commit();
    }

    public static String getSecurityPin(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return prefs.getString(SECURITY_PIN, null);
    }

//    public static void storeDashboardObject(DashboardResponseModel model, Context context) {
//        if (context != null) {
//            SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit();
//            Gson gson = new Gson();
//            String json = gson.toJson(model);
//            editor.putString(DASHBOARD_OBJECT, json);
//            editor.apply();
//        }
//    }
//
//    public static DashboardResponseModel getDashboardObject(Context context) {
//        final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE_NAME, 0);
//        Gson gson = new Gson();
//        String json = prefs.getString(DASHBOARD_OBJECT, "");
//        return gson.fromJson(json, DashboardResponseModel.class);
//    }
}
