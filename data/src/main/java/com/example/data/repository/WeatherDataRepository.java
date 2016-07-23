package com.example.data.repository;

import com.example.data.executor.JobExecutor;
import com.example.data.model.Weather;
import com.example.data.model.dao.WeatherDAO;
import com.example.data.repository.local.database.DiskDataSource;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class WeatherDataRepository implements WeatherDataStore {

    private DiskDataSource dataSource;

    public WeatherDataRepository(DiskDataSource dataSource){
        if(dataSource==null)
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        this.dataSource = dataSource;
    }

    @Override
    public Observable<List<Weather>> getWeathersBy(int cityId, int startPeriod, int endPeriod,
                                                   boolean useRu, boolean useNightIcon, int limit) {
        return loadByCity(cityId, dataSource, startPeriod, endPeriod, useRu, useNightIcon, limit)
                .subscribeOn(Schedulers.from(JobExecutor.getInstance()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Weather>> getWeathersBy(int lon, int lat, int startPeriod, int endPeriod,
                                                   boolean useRu, boolean useNightIcon, int limit) {
        return loadByCoords(lon, lat, dataSource, startPeriod, endPeriod, useRu, useNightIcon, limit)
                .subscribeOn(Schedulers.from(JobExecutor.getInstance()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Weather>> getHistoryWeathers(int offset, int limit, boolean useRu) {
        return loadByHistory(offset, limit, dataSource, useRu)
                .subscribeOn(Schedulers.from(JobExecutor.getInstance()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<WeatherDAO>> getAllWeathers() {
        return loadAll(dataSource)
                .subscribeOn(Schedulers.from(JobExecutor.getInstance()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<Weather>> loadByCity(int cityId,  DiskDataSource dataSource, int startPeriod, int endPeriod,
                                                 boolean useRu, boolean useNightIcon, int limit){
        return Observable.create(new Observable.OnSubscribe<List<Weather>>() {
            @Override
            public void call(Subscriber<? super List<Weather>> subscriber) {
                try {
                    List<Weather> weathers = dataSource.selectWeatherItemsBy(cityId, startPeriod, endPeriod, useRu, useNightIcon, limit);
                    subscriber.onNext(weathers);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observable<List<Weather>> loadByCoords(int lon, int lat, DiskDataSource dataSource, int startPeriod, int endPeriod,
                                                 boolean useRu, boolean useNightIcon, int limit){
        return Observable.create(new Observable.OnSubscribe<List<Weather>>() {
            @Override
            public void call(Subscriber<? super List<Weather>> subscriber) {
                try {
                    List<Weather> weathers = dataSource.selectWeatherItemsBy(lon, lat, startPeriod, endPeriod, useRu, useNightIcon, limit);
                    subscriber.onNext(weathers);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observable<List<Weather>> loadByHistory(int offset, int limit, DiskDataSource dataSource, boolean useRu){
        return Observable.create(new Observable.OnSubscribe<List<Weather>>() {
            @Override
            public void call(Subscriber<? super List<Weather>> subscriber) {
                try {
                    List<Weather> weathers = dataSource.selectWeatherItemsBy(offset, limit, useRu);
                    subscriber.onNext(weathers);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observable<List<WeatherDAO>> loadAll(DiskDataSource dataSource){
        return Observable.create(new Observable.OnSubscribe<List<WeatherDAO>>() {
            @Override
            public void call(Subscriber<? super List<WeatherDAO>> subscriber) {
                try {
                    List<WeatherDAO> weathers = dataSource.getAll();
                    subscriber.onNext(weathers);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
