
package com.example.data.model.dto;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class WeatherItemDTO {

    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("main")
    @Expose
    private MainDTO main;
    @SerializedName("weather")
    @Expose
    private List<WeatherDTO> weather = new ArrayList<WeatherDTO>();
    @SerializedName("clouds")
    @Expose
    private CloudsDTO clouds;
    @SerializedName("wind")
    @Expose
    private WindDTO wind;
    @SerializedName("rain")
    @Expose
    private RainDTO rain;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    public WeatherItemDTO() {
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public MainDTO getMain() {
        return main;
    }

    public void setMain(MainDTO main) {
        this.main = main;
    }

    @Nullable
    public List<WeatherDTO> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDTO> weather) {
        this.weather = weather;
    }

    public CloudsDTO getClouds() {
        return clouds;
    }

    public void setClouds(CloudsDTO clouds) {
        this.clouds = clouds;
    }

    public WindDTO getWind() {
        return wind;
    }

    public void setWind(WindDTO wind) {
        this.wind = wind;
    }

    public RainDTO getRain() {
        return rain;
    }

    public void setRain(RainDTO rain) {
        this.rain = rain;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WeatherItemDTO{");
        sb.append("dt=").append(dt);
        sb.append(", main=").append(main);
        sb.append(", weather=").append(weather);
        sb.append(", clouds=").append(clouds);
        sb.append(", wind=").append(wind);
        sb.append(", rain=").append(rain);
        sb.append(", dtTxt='").append(dtTxt).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
