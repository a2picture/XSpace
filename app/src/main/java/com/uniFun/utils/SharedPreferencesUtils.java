package com.uniFun.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jixiongxu on 2018/2/10.
 */

public class SharedPreferencesUtils
{
    private static String sharePreName = "myShare";

    public static void put(Context context, String key, boolean b)
    {
        SharedPreferences sp = context.getSharedPreferences(sharePreName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public static void put(Context context, String key, String value)
    {
        SharedPreferences sp = context.getSharedPreferences(sharePreName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void put(Context context, String key, float value)
    {
        SharedPreferences sp = context.getSharedPreferences(sharePreName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void put(Context context, String key, long value)
    {
        SharedPreferences sp = context.getSharedPreferences(sharePreName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharePreName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static boolean getBool(Context context, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharePreName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static int getInt(Context context, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharePreName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static float getFloat(Context context, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharePreName, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0f);
    }

    public static long getLong(Context context, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharePreName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

}
