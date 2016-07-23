package com.example.data.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.data.model.dao.WeatherDAO;
import com.example.data.model.dto.CityDTO;
import com.example.data.model.dto.ForecastResponse;
import com.example.data.model.dto.WeatherItemDTO;
import com.example.data.repository.local.database.DBConst;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class WeatherMapper {

    public static Observable<List<WeatherDAO>> transform(@NonNull ForecastResponse response){
        return Observable.create(new Observable.OnSubscribe<List<WeatherDAO>>() {
            @Override
            public void call(Subscriber<? super List<WeatherDAO>> subscriber) {
                try {
                    CityDTO cityDTO = response.getCity();
                    List<WeatherDAO> results = new ArrayList<>();
                    if(cityDTO!=null && response.getWeathersItems()!=null && response.getWeathersItems().size()>0){
                        for(WeatherItemDTO item:response.getWeathersItems())
                            results.add(transform(cityDTO, item));
                    }else {
                        subscriber.onError(new NullPointerException("ForecastResponse have null data"));
                    }
                    subscriber.onNext(results);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static List<WeatherDAO> transformList(@NonNull ForecastResponse response){
        CityDTO cityDTO = response.getCity();
        List<WeatherDAO> results = new ArrayList<>();
        if(cityDTO!=null && response.getWeathersItems()!=null && response.getWeathersItems().size()>0){
            for(WeatherItemDTO item:response.getWeathersItems())
                results.add(transform(cityDTO, item));
        }
        return results;
    }

    public static WeatherDAO transform(@NonNull CityDTO city, @NonNull WeatherItemDTO item){
        WeatherDAO result = new WeatherDAO();
        result.setCityId(city.getId());
        if(city.getCoord()!=null) {
            result.setCoordLat(city.getCoord().getLat());
            result.setCoordLon(city.getCoord().getLon());
        } else {
            throw new NullPointerException();
        }
        result.setDt(item.getDt());
        if(item.getMain()!=null) {
            result.setTemp(item.getMain().getTemp());
            result.setTempMin(item.getMain().getTempMin());
            result.setTempMax(item.getMain().getTempMax());
            result.setPressure(item.getMain().getPressure());
            result.setSeaLevel(item.getMain().getSeaLevel());
            result.setGrndLevel(item.getMain().getGrndLevel());
            result.setHumidity(item.getMain().getHumidity());
            result.setTempKf(item.getMain().getTempKf());
        } else {
            throw new NullPointerException();
        }
        if(item.getWeather()!=null && item.getWeather().get(0)!=null)
            result.setConditionId(item.getWeather().get(0).getId());
        else {
            throw new NullPointerException();
        }
        if(item.getClouds()!=null)
            result.setCloudsAll(item.getClouds().getAll());
        else {
            throw new NullPointerException();
        }
        if(item.getWind()!=null) {
            result.setWindSpeed(item.getWind().getSpeed());
            result.setWindDeg(item.getWind().getDeg());
        } else {
            throw new NullPointerException();
        }
        return result;
    }

    public static Weather transform(@NonNull Cursor cursor){
        Weather result = new Weather();
        result.setCityId(cursor.getInt(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.CITY_ID)));
        result.setCityName(cursor.getString(cursor.getColumnIndex(DBConst.AS.CITY_NAME)));
        result.setDesc(cursor.getString(cursor.getColumnIndex(DBConst.AS.COND_DESC)));
        result.setIcon(cursor.getString(cursor.getColumnIndex(DBConst.AS.COND_ICON)));
        int latIndex = cursor.getColumnIndex(DBConst.WEATHER_COLUMN.LAT);
        if(latIndex>=0)
            result.setLat(cursor.getDouble(latIndex));
        int lonIndex = cursor.getColumnIndex(DBConst.WEATHER_COLUMN.LON);
        if(lonIndex>=0)
            result.setLon(cursor.getDouble(lonIndex));
        result.setDate(cursor.getLong(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.DATE)));
        result.setTemp(cursor.getDouble(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.TEMP)));
        result.setTempMin(cursor.getInt(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.T_MIN)));
        result.setTempMax(cursor.getDouble(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.T_MAX)));
        result.setPressure(cursor.getDouble(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.PRESSURE)));
        result.setHumidity(cursor.getInt(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.HUMIDITY)));
        result.setWindSpeed(cursor.getDouble(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.WIND_SPEED)));
        return result;
    }
    public static WeatherDAO transformDAO(@NonNull Cursor cursor){
        WeatherDAO result = new WeatherDAO();
        result.setCityId(cursor.getInt(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.CITY_ID)));
        result.setCoordLat(cursor.getDouble(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.LAT)));
        result.setCoordLon(cursor.getDouble(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.LON)));
        result.setDt(cursor.getLong(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.DATE)));
        result.setTemp(cursor.getDouble(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.TEMP)));
        result.setTempMin(cursor.getInt(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.T_MIN)));
        result.setTempMax(cursor.getDouble(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.T_MAX)));
        result.setPressure(cursor.getDouble(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.PRESSURE)));
        result.setHumidity(cursor.getInt(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.HUMIDITY)));
        result.setWindSpeed(cursor.getDouble(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.WIND_SPEED)));
        result.setConditionId(cursor.getInt(cursor.getColumnIndex(DBConst.WEATHER_COLUMN.CONDITION_ID)));
        return result;
    }
}
