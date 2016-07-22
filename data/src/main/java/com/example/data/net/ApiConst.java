package com.example.data.net;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class ApiConst {
    public static final class URLS {
        public static final String BASE_URL = "http://api.openweathermap.org";
    }
    public static final class PARAMS {
        public static final String APPID = "1f0402f014d6e14ae3fe88f3b8971a82";
        public static final String UNIT_METRICS = "metric";
        public static final HttpLoggingInterceptor.Level LOG_LEVEL = HttpLoggingInterceptor.Level.BASIC;
    }
}
