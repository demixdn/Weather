package com.example.data.net;

import android.support.annotation.NonNull;

import com.example.data.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class ApiModule {
    @NonNull
    private final String baseUrl;
    @NonNull
    private final Gson gson;
    @NonNull
    private final OkHttpClient.Builder httpClientBuilder;

    public ApiModule(@NonNull String baseUrl){
        this.baseUrl = baseUrl;

        httpClientBuilder = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(ApiConst.PARAMS.LOG_LEVEL);
        if(BuildConfig.DEBUG)
            httpClientBuilder.addInterceptor(logging);
    }

    @NonNull
    public WeatherAPI provideApi()
    {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClientBuilder.build())
                .build()
                .create(WeatherAPI.class);
    }
}
