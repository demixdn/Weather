package com.example.wheather.data.mapper;

import com.example.wheather.R;
import com.example.wheather.view.AppConst;

/**
 * Created by Aleksandr on 23.07.2016 in Weather.
 */
public class DataConst {
    public static final class UpdateConfig {
        public static final int THREE_HOUR = 3 * 60 * 60; //in seconds
        public static final int SIX_HOUR = 6 * 60 * 60; //in seconds
        public static final int NINE_HOUR = 9 * 60 * 60; //in seconds
        public static final int TWELVE_HOUR = 12 * 60 * 60; //in seconds
        public static final int TWENTY_FOUR_HOUR = 24 * 60 * 60; //in seconds
        public static final int FORTY_EIGHT_HOUR = 48 * 60 * 60; //in seconds

        public static final String THREE_HOUR_KEY = "three_hour";
        public static final String SIX_HOUR_KEY =  "six_hour";
        public static final String NINE_HOUR_KEY =  "nine_hour";
        public static final String TWELVE_HOUR_KEY =  "twelve_hour";
        public static final String TWENTY_FOUR_HOUR_KEY =  "twenty_four_hour";
        public static final String FORTY_EIGHT_HOUR_KEY =  "forty_eight_hour";

        public static int valueHourBy(String key){
            switch (key){
                case THREE_HOUR_KEY:return THREE_HOUR;
                case SIX_HOUR_KEY:return SIX_HOUR;
                case NINE_HOUR_KEY:return NINE_HOUR;
                case TWELVE_HOUR_KEY:return TWELVE_HOUR;
                case TWENTY_FOUR_HOUR_KEY:return TWENTY_FOUR_HOUR;
                case FORTY_EIGHT_HOUR_KEY:return FORTY_EIGHT_HOUR;
                default:return SIX_HOUR;
            }
        }
        public static String keyHourBy(int value){
            switch (value){
                case THREE_HOUR:return THREE_HOUR_KEY;
                case SIX_HOUR:return SIX_HOUR_KEY;
                case NINE_HOUR:return NINE_HOUR_KEY;
                case TWELVE_HOUR:return TWELVE_HOUR_KEY;
                case TWENTY_FOUR_HOUR:return TWENTY_FOUR_HOUR_KEY;
                case FORTY_EIGHT_HOUR:return FORTY_EIGHT_HOUR_KEY;
                default:return SIX_HOUR_KEY;
            }
        }
    }

    public static final class Theme{
        public static String getThemeBy(int conditionId){
            int group = conditionId/100;
            switch (group){
                case 2:return AppConst.THEME_CYAN;
                case 3:return AppConst.THEME_TURQUOISE;
                case 5:return AppConst.THEME_BLY;
                case 6:return AppConst.THEME_BLY;
                case 7:return AppConst.THEME_TURQUOISE;
                case 8:return AppConst.THEME_YELLOW;
                case 9:return AppConst.THEME_VIOLET;
                default:return AppConst.THEME_BLY;
            }
        }

        public static int getPrimary(int conditionId){
            int group = conditionId/100;
            switch (group){
                case 2:return R.color.colorPrimaryCyan;
                case 3:return R.color.colorPrimaryTurquoise;
                case 5:return R.color.colorPrimaryBly;
                case 6:return R.color.colorPrimaryBly;
                case 7:return R.color.colorPrimaryTurquoise;
                case 8:return R.color.colorPrimaryYellow;
                case 9:return R.color.colorPrimaryViolet;
                default:return R.color.colorPrimaryBly;
            }
        }
        public static int getPrimaryDark(int conditionId){
            int group = conditionId/100;
            switch (group){
                case 2:return R.color.colorPrimaryCyanDark;
                case 3:return R.color.colorPrimaryTurquoiseDark;
                case 5:return R.color.colorPrimaryBlyDark;
                case 6:return R.color.colorPrimaryBlyDark;
                case 7:return R.color.colorPrimaryTurquoiseDark;
                case 8:return R.color.colorPrimaryYellowDark;
                case 9:return R.color.colorPrimaryVioletDark;
                default:return R.color.colorPrimaryBlyDark;
            }
        }
    }
}
