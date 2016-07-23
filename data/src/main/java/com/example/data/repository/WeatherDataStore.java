package com.example.data.repository;

import com.example.data.model.Weather;
import com.example.data.model.dao.WeatherDAO;

import java.util.List;

import rx.Observable;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 * Interface that represents a data store from where data is retrieved.
 */
public interface WeatherDataStore {
    /**
     * Get an {@link rx.Observable} which will emit a List of {@link Weather}.
     * @param cityId The id to retrieve weather data.
     * @param startPeriod The begin point of data, in seconds
     * @param endPeriod The end point of data, in seconds
     * @param useRu if locale is ru
     * @param useNightIcon if night
     * @param limit Limit to retrieve weather items
     */
    Observable<List<Weather>> getWeathersBy(int cityId, int startPeriod, int endPeriod,
                                            boolean useRu, boolean useNightIcon, int limit);

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link Weather}.
     * @param lon The latitude to retrieve weather data.
     * @param lat The longitude to retrieve weather data.
     * @param startPeriod The begin point of data, in seconds
     * @param endPeriod The end point of data, in seconds
     * @param useRu if locale is ru
     * @param useNightIcon if night
     * @param limit Limit to retrieve weather items
     */
    Observable<List<Weather>> getWeathersBy(int lon, int lat, int startPeriod, int endPeriod,
                                            boolean useRu, boolean useNightIcon, int limit);

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link Weather}.
     * @param offset start number of item with historical data. Default 0
     * @param limit count of items with historical data
     */
    Observable<List<Weather>> getHistoryWeathers(int offset, int limit, boolean useRu);

    /**
     * Use only for tests
     */
    @Deprecated
    Observable<List<WeatherDAO>> getAllWeathers();
}
