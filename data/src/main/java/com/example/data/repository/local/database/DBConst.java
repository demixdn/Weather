package com.example.data.repository.local.database;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class DBConst {
    public static final class DATABASE {
        public static final String NAME = "weather_db.db";
        public static final int VERSION = 1;
        public static final String PREFS_KEY_DATABASE_VERSION = "database_version";
    }

    public static final class TABLE {
        public static final String CITIES = "Cities";
        public static final String CONDITIONS = "Conditions";
        public static final String WEATHERS = "Weathers";
    }

    public static final class CITY_COLUMN {
        public static final String ID = "id";                       //INTEGER PRIMARY KEY
        public static final String NAME = "name";                   //TEXT
    }

    public static final class CONDITION_COLUMN {
        public static final String ID = "id";                       //INTEGER PRIMARY KEY
        public static final String MAIN_EN = "main_en";             //TEXT
        public static final String DESC_EN = "description_en";      //TEXT
        public static final String MAIN_RU = "main_ru";             //TEXT
        public static final String DESC_RU = "description_ru";      //TEXT
        public static final String DAY_ICON = "day_icon";           //TEXT
        public static final String NIGHT_ICON = "night_icon";       //TEXT
    }

    public static final class WEATHER_COLUMN {
        public static final String CITY_ID = "city_id";             //INTEGER PART PRIMARY KEY -> FOREIGN KEY(city_id) REFERENCES Cities(id)
        public static final String LON = "coord_lon";               //REAL
        public static final String LAT = "coord_lat";               //REAL
        public static final String DATE = "dt";                     //INTEGER PART PRIMARY KEY
        public static final String TEMP = "temp";                   //REAL
        public static final String T_MIN = "temp_min";              //REAL
        public static final String T_MAX = "temp_max";              //REAL
        public static final String PRESSURE = "pressure";           //REAL
        public static final String SEA_LEVEL = "sea_level";         //REAL
        public static final String GROUND_LEVEL = "grnd_level";     //REAL
        public static final String HUMIDITY = "humidity";           //INTEGER
        public static final String TEMP_KF = "temp_kf";             //REAL
        public static final String CONDITION_ID = "condition_id";   //INTEGER -> FOREIGN KEY(condition_id) REFERENCES Conditions(id)
        public static final String CLOUDS = "clouds_all";           //REAL
        public static final String WIND_SPEED = "wind_speed";       //REAL
        public static final String WIND_DEG = "wind_deg";           //REAL
    }

    public static final class AS {
        public static final String CITY_NAME = "city_name";
        public static final String COND_DESC = "desc";
        public static final String COND_ICON = "icon";
        public static final String COND_ID = "con_id";
    }

    public static final class QUERY {

        //<editor-fold desc="CREATE_TABLE_CITY">
        public static final String CREATE_TABLE_CITY = "CREATE TABLE "
                + TABLE.CITIES
                + " ("
                + CITY_COLUMN.ID + " INTEGER PRIMARY KEY, "
                + CITY_COLUMN.NAME + " TEXT NOT NULL"
                + ");";
        //</editor-fold>

        //<editor-fold desc="CREATE_TABLE_CONDITION">
        public static final String CREATE_TABLE_CONDITION= "CREATE TABLE "
                + TABLE.CONDITIONS
                + " ("
                + CONDITION_COLUMN.ID + " INTEGER PRIMARY KEY, "
                + CONDITION_COLUMN.MAIN_EN + " TEXT, "
                + CONDITION_COLUMN.DESC_EN + " TEXT, "
                + CONDITION_COLUMN.MAIN_RU + " TEXT, "
                + CONDITION_COLUMN.DESC_RU + " TEXT, "
                + CONDITION_COLUMN.DAY_ICON + " TEXT, "
                + CONDITION_COLUMN.NIGHT_ICON + " TEXT "
                + ");";
        //</editor-fold>

        //<editor-fold desc="CREATE_TABLE_WEATHER">
        public static final String CREATE_TABLE_WEATHER = "CREATE TABLE "
                + TABLE.WEATHERS
                + " ("
                + WEATHER_COLUMN.CITY_ID + " INT, "
                + WEATHER_COLUMN.LON + " REAL, "
                + WEATHER_COLUMN.LAT + " REAL, "
                + WEATHER_COLUMN.DATE + " INTEGER, "
                + WEATHER_COLUMN.TEMP + " REAL, "
                + WEATHER_COLUMN.T_MIN + " REAL, "
                + WEATHER_COLUMN.T_MAX + " REAL, "
                + WEATHER_COLUMN.PRESSURE + " REAL, "
                + WEATHER_COLUMN.SEA_LEVEL + " REAL, "
                + WEATHER_COLUMN.GROUND_LEVEL + " REAL, "
                + WEATHER_COLUMN.HUMIDITY + " INTEGER, "
                + WEATHER_COLUMN.TEMP_KF + " REAL, "
                + WEATHER_COLUMN.CONDITION_ID + " INTEGER, "
                + WEATHER_COLUMN.CLOUDS + " INTEGER, "
                + WEATHER_COLUMN.WIND_SPEED + " REAL, "
                + WEATHER_COLUMN.WIND_DEG + " REAL, "
                + "PRIMARY KEY ("+ WEATHER_COLUMN.CITY_ID +", "+ WEATHER_COLUMN.DATE +") );";
        //</editor-fold>

        //<editor-fold desc="DROP TABLES">
        public static final String DROP_TABLE_CITY = "DROP TABLE IF EXISTS "+TABLE.CITIES+";";
        public static final String DROP_TABLE_CONDITION = "DROP TABLE IF EXISTS "+TABLE.CONDITIONS+";";
        public static final String DROP_TABLE_WEATHER = "DROP TABLE IF EXISTS "+TABLE.WEATHERS+";";
        //</editor-fold>

        //<editor-fold desc="INSERT ONE ITEM">
        public static final String INSERT_CITY = "INSERT INTO "+TABLE.CITIES+" VALUES (%1$d, '%2$s');";
        public static final String INSERT_CONDITION = "INSERT INTO "+TABLE.CONDITIONS
                +" VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s');";
        public static final String INSERT_WEATHER = "INSERT OR REPLACE INTO "+TABLE.WEATHERS
                +" VALUES (%d, %s, %s, %d, %s, %s, %s, %s, %s, %s, %d, %s, %d, %d, %s, %s);";
        //</editor-fold>

        public static String selectWeatherBy(int cityId, int startPeriod, int endPeriod, boolean useRu, boolean useNightIcon, int limit) {
            String select = "SELECT " +
                    TABLE.CITIES + "." + CITY_COLUMN.NAME + " AS " + AS.CITY_NAME + ", " +
                    TABLE.CONDITIONS + "." + (useRu ? CONDITION_COLUMN.DESC_RU : CONDITION_COLUMN.DESC_EN) + " AS " + AS.COND_DESC + ", " +
                    TABLE.CONDITIONS + "." + (useNightIcon ? CONDITION_COLUMN.NIGHT_ICON : CONDITION_COLUMN.DAY_ICON) + " AS " + AS.COND_ICON + ", " +
                    WEATHER_COLUMN.CITY_ID + ", " +
                    WEATHER_COLUMN.DATE + ", " +
                    WEATHER_COLUMN.TEMP + ", " +
                    WEATHER_COLUMN.T_MIN + ", " +
                    WEATHER_COLUMN.T_MAX + ", " +
                    WEATHER_COLUMN.PRESSURE + ", " +
                    WEATHER_COLUMN.HUMIDITY + ", " +
                    WEATHER_COLUMN.WIND_SPEED + " " +
                    "FROM " + TABLE.WEATHERS + " " +
                    "INNER JOIN " + TABLE.CITIES + " ON " +
                    TABLE.WEATHERS + "." + WEATHER_COLUMN.CITY_ID + " = " + TABLE.CITIES + "." + CITY_COLUMN.ID + " " +
                    "INNER JOIN " + TABLE.CONDITIONS + " ON " +
                    TABLE.WEATHERS + "." + WEATHER_COLUMN.CONDITION_ID + " = " + TABLE.CONDITIONS + "." + CONDITION_COLUMN.ID + " " +
                    "WHERE " +
                    TABLE.WEATHERS + "." + WEATHER_COLUMN.CITY_ID + " = " + cityId +
                    " AND " +
                    TABLE.WEATHERS + "." + WEATHER_COLUMN.DATE + " > " + startPeriod +
                    " AND " +
                    TABLE.WEATHERS + "." + WEATHER_COLUMN.DATE + " < " + endPeriod + " " +
                    "ORDER BY " + TABLE.WEATHERS + "." + WEATHER_COLUMN.DATE + " DESC " +
                    "LIMIT " + (limit <= 0 ? 10 : limit) + " ";
            return select;
        }
    }
}
