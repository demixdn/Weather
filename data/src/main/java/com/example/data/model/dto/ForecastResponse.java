package com.example.data.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class ForecastResponse {
    @SerializedName("city")
    @Expose
    private CityDTO city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private double message;
    @SerializedName("cnt")
    @Expose
    private int cnt;
    @SerializedName("list")
    @Expose
    private List<WeatherItemDTO> weathersItems= new ArrayList<WeatherItemDTO>();

    public ForecastResponse() {
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<WeatherItemDTO> getWeathersItems() {
        return weathersItems;
    }

    public void setWeathersItems(List<WeatherItemDTO> weathersItems) {
        this.weathersItems = weathersItems;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ForecastResponse{");
        sb.append("city=").append(city);
        sb.append(", cod='").append(cod).append('\'');
        sb.append(", message=").append(message);
        sb.append(", cnt=").append(cnt);
        sb.append(", weathersItems=").append(weathersItems);
        sb.append('}');
        return sb.toString();
    }
}
