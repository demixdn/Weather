package com.example.wheather.view.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.data.model.dao.WeatherDAO;
import com.example.wheather.R;
import com.example.wheather.data.AppSettings;
import com.example.wheather.data.DataManager;
import com.example.wheather.data.utils.LogUtils;
import com.example.wheather.view.MainActivity;
import com.example.wheather.view.adapters.WeekRecyclerAdapter;
import com.example.wheather.view.model.DayWeather;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherWeekFragment extends Fragment {

    private RecyclerView recyclerView;

    CompositeSubscription subscription = new CompositeSubscription();
    ProgressDialog progressDialog;
    WeekRecyclerAdapter adapter;

    public WeatherWeekFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initProgressDialog();
        if(AppSettings.isUpdateOnStart())
            loadFromApi();
        else
            loadFromStore();
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.weather_week);
        initAdapter();
    }

    @Override
    public void onDetach(){
        super.onDetach();
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_week, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.rvWeekWeathers);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
    }

    private void loadFromApi() {
        progressDialog.show();
        subscription.add(DataManager.getInstance().loadFromServer(getActivity())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WeatherDAO>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        progressDialog.cancel();
                    }

                    @Override
                    public void onNext(List<WeatherDAO> list) {
                        LogUtils.E("--==WEATHER LOADED==--");
                        if(list.size()>0){
                            int cityId = list.get(0).getCityId();
                            AppSettings.setCityId(cityId);
                        }
                        loadFromStore();
                    }
                }));
    }

    private void loadFromStore() {
        if(DataManager.getInstance().getWeekItems()!=null){
            initAdapter(DataManager.getInstance().getWeekItems());
            progressDialog.cancel();
        }else {
            subscription.add(DataManager.getInstance().getWeekWeathers()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<DayWeather>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            progressDialog.cancel();
                        }

                        @Override
                        public void onNext(List<DayWeather> weathers) {
                            progressDialog.cancel();
                            LogUtils.E("WEATHERS!! " + weathers.size());
                            LogUtils.E(weathers.toString());
                            initAdapter(weathers);
                        }
                    }));
        }
    }

    private void initAdapter(List<DayWeather> items){
        LogUtils.E(items.toString());
        adapter = new WeekRecyclerAdapter(getActivity(), items);
        if(recyclerView!=null)
            recyclerView.setAdapter(adapter);
    }

    private void initAdapter(){
        if(adapter!=null)
            recyclerView.setAdapter(adapter);
    }

}
