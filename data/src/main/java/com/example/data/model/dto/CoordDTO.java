
package com.example.data.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CoordDTO {

    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("lat")
    @Expose
    private double lat;

    public CoordDTO() {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CoordDTO{");
        sb.append("lon=").append(lon);
        sb.append(", lat=").append(lat);
        sb.append('}');
        return sb.toString();
    }
}
