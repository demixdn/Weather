package com.example.wheather.data;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.data.model.Weather;
import com.example.data.model.dao.WeatherDAO;
import com.example.data.net.OpenWeatherConnection;
import com.example.data.repository.WeatherDataRepository;
import com.example.data.repository.WeatherDataStore;
import com.example.wheather.R;
import com.example.wheather.WeatherApp;
import com.example.wheather.data.mapper.DataMapper;
import com.example.wheather.data.utils.CheckConnection;
import com.example.wheather.data.utils.LogUtils;
import com.example.wheather.view.AppConst;
import com.example.wheather.view.model.DayWeather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Aleksandr on 23.07.2016 in Weather.
 */
public class DataManager {
    private static DataManager instance = new DataManager();

    public static DataManager getInstance() {
        return instance;
    }

    private DayWeather firstDay;

    private List<DayWeather> weekItems;

    private List<DayWeather> historyItems;

    public DataManager(){

    }

    public void cleardata(){
        if(firstDay!=null)
            firstDay = null;
        if(weekItems!=null)
            weekItems.clear();
        if(historyItems!=null)
            historyItems.clear();
    }

    public DayWeather getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(DayWeather firstDay) {
        this.firstDay = firstDay;
    }

    public List<DayWeather> getWeekItems() {
        return weekItems;
    }

    public void setWeekItems(List<DayWeather> weekItems) {
        this.weekItems = weekItems;
    }

    public List<DayWeather> getHistoryItems() {
        return historyItems;
    }

    public void setHistoryItems(List<DayWeather> historyItems) {
        this.historyItems = historyItems;
    }

    public Observable<List<WeatherDAO>> loadFromServer(Context context){
        if(AppSettings.isUpdateOnWifi() && CheckConnection.isWiFiConnected(context)
                || !AppSettings.isUpdateOnWifi() && CheckConnection.isActiveConnected(context)) {
            OpenWeatherConnection connection = new OpenWeatherConnection(WeatherApp.getInstance().getDiskDataSource());
            if (AppSettings.isAutoLocation()) {
                double latitude = AppSettings.getCoordLat();
                double longitude = AppSettings.getCoordLon();
                return connection.forecastByCoords(String.valueOf(latitude), String.valueOf(longitude));
            } else if (AppSettings.cityID() > 0) {
                return connection.forecastByCityId(String.valueOf(AppSettings.cityID()));
            } else {
                return Observable.just(Collections.emptyList());
            }
        }else {
            return Observable.just(Collections.emptyList());
        }
    }

    /**
     * Get seconds from 1970... to begin of this day
     * @return seconds with UTC offset
     */
    private int getCurrentDayBeginUTC(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long offsetUTC = calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET);
        long currentUTC = calendar.getTimeInMillis() + offsetUTC;
        return (int)(currentUTC/1000L);
    }

    /**
     * Get seconds from 1970... to current time
     * @return seconds with UTC offset
     */
    public int getCurrentTimeUTC(){
        Calendar calendar = Calendar.getInstance();
        return getCalendarTimeUTC(calendar);
    }

    /**
     * Get seconds from 1970... to calendar time
     * @return seconds with UTC offset
     */
    private int getCalendarTimeUTC(Calendar calendar){
        long offsetUTC = calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET);
        long currentUTC = calendar.getTimeInMillis() + offsetUTC;
        return (int)(currentUTC/1000L);
    }

    public Observable<List<DayWeather>> getWeekWeathers(){
        WeatherDataStore dataStore = new WeatherDataRepository(WeatherApp.getInstance().getDiskDataSource());
        //init params
        int start = DataManager.getInstance().getCurrentDayBeginUTC();
        int end = DataManager.getInstance().getCurrentDayBeginUTC() + 5 * AppConst.SECONDS_IN_DAY;
        String lang = WeatherApp.getInstance().getString(R.string.lang);
        boolean useRu = lang.compareTo("ru") == 0;
        boolean useNight = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 20;
        LogUtils.E("Weather param :: "+start+" : "+end+" : "+useRu+" : "+useNight+";");
        //load data
        if(AppSettings.isAutoLocation() && AppSettings.cityID()==0) {
            double latitude = AppSettings.getCoordLat();
            double longitude = AppSettings.getCoordLon();
            if(latitude != 0 && longitude != 0)
                return dataStore.getWeathersBy(longitude, latitude, start, end, useRu, 0)
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(this::transformFrom);
            else
                return Observable.just(Collections.emptyList());
        } else if(AppSettings.cityID()>0){
            return dataStore.getWeathersBy(AppSettings.cityID(), start, end, useRu, 0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(this::transformFrom);
        } else {
            return Observable.just(Collections.emptyList());
        }
    }

    /**
     * It finds the first item, which is greater than a predetermined time
     * @param time predetermined time, in seconds
     * @param inList list of items
     * @return founded item or null
     */
    @Nullable
    public Weather findFirstNearBy(int time, List<Weather> inList){
        List<Weather> target = new ArrayList<>(inList);
        Collections.sort(target, (lhs, rhs) -> (int)(lhs.getDate() - rhs.getDate()));
        LogUtils.E(target.toString());
        for(Weather item:target)
            if(item.getDate()>=time)
                return item;
        return null;
    }

    private Observable<List<DayWeather>> transformFrom(List<Weather> listData){
        return Observable.create(new Observable.OnSubscribe<List<DayWeather>>() {
            @Override
            public void call(Subscriber<? super List<DayWeather>> subscriber) {
                try{
                    List<DayWeather> weathers = new ArrayList<DayWeather>();
                    Calendar calendar = Calendar.getInstance();
                    for(int i=0;i<5;i++){
                        int time = getCalendarTimeUTC(calendar);
                        DayWeather day = DataMapper.transformFrom(findFirstNearBy(time, listData));
                        if(day!=null) {
                            weathers.add(day);
                            if(i==0) setFirstDay(day);
                        }

                        calendar.add(Calendar.SECOND, AppConst.SECONDS_IN_DAY);
                        calendar.set(Calendar.HOUR_OF_DAY, 5);
                        calendar.set(Calendar.MINUTE, 59);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                    }
                    weekItems = weathers;
                    subscriber.onNext(weathers);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<List<DayWeather>> getHistory(int offset, int limit){
        String lang = WeatherApp.getInstance().getString(R.string.lang);
        boolean useRu = lang.compareTo("ru") == 0;
        WeatherDataStore dataStore = new WeatherDataRepository(WeatherApp.getInstance().getDiskDataSource());
        return dataStore.getHistoryWeathers(offset, limit, useRu)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::transformHistoryFrom);
    }

    private Observable<List<DayWeather>> transformHistoryFrom(List<Weather> listData){
        return Observable.create(new Observable.OnSubscribe<List<DayWeather>>() {
            @Override
            public void call(Subscriber<? super List<DayWeather>> subscriber) {
                try{
                    LogUtils.E(listData.toString());
                    List<DayWeather> weathers = new ArrayList<DayWeather>();
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.SECOND, AppConst.SECONDS_IN_DAY);
                    calendar.set(Calendar.HOUR_OF_DAY, 11);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    int count = listData.size()/8 + 1;
                    for(int i=0;i<count;i++){
                        calendar.setTimeInMillis(calendar.getTimeInMillis() - AppConst.SECONDS_IN_DAY * 1000);
                        int time = (int)(calendar.getTimeInMillis()/1000L);
                        DayWeather day = DataMapper.transformFrom(findFirstNearBy(time, listData));
                        if(day!=null) weathers.add(day);
                    }
                    historyItems = weathers;
                    subscriber.onNext(weathers);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

}
