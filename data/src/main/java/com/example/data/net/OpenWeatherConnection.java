package com.example.data.net;

import com.example.data.executor.JobExecutor;
import com.example.data.model.WeatherMapper;
import com.example.data.model.dao.WeatherDAO;
import com.example.data.repository.local.database.DiskDataSource;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class OpenWeatherConnection {

    private ApiModule apiModule;
    private DiskDataSource dataSource;

    public OpenWeatherConnection(DiskDataSource dataSource){
        if(dataSource==null)
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        this.apiModule = new ApiModule(ApiConst.URLS.BASE_URL);
        this.dataSource = dataSource;
    }


    public Observable<Boolean> forecastByCoords(@Query("lat") String lat, @Query("lon") String lon) {
        return apiModule.provideApi()
                .forecastByCoords(lat, lon, ApiConst.PARAMS.APPID, ApiConst.PARAMS.UNIT_METRICS)
                .subscribeOn(Schedulers.from(JobExecutor.getInstance()))
                .flatMap(WeatherMapper::transform)
                .flatMap(this::saveListToDB)
                .observeOn(Schedulers.io());
    }


    public Observable<Boolean> forecastByCityId(@Query("id") String cityId) {
        return apiModule.provideApi().forecastByCityId(cityId, ApiConst.PARAMS.APPID, ApiConst.PARAMS.UNIT_METRICS)
                .subscribeOn(Schedulers.from(JobExecutor.getInstance()))
                .flatMap(WeatherMapper::transform)
                .flatMap(this::saveListToDB)
                .observeOn(Schedulers.io());
    }

    private Observable<Boolean> saveListToDB(List<WeatherDAO> list){
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    dataSource.insertListWeatherItems(list);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
