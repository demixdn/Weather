
package com.example.data.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CloudsDTO {

    @SerializedName("all")
    @Expose
    private int all;

    public CloudsDTO() {
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CloudsDTO{");
        sb.append("all=").append(all);
        sb.append('}');
        return sb.toString();
    }
}
