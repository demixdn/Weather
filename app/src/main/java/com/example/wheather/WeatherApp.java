package com.example.wheather;

import android.app.Application;

/**
 * Created by Aleksandr on 20.07.2016 in Wheather.
 */
public class WeatherApp extends Application {

    private static WeatherApp instance;

    public static WeatherApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
