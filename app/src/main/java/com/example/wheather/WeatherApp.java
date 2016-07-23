package com.example.wheather;

import android.app.Application;

import com.example.data.repository.local.database.DiskDataSource;

/**
 * Created by Aleksandr on 20.07.2016 in Wheather.
 */
public class WeatherApp extends Application {

    private static WeatherApp instance;

    public static WeatherApp getInstance(){
        return instance;
    }

    private DiskDataSource diskDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        diskDataSource = new DiskDataSource(this);
    }

    public DiskDataSource getDiskDataSource() {
        return diskDataSource;
    }
}
