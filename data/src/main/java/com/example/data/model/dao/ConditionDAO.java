package com.example.data.model.dao;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.example.data.repository.local.database.DBConst;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class ConditionDAO {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("main_en")
    @Expose
    private String mainEn;
    @SerializedName("description_en")
    @Expose
    private String descriptionEn;
    @SerializedName("main_ru")
    @Expose
    private String mainRu;
    @SerializedName("description_ru")
    @Expose
    private String descriptionRu;
    @SerializedName("day_icon")
    @Expose
    private String dayIcon;
    @SerializedName("night_icon")
    @Expose
    private String nightIcon;

    public ConditionDAO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMainEn() {
        return mainEn;
    }

    public void setMainEn(String mainEn) {
        this.mainEn = mainEn;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getMainRu() {
        return mainRu;
    }

    public void setMainRu(String mainRu) {
        this.mainRu = mainRu;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getDayIcon() {
        return dayIcon;
    }

    public void setDayIcon(String dayIcon) {
        this.dayIcon = dayIcon;
    }

    public String getNightIcon() {
        return nightIcon;
    }

    public void setNightIcon(String nightIcon) {
        this.nightIcon = nightIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionDAO that = (ConditionDAO) o;

        return id == that.id && descriptionEn.equals(that.descriptionEn);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + descriptionEn.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConditionDAO{");
        sb.append("id=").append(id);
        sb.append(", mainEn='").append(mainEn).append('\'');
        sb.append(", descriptionEn='").append(descriptionEn).append('\'');
        sb.append(", mainRu='").append(mainRu).append('\'');
        sb.append(", descriptionRu='").append(descriptionRu).append('\'');
        sb.append(", dayIcon='").append(dayIcon).append('\'');
        sb.append(", nightIcon='").append(nightIcon).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    public String insertString() {
        return String.format(DBConst.QUERY.INSERT_CONDITION, id, mainEn, descriptionEn, mainRu, descriptionRu, dayIcon, nightIcon);
    }
}
