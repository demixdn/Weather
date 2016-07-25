package com.example.wheather.data.sync;

import android.content.Context;

import com.example.data.model.dao.WeatherDAO;
import com.example.wheather.data.AppSettings;
import com.example.wheather.data.DataManager;
import com.example.wheather.data.mapper.DataConst;
import com.example.wheather.data.utils.LogUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aleksandr on 20.04.2016 in teleport-android.
 */
public class SyncDataHandler {
    private Subscription subscription;
    private Context context;

    public SyncDataHandler(Context context) {
        this.context = context;
    }


    public void start() {
        int time = DataConst.UpdateConfig.valueHourBy(AppSettings.periodHour());
        subscription = Observable.interval(time, TimeUnit.SECONDS, Schedulers.io())
                .flatMap(aLong1 -> DataManager.getInstance().loadFromServer(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::doOnNext, this::doOnError);
    }

    private void doOnNext(List<WeatherDAO> response) {
        this.stop();
    }

    private void doOnError(Throwable throwable){
        this.stop();
    }

    public void stop() {
        if(AppSettings.isUpdateOnStart()) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                LogUtils.I("SyncDataHandler", "stopped");
                subscription.unsubscribe();
            }
        }
    }

}
