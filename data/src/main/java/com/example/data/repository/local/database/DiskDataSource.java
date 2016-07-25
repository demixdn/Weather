package com.example.data.repository.local.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.data.model.Weather;
import com.example.data.model.WeatherMapper;
import com.example.data.model.dao.CityDAO;
import com.example.data.model.dao.WeatherDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class DiskDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper sqliteHelper;

    public DiskDataSource(Context context){
        sqliteHelper = new SQLiteHelper(context);
        open();
    }

    public void open(){
        open(0);
    }

    public void open(int attempt) {
        close();
        try {
            database = sqliteHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            if (attempt < 3)
                open(attempt+1);
        }

    }

    public void close() {
        sqliteHelper.close();
    }

    public void insertWeatherItem(@NonNull WeatherDAO item){
        database.execSQL(item.insertString());
    }

    public void insertListWeatherItems(@NonNull List<WeatherDAO> items){
        if(items.size()>0) {
            for (WeatherDAO item : items) {
                ContentValues insertValues = getWeatherContentValues(item);
                int id = (int) database.insertWithOnConflict(DBConst.TABLE.WEATHERS, null, insertValues, SQLiteDatabase.CONFLICT_IGNORE);
                if (id == -1) {
                    String where = DBConst.WEATHER_COLUMN.CITY_ID + " = ? AND " + DBConst.WEATHER_COLUMN.DATE + " = ?";
                    String[] params = new String[]{String.valueOf(item.getCityId()), String.valueOf(item.getDt())};
                    id = database.update(DBConst.TABLE.WEATHERS, insertValues, where, params);
                }
            }
        }
    }

    private ContentValues getWeatherContentValues(WeatherDAO item) {
        ContentValues insertValues = new ContentValues(16);
        insertValues.put(DBConst.WEATHER_COLUMN.CITY_ID, item.getCityId());
        insertValues.put(DBConst.WEATHER_COLUMN.CLOUDS, item.getCloudsAll());
        insertValues.put(DBConst.WEATHER_COLUMN.CONDITION_ID, item.getConditionId());
        insertValues.put(DBConst.WEATHER_COLUMN.DATE, item.getDt());
        insertValues.put(DBConst.WEATHER_COLUMN.GROUND_LEVEL, item.getGrndLevel());
        insertValues.put(DBConst.WEATHER_COLUMN.HUMIDITY, item.getHumidity());
        insertValues.put(DBConst.WEATHER_COLUMN.LAT, item.getCoordLat());
        insertValues.put(DBConst.WEATHER_COLUMN.LON, item.getCoordLon());
        insertValues.put(DBConst.WEATHER_COLUMN.PRESSURE, item.getPressure());
        insertValues.put(DBConst.WEATHER_COLUMN.SEA_LEVEL, item.getSeaLevel());
        insertValues.put(DBConst.WEATHER_COLUMN.T_MAX, item.getTempMax());
        insertValues.put(DBConst.WEATHER_COLUMN.T_MIN, item.getTempMin());
        insertValues.put(DBConst.WEATHER_COLUMN.TEMP, item.getTemp());
        insertValues.put(DBConst.WEATHER_COLUMN.TEMP_KF, item.getTempKf());
        insertValues.put(DBConst.WEATHER_COLUMN.WIND_DEG, item.getWindDeg());
        insertValues.put(DBConst.WEATHER_COLUMN.WIND_SPEED, item.getWindSpeed());
        return insertValues;
    }

    @NonNull
    public List<Weather> selectWeatherItemsBy(int cityId, int startPeriod, int endPeriod,
                                                 boolean useRu, int limit){
        String table = DBConst.TABLE.WEATHERS + " " +
                "INNER JOIN " + DBConst.TABLE.CITIES + " ON " +
                DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.CITY_ID + " = " + DBConst.TABLE.CITIES + "." + DBConst.CITY_COLUMN.ID + " " +
                "INNER JOIN " + DBConst.TABLE.CONDITIONS + " ON " +
                DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.CONDITION_ID + " = " + DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.ID;
        String columns[] = { DBConst.TABLE.CITIES + "." + DBConst.CITY_COLUMN.NAME + " AS " + DBConst.AS.CITY_NAME,
                DBConst.TABLE.CONDITIONS + "." + (useRu ? DBConst.CONDITION_COLUMN.DESC_RU : DBConst.CONDITION_COLUMN.DESC_EN) + " AS " + DBConst.AS.COND_DESC,
                DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.DAY_ICON,
                DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.NIGHT_ICON,
                DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.ID + " AS " +  DBConst.AS.COND_ID,
                DBConst.WEATHER_COLUMN.CITY_ID,
                DBConst.WEATHER_COLUMN.DATE,
                DBConst.WEATHER_COLUMN.TEMP,
                DBConst.WEATHER_COLUMN.T_MIN,
                DBConst.WEATHER_COLUMN.T_MAX,
                DBConst.WEATHER_COLUMN.PRESSURE,
                DBConst.WEATHER_COLUMN.HUMIDITY,
                DBConst.WEATHER_COLUMN.WIND_SPEED };
        String selection = DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.CITY_ID + " = ?" +
                " AND " + DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.DATE + " > ?" +
                " AND " + DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.DATE + " < ?";
        String[] selectionArgs = {String.valueOf(cityId), String.valueOf(startPeriod), String.valueOf(endPeriod)};
        String orderBy = DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.DATE + " ASC";
        String qlimit = limit <= 0 ? null : String.valueOf(limit);
        Cursor cursor = database.query(table, columns, selection, selectionArgs, null, null, orderBy, qlimit);
        List<Weather> results = new ArrayList<>();
        if(cursor!=null){
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()) {
                    Weather item = WeatherMapper.transform(cursor);
                    results.add(item);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return results;
    }

    @NonNull
    public List<Weather> selectWeatherItemsBy(double lon, double lat, int startPeriod, int endPeriod,
                                              boolean useRu, int limit){
        String table = DBConst.TABLE.WEATHERS + " " +
                "INNER JOIN " + DBConst.TABLE.CITIES + " ON " +
                DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.CITY_ID + " = " + DBConst.TABLE.CITIES + "." + DBConst.CITY_COLUMN.ID + " " +
                "INNER JOIN " + DBConst.TABLE.CONDITIONS + " ON " +
                DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.CONDITION_ID + " = " + DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.ID;
        String columns[] = { DBConst.TABLE.CITIES + "." + DBConst.CITY_COLUMN.NAME + " AS " + DBConst.AS.CITY_NAME,
                DBConst.TABLE.CONDITIONS + "." + (useRu ? DBConst.CONDITION_COLUMN.DESC_RU : DBConst.CONDITION_COLUMN.DESC_EN) + " AS " + DBConst.AS.COND_DESC,
                DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.DAY_ICON,
                DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.NIGHT_ICON,
                DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.ID + " AS " +  DBConst.AS.COND_ID,
                DBConst.WEATHER_COLUMN.CITY_ID,
                DBConst.WEATHER_COLUMN.LON,
                DBConst.WEATHER_COLUMN.LAT,
                DBConst.WEATHER_COLUMN.DATE,
                DBConst.WEATHER_COLUMN.TEMP,
                DBConst.WEATHER_COLUMN.T_MIN,
                DBConst.WEATHER_COLUMN.T_MAX,
                DBConst.WEATHER_COLUMN.PRESSURE,
                DBConst.WEATHER_COLUMN.HUMIDITY,
                DBConst.WEATHER_COLUMN.WIND_SPEED };
        String selection = DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.LON + " = ?" +
                " AND " + DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.LAT + " = ?" +
                " AND " + DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.DATE + " > ?" +
                " AND " + DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.DATE + " < ?";
        String[] selectionArgs = {String.valueOf(lon), String.valueOf(lat), String.valueOf(startPeriod), String.valueOf(endPeriod)};
        String orderBy = DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.DATE + " ASC";
        String qlimit = limit <= 0 ? null : String.valueOf(limit);
        Cursor cursor = database.query(table, columns, selection, selectionArgs, null, null, orderBy, qlimit);
        List<Weather> results = new ArrayList<>();
        if(cursor!=null){
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()) {
                    Weather item = WeatherMapper.transform(cursor);
                    results.add(item);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return results;
    }

    @NonNull
    public List<Weather> selectWeatherItemsBy(int offset, int limit, boolean useRu){
        String table = DBConst.TABLE.WEATHERS + " " +
                "INNER JOIN " + DBConst.TABLE.CITIES + " ON " +
                DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.CITY_ID + " = " + DBConst.TABLE.CITIES + "." + DBConst.CITY_COLUMN.ID + " " +
                "INNER JOIN " + DBConst.TABLE.CONDITIONS + " ON " +
                DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.CONDITION_ID + " = " + DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.ID;
        String columns[] = { DBConst.TABLE.CITIES + "." + DBConst.CITY_COLUMN.NAME + " AS " + DBConst.AS.CITY_NAME,
                DBConst.TABLE.CONDITIONS + "." + (useRu ? DBConst.CONDITION_COLUMN.DESC_RU : DBConst.CONDITION_COLUMN.DESC_EN) + " AS " + DBConst.AS.COND_DESC,
                DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.DAY_ICON,
                DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.NIGHT_ICON,
                DBConst.TABLE.CONDITIONS + "." + DBConst.CONDITION_COLUMN.ID + " AS " +  DBConst.AS.COND_ID,
                DBConst.WEATHER_COLUMN.CITY_ID,
                DBConst.WEATHER_COLUMN.LON,
                DBConst.WEATHER_COLUMN.LAT,
                DBConst.WEATHER_COLUMN.DATE,
                DBConst.WEATHER_COLUMN.TEMP,
                DBConst.WEATHER_COLUMN.T_MIN,
                DBConst.WEATHER_COLUMN.T_MAX,
                DBConst.WEATHER_COLUMN.PRESSURE,
                DBConst.WEATHER_COLUMN.HUMIDITY,
                DBConst.WEATHER_COLUMN.WIND_SPEED };
        String where = DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.DATE + " < ?";
        String[] args = new String[]{String.valueOf(System.currentTimeMillis()/1000L)};
        String orderBy = DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.DATE + " DESC";
        offset = offset < 0 ? 0 : offset;
        limit = limit <= 0 ? 100 : limit;
        String qlimit = String.valueOf(offset+","+limit);
        Cursor cursor = database.query(table, columns, where, args, null, null, orderBy, qlimit);
        List<Weather> results = new ArrayList<>();
        if(cursor!=null){
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()) {
                    Weather item = WeatherMapper.transform(cursor);
                    results.add(item);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return results;
    }

    @NonNull
    public List<WeatherDAO> getAll(){
        String orderBy = DBConst.TABLE.WEATHERS + "." + DBConst.WEATHER_COLUMN.DATE + " DESC";
        Cursor cursor = database.query(DBConst.TABLE.WEATHERS, null, null, null, null, null, orderBy, String.valueOf(1000));
        List<WeatherDAO> results = new ArrayList<>();
        if(cursor!=null){
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()) {
                    WeatherDAO item = WeatherMapper.transformDAO(cursor);
                    results.add(item);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return results;
    }

    @NonNull
    public List<CityDAO> getAllCities(){
        Cursor cursor = database.query(DBConst.TABLE.CITIES, null, null, null, null, null, null, null);
        List<CityDAO> results = new ArrayList<>();
        if(cursor!=null){
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()) {
                    CityDAO item = WeatherMapper.transformToCity(cursor);
                    results.add(item);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return results;
    }


}
