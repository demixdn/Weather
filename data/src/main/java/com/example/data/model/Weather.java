package com.example.data.model;

import com.example.data.repository.local.database.DBConst;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class Weather {
    @SerializedName(DBConst.AS.CITY_NAME)
    @Expose
    private String cityName;

    @SerializedName(DBConst.AS.COND_DESC)
    @Expose
    private String desc;

    @SerializedName(DBConst.CONDITION_COLUMN.DAY_ICON)
    @Expose
    private String day_icon;

    @SerializedName(DBConst.CONDITION_COLUMN.NIGHT_ICON)
    @Expose
    private String night_icon;

    @SerializedName(DBConst.AS.COND_ID)
    @Expose
    private int conditionId;

    @SerializedName(DBConst.WEATHER_COLUMN.CITY_ID)
    @Expose
    private int cityId;

    @SerializedName(DBConst.WEATHER_COLUMN.LON)
    @Expose
    private double lon;

    @SerializedName(DBConst.WEATHER_COLUMN.LAT)
    @Expose
    private double lat;

    @SerializedName(DBConst.WEATHER_COLUMN.DATE)
    @Expose
    private long date;

    @SerializedName(DBConst.WEATHER_COLUMN.TEMP)
    @Expose
    private double temp;

    @SerializedName(DBConst.WEATHER_COLUMN.T_MIN)
    @Expose
    private double tempMin;

    @SerializedName(DBConst.WEATHER_COLUMN.T_MAX)
    @Expose
    private double tempMax;

    @SerializedName(DBConst.WEATHER_COLUMN.PRESSURE)
    @Expose
    private double pressure;

    @SerializedName(DBConst.WEATHER_COLUMN.HUMIDITY)
    @Expose
    private int humidity;

    @SerializedName(DBConst.WEATHER_COLUMN.WIND_SPEED)
    @Expose
    private double windSpeed;

    public Weather() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDayIcon() {
        return day_icon;
    }

    public void setDayIcon(String day_icon) {
        this.day_icon = day_icon;
    }

    public String getNightIcon() {
        return night_icon;
    }

    public void setNightIcon(String night_icon) {
        this.night_icon = night_icon;
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weather weather = (Weather) o;

        if (cityId != weather.cityId) return false;
        if (date != weather.date) return false;
        if (!cityName.equals(weather.cityName)) return false;
        return desc.equals(weather.desc);

    }

    @Override
    public int hashCode() {
        int result = cityName.hashCode();
        result = 31 * result + desc.hashCode();
        result = 31 * result + cityId;
        result = 31 * result + (int) (date ^ (date >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Weather{");
        sb.append("cityName='").append(cityName).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", d_icon='").append(day_icon).append('\'');
        sb.append(", n_icon='").append(night_icon).append('\'');
        sb.append(", date=").append(date);
        sb.append(", temp=").append(temp);
        sb.append(", tempMin=").append(tempMin);
        sb.append(", tempMax=").append(tempMax);
        sb.append(", pressure=").append(pressure);
        sb.append(", humidity=").append(humidity);
        sb.append(", windSpeed=").append(windSpeed);
        sb.append('}');
        return sb.toString();
    }
}
