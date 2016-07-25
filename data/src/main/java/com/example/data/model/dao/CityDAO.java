package com.example.data.model.dao;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.example.data.repository.local.database.DBConst;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class CityDAO {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    public CityDAO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    public String insertString(){
        return String.format(DBConst.QUERY.INSERT_CITY, id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityDAO city = (CityDAO) o;

        return id == city.id && (name != null ? name.equals(city.name) : city.name == null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
//        final StringBuilder sb = new StringBuilder("CityDAO{");
//        sb.append("id=").append(id);
//        sb.append(", name='").append(name).append('\'');
//        sb.append('}');
//        return sb.toString();
        return String.valueOf(name);
    }
}
