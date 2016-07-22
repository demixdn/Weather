package com.example.data.net;

import com.example.data.model.dto.ForecastResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public interface WeatherAPI {

    /**
     * Weather forecast for 5 days with data every 3 hours by geographic coordinates.
     * All weather data can be obtained in JSON.
     * @param lat latitude of device
     * @param lon longitude of device
     * @param appId always need use the value {@link ApiConst.PARAMS.APPID}
     * @param units always need use the value {@link ApiConst.PARAMS.UNIT_METRICS}
     * @return all server data in wrapper {@link ForecastResponse}
     */
    @SuppressWarnings("JavadocReference")
    @GET("data/2.5/forecast")
    Observable<ForecastResponse> forecastByCoords(@Query("lat") String lat,
                                                  @Query("lon") String lon,
                                                  @Query("appid") String appId,
                                                  @Query("units") String units);

    /**
     * Weather forecast for 5 days with data every 3 hours by city ID.
     * City ID search in database, original data in raw/cities.json.
     * API responds with exact result. All weather data can be obtained in JSON
     * @param id id of city
     * @param appId always need use the value {@link ApiConst.PARAMS.APPID}
     * @param units always need use the value {@link ApiConst.PARAMS.UNIT_METRICS}
     * @return all server data in wrapper {@link ForecastResponse}
     */
    @SuppressWarnings("JavadocReference")
    @GET("data/2.5/forecast")
    Observable<ForecastResponse> forecastByCityId(@Query("id") String cityId,
                                                  @Query("appid") String appId,
                                                  @Query("units") String units);
}
