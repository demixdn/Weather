package com.example.data.model.dao;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.example.data.repository.local.database.DBConst;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class WeatherDAO {
    @SerializedName("city_id")
    @Expose
    private int cityId;
    @SerializedName("coord_lon")
    @Expose
    private double coordLon;
    @SerializedName("coord_lat")
    @Expose
    private double coordLat;
    @SerializedName("dt")
    @Expose
    private long dt;
    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("temp_min")
    @Expose
    private double tempMin;
    @SerializedName("temp_max")
    @Expose
    private double tempMax;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("sea_level")
    @Expose
    private double seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private double grndLevel;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("temp_kf")
    @Expose
    private double tempKf;
    @SerializedName("condition_id")
    @Expose
    private int conditionId;
    @SerializedName("clouds_all")
    @Expose
    private int cloudsAll;
    @SerializedName("wind_speed")
    @Expose
    private double windSpeed;
    @SerializedName("wind_deg")
    @Expose
    private double windDeg;

    public WeatherDAO() {
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public double getCoordLon() {
        return coordLon;
    }

    public void setCoordLon(double coordLon) {
        this.coordLon = coordLon;
    }

    public double getCoordLat() {
        return coordLat;
    }

    public void setCoordLat(double coordLat) {
        this.coordLat = coordLat;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTempKf() {
        return tempKf;
    }

    public void setTempKf(double tempKf) {
        this.tempKf = tempKf;
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public int getCloudsAll() {
        return cloudsAll;
    }

    public void setCloudsAll(int cloudsAll) {
        this.cloudsAll = cloudsAll;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(double windDeg) {
        this.windDeg = windDeg;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    public String insertString(){
        return String.format(DBConst.QUERY.INSERT_WEATHER, getCityId(),
                String.valueOf(getCoordLon()).replace(",","."),
                String.valueOf(getCoordLat()).replace(",","."),
                getDt(),
                String.valueOf(getTemp()).replace(",","."),
                String.valueOf(getTempMin()).replace(",","."),
                String.valueOf(getTempMax()).replace(",","."),
                String.valueOf(getPressure()).replace(",","."),
                String.valueOf(getSeaLevel()).replace(",","."),
                String.valueOf(getGrndLevel()).replace(",","."),
                getHumidity(),
                String.valueOf(getTempKf()).replace(",","."),
                getConditionId(),
                getCloudsAll(),
                String.valueOf(getWindSpeed()).replace(",","."),
                String.valueOf(getWindDeg()).replace(",","."));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherDAO that = (WeatherDAO) o;
        return cityId == that.cityId && dt == that.dt;

    }

    @Override
    public int hashCode() {
        int result = cityId;
        result = 31 * result + (int)dt;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WeatherDAO{");
        sb.append("dt=").append(dt);
        sb.append(", temp=").append(temp);
        sb.append(", tempMin=").append(tempMin);
        sb.append(", tempMax=").append(tempMax);
        sb.append(", pressure=").append(pressure);
        sb.append(", humidity=").append(humidity);
        sb.append(", conditionId=").append(conditionId);
        sb.append(", windSpeed=").append(windSpeed);
        sb.append('}');
        return sb.toString();
    }
}
