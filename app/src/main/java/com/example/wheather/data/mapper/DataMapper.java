package com.example.wheather.data.mapper;

import android.support.annotation.Nullable;

import com.example.data.model.Weather;
import com.example.wheather.view.model.DayWeather;

import java.util.Calendar;

/**
 * Created by Aleksandr on 24.07.2016 in Weather.
 */
public class DataMapper {
    @Nullable
    public static DayWeather transformFrom(Weather item){
        if(item==null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(item.getDate());
        boolean useNight = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 20;
        DayWeather day = new DayWeather(item.getDate(), item.getDesc(), item.getConditionId(),
                item.getTemp(), item.getTempMin(), item.getTempMax(), item.getWindSpeed(),
                item.getPressure(), item.getHumidity(),
                useNight?item.getNightIcon():item.getDayIcon());
        return day;
    }
}
