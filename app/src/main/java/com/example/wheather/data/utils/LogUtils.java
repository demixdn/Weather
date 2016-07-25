package com.example.wheather.data.utils;


import com.example.wheather.BuildConfig;

/**
 * Created by Aleksandr on 20.07.2016 in Wheather.
 */
public class LogUtils {

    private static final String TAG = BuildConfig.APPLICATION_ID;
    private static final boolean LOG_MODE = BuildConfig.DEBUG;

    public static void E(String msg)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty())
            android.util.Log.e(TAG, msg);
    }

    public static void E(String tag, String msg)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty())
            android.util.Log.e(tag, msg);
    }

    public static void E(String msg, boolean isShow)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty() && isShow)
            android.util.Log.e(TAG, msg);
    }

    public static void E(String tag, String msg, boolean isShow)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty() && isShow)
            android.util.Log.e(tag, msg);
    }

    public static void I(String msg)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty())
            android.util.Log.i(TAG, msg);
    }

    public static void I(String tag, String msg)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty())
            android.util.Log.i(tag, msg);
    }

    public static void I(String msg, boolean isShow)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty() && isShow)
            android.util.Log.i(TAG, msg);
    }

    public static void I(String tag, String msg, boolean isShow)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty() && isShow)
            android.util.Log.i(tag, msg);
    }

    public static void D(String msg)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty())
            android.util.Log.d(TAG, msg);
    }

    public static void D(String tag, String msg)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty())
            android.util.Log.d(tag, msg);
    }

    public static void D(String msg, boolean isShow)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty() && isShow)
            android.util.Log.d(TAG, msg);
    }

    public static void D(String tag, String msg, boolean isShow)
    {
        if(LOG_MODE && msg!=null && !msg.isEmpty() && isShow)
            android.util.Log.d(tag, msg);
    }
}
