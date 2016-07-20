package com.example.wheather.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wheather.R;
import com.example.wheather.view.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherWeekFragment extends Fragment {


    public WeatherWeekFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.weather_week);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_week, container, false);
        return view;
    }

}
