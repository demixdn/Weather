
package com.example.data.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RainDTO {

    @SerializedName("3h")
    @Expose
    private double rain3h;

    public RainDTO() {
    }

    public double getRain3h() {
        return rain3h;
    }

    public void setRain3h(double rain3h) {
        this.rain3h = rain3h;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RainDTO{");
        sb.append("rain3h=").append(rain3h);
        sb.append('}');
        return sb.toString();
    }
}
