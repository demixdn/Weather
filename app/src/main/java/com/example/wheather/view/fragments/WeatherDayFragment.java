package com.example.wheather.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wheather.R;
import com.example.wheather.data.LogUtils;
import com.example.wheather.view.AppConst;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDayFragment extends Fragment {

    private String themeValue;

    public static WeatherDayFragment getInstance(String theme){
        Bundle args = new Bundle(1);
        args.putString(AppConst.THEME_KEY, theme);
        WeatherDayFragment fragment = new WeatherDayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public WeatherDayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle arguments){
        super.onCreate(arguments);
        if(arguments!=null){
            themeValue = arguments.getString(AppConst.THEME_KEY);
            if(TextUtils.isEmpty(themeValue)) themeValue = AppConst.THEME_CYAN;
        }else {
            themeValue = AppConst.THEME_CYAN;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.weather_day);
        LogUtils.E("Fragment title - "+getString(R.string.weather_day));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_day, container, false);
        setBackground(view);
        return view;
    }

    private void setBackground(View view){
        switch (themeValue){
            case AppConst.THEME_VIOLET:
                view.setBackgroundResource(R.drawable.bg_theme_violet);
                return;
            case AppConst.THEME_BLY:
                view.setBackgroundResource(R.drawable.bg_theme_bly);
                return;
            case AppConst.THEME_CYAN:
                view.setBackgroundResource(R.drawable.bg_theme_cyan);
                return;
            case AppConst.THEME_TURQUOISE:
                view.setBackgroundResource(R.drawable.bg_theme_turquoise);
                return;
            case AppConst.THEME_YELLOW:
                view.setBackgroundResource(R.drawable.bg_theme_yellow);
        }
    }

}
