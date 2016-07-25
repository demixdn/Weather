package com.example.wheather.data.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Aleksandr on 23.07.2016 in Weather.
 */
public class CheckConnection {
    public static boolean isWiFiConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetwork != null && wifiNetwork.isConnected();
    }

    public static boolean isMobileConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return wifiNetwork != null && wifiNetwork.isConnected();
    }

    public static boolean isActiveConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
    }
}
