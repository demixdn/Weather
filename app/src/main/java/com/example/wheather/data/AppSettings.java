package com.example.wheather.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wheather.WeatherApp;
import com.example.wheather.data.mapper.DataConst;

/**
 * Created by Aleksandr on 23.07.2016 in Weather.
 */
public class AppSettings {
    private static final String SETTINGS_FILE = "SettingsFile";
    private static final String CONFIG_WIFI = "config_wifi"; //true or false
    private static final String CONFIG_UPDATE_START = "config_update_start"; //true or false
    private static final String CONFIG_MESSAGE = "config_message"; //true or false
    private static final String CONFIG_PERIOD_HOUR = "config_period_hour"; //string key
    private static final String CONFIG_AUTO_LOCATION = "config_location"; //true or false
    private static final String DATA_CITY_ID = "config_city_id"; //int key
    private static final String DATA_CITY_NAME = "config_city_name"; //string key
    private static final String DATA_COORD_LAT = "data_coord_lat"; //double
    private static final String DATA_COORD_LON = "data_coord_lon"; //double

    private static SharedPreferences preferences = WeatherApp.getInstance().getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE);

    public static boolean isUpdateOnWifi(){
        return preferences.getBoolean(CONFIG_WIFI, false);
    }

    public static boolean isUpdateOnStart(){
        return preferences.getBoolean(CONFIG_UPDATE_START, true);
    }

    public static boolean isShowOnExit(){
        return preferences.getBoolean(CONFIG_MESSAGE, true);
    }

    public static boolean isAutoLocation(){
        return preferences.getBoolean(CONFIG_AUTO_LOCATION, true);
    }

    public static String periodHour(){
        return preferences.getString(CONFIG_PERIOD_HOUR, DataConst.UpdateConfig.SIX_HOUR_KEY);
    }

    public static int cityID(){
        return preferences.getInt(DATA_CITY_ID, 0);
    }

    public static String cityName(){
        return preferences.getString(DATA_CITY_NAME, null);
    }

    public static double getCoordLat(){
        return Double.parseDouble(preferences.getString(DATA_COORD_LAT,"0.0"));
    }

    public static void setCoordLat(double value){
        preferences.edit().putString(DATA_COORD_LAT, String.valueOf(value)).apply();
    }

    public static double getCoordLon(){
        return Double.parseDouble(preferences.getString(DATA_COORD_LON,"0.0"));
    }

    public static void setCoordLon(double value){
        preferences.edit().putString(DATA_COORD_LON, String.valueOf(value)).apply();
    }

    public static void setConfigWifi(boolean value){
        preferences.edit().putBoolean(CONFIG_WIFI, value).apply();
    }

    public static void setConfigUpdateStart(boolean value){
        preferences.edit().putBoolean(CONFIG_UPDATE_START, value).apply();
    }

    public static void setConfigMessage(boolean value){
        preferences.edit().putBoolean(CONFIG_MESSAGE, value).apply();
    }

    public static void setConfigAutoLocation(boolean value){
        preferences.edit().putBoolean(CONFIG_AUTO_LOCATION, value).apply();
    }

    public static void setConfigPeriodHour(String value){
        preferences.edit().putString(CONFIG_PERIOD_HOUR, value).apply();
    }

    public static void setCityId(int value){
        preferences.edit().putInt(DATA_CITY_ID, value).apply();
    }

    public static void setCityName(String value){
        preferences.edit().putString(DATA_CITY_NAME, value).apply();
    }

}
