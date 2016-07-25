package com.example.wheather.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.data.model.dao.CityDAO;
import com.example.data.repository.WeatherDataRepository;
import com.example.data.repository.WeatherDataStore;
import com.example.wheather.R;
import com.example.wheather.WeatherApp;
import com.example.wheather.data.AppSettings;
import com.example.wheather.data.mapper.DataConst;
import com.example.wheather.data.utils.LogUtils;
import com.example.wheather.data.sync.SyncService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

public class SettingsActivity extends AppCompatActivity {

    RelativeLayout layoutCityContainer;
    RelativeLayout layoutPeriodContainer;
    TextInputLayout autoCompleteTextTil;
    AutoCompleteTextView autoCompleteTextView;

    ArrayList<CityDAO> cities = new ArrayList<>();

    CompositeSubscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initToolbar();
        initSwitches();
        initAutoComplete();
        WeatherDataStore dataStore = new WeatherDataRepository(WeatherApp.getInstance().getDiskDataSource());
        subscription = new CompositeSubscription();
        LogUtils.E("Sart load cities");
        subscription.add(dataStore.getAllCities().subscribe(new Subscriber<List<CityDAO>>() {
            @Override
            public void onCompleted() {
                initAdapter();
                LogUtils.E("end load cities");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<CityDAO> cityDAOs) {
                if(cityDAOs!=null)
                    cities.addAll(cityDAOs);
            }
        }));
    }

    @Override
    public void onDestroy(){
        if(!AppSettings.isUpdateOnStart()) {
            startService(new Intent(WeatherApp.getInstance(), SyncService.class));
        }else {
            stopService(new Intent(WeatherApp.getInstance(), SyncService.class));
        }
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
        super.onDestroy();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    private void initSwitches(){
        layoutCityContainer = (RelativeLayout)findViewById(R.id.rlSettingsCityContainer);
        layoutPeriodContainer = (RelativeLayout)findViewById(R.id.rlSettingsPeriodContainer);
        SwitchCompat switchWifi = (SwitchCompat)findViewById(R.id.scSettingsUploadWifi);
        SwitchCompat switchStartup = (SwitchCompat)findViewById(R.id.scSettingsUploadStart);
        SwitchCompat switchExit = (SwitchCompat)findViewById(R.id.scSettingsAskExit);
        SwitchCompat switchLocation = (SwitchCompat)findViewById(R.id.scSettingsAutoLocation);

        switchWifi.setChecked(AppSettings.isUpdateOnWifi());
        switchStartup.setChecked(AppSettings.isUpdateOnStart());
        switchExit.setChecked(AppSettings.isShowOnExit());
        switchLocation.setChecked(AppSettings.isAutoLocation());

        initPeriodContainer();

        int visibilityCity = switchLocation.isChecked() ? View.GONE : View.VISIBLE;
        layoutCityContainer.setVisibility(visibilityCity);

        switchWifi.setOnCheckedChangeListener((buttonView, isChecked) -> AppSettings.setConfigWifi(isChecked));
        switchExit.setOnCheckedChangeListener((buttonView, isChecked) -> AppSettings.setConfigMessage(isChecked));
        switchStartup.setOnCheckedChangeListener(this::switchUpdateChange);
        switchLocation.setOnCheckedChangeListener(this::switchLocationChange);
    }

    private void initPeriodContainer(){
        int visibility = AppSettings.isUpdateOnStart() ? View.GONE : View.VISIBLE;
        layoutPeriodContainer.setVisibility(visibility);
        RadioGroup radioGroupPeriod = (RadioGroup)findViewById(R.id.rgSettingsPeriod);
        ((AppCompatRadioButton)radioGroupPeriod.getChildAt(0))
                .setOnCheckedChangeListener((buttonView, isChecked) -> checkedRG(DataConst.UpdateConfig.THREE_HOUR_KEY, isChecked));
        ((AppCompatRadioButton)radioGroupPeriod.getChildAt(1))
                .setOnCheckedChangeListener((buttonView, isChecked) -> checkedRG(DataConst.UpdateConfig.SIX_HOUR_KEY, isChecked));
        ((AppCompatRadioButton)radioGroupPeriod.getChildAt(2))
                .setOnCheckedChangeListener((buttonView, isChecked) -> checkedRG(DataConst.UpdateConfig.NINE_HOUR_KEY, isChecked));
        ((AppCompatRadioButton)radioGroupPeriod.getChildAt(3))
                .setOnCheckedChangeListener((buttonView, isChecked) -> checkedRG(DataConst.UpdateConfig.TWELVE_HOUR_KEY, isChecked));
        ((AppCompatRadioButton)radioGroupPeriod.getChildAt(4))
                .setOnCheckedChangeListener((buttonView, isChecked) -> checkedRG(DataConst.UpdateConfig.TWENTY_FOUR_HOUR_KEY, isChecked));
        ((AppCompatRadioButton)radioGroupPeriod.getChildAt(5))
                .setOnCheckedChangeListener((buttonView, isChecked) -> checkedRG(DataConst.UpdateConfig.FORTY_EIGHT_HOUR_KEY, isChecked));
        if(!AppSettings.isUpdateOnStart()){
            setCheckedButton(radioGroupPeriod);
        }
    }

    private void checkedRG(String key, boolean isChecked){
        RadioGroup radioGroupPeriod = (RadioGroup)findViewById(R.id.rgSettingsPeriod);
        if(isChecked) {
            AppSettings.setConfigPeriodHour(key);
            setCheckedButton(radioGroupPeriod);
        }
    }

    private void setCheckedButton(RadioGroup radioGroupPeriod) throws NullPointerException {
        String key = AppSettings.periodHour();
        switch (key){
            case DataConst.UpdateConfig.THREE_HOUR_KEY:
                setCheckedRGItem(radioGroupPeriod, 0);
                break;
            case DataConst.UpdateConfig.SIX_HOUR_KEY:
                setCheckedRGItem(radioGroupPeriod, 1);
                break;
            case DataConst.UpdateConfig.NINE_HOUR_KEY:
                setCheckedRGItem(radioGroupPeriod, 2);
                break;
            case DataConst.UpdateConfig.TWELVE_HOUR_KEY:
                setCheckedRGItem(radioGroupPeriod, 3);
                break;
            case DataConst.UpdateConfig.TWENTY_FOUR_HOUR_KEY:
                setCheckedRGItem(radioGroupPeriod, 4);
                break;
            case DataConst.UpdateConfig.FORTY_EIGHT_HOUR_KEY:
                setCheckedRGItem(radioGroupPeriod, 5);
                break;
            default:
                setCheckedRGItem(radioGroupPeriod, 1);
                break;
        }
    }

    private void setCheckedRGItem(RadioGroup radioGroupPeriod, int index) {
        for(int i=0;i<radioGroupPeriod.getChildCount();i++){
            ((AppCompatRadioButton)radioGroupPeriod.getChildAt(i)).setChecked(false);
        }
        ((AppCompatRadioButton)radioGroupPeriod.getChildAt(index)).setChecked(true);
    }

    private void switchLocationChange(CompoundButton buttonView, boolean isChecked) {
        int visibility = isChecked ? View.GONE : View.VISIBLE;
        layoutCityContainer.setVisibility(visibility);
        if(isChecked) {
            AppSettings.setConfigAutoLocation(true);
            AppSettings.setCityName(null);
            AppSettings.setCityId(0);
            autoCompleteTextView.setText("");
            autoCompleteTextView.clearComposingText();
            autoCompleteTextView.clearFocus();
        }
    }

    private void switchUpdateChange(CompoundButton buttonView, boolean isChecked) {
        AppSettings.setConfigUpdateStart(isChecked);
        int visibility = isChecked ? View.GONE : View.VISIBLE;
        layoutPeriodContainer.setVisibility(visibility);
    }

    private void initAutoComplete() {
        autoCompleteTextTil = (TextInputLayout)findViewById(R.id.tilSettingsCity);
        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.actvSettingsCityEdit);
        setStyleForTextForAutoComplete(getResources().getColor(R.color.colorAccent));
        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                setStyleForTextForAutoComplete(getResources().getColor(R.color.colorAccent));
                autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                AppSettings.setCityName(null);
                AppSettings.setCityId(0);
            } else {
                if(autoCompleteTextView.getText().length() == 0) {
                    setStyleForTextForAutoComplete(getResources().getColor(R.color.colorAccent));
                }
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityDAO selectedItem = (CityDAO) parent.getItemAtPosition(position);
                LogUtils.E(selectedItem.toString());
                autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check,0);
                AppSettings.setCityName(selectedItem.getName());
                AppSettings.setCityId(selectedItem.getId());
                hideKeyboard();
                autoCompleteTextView.clearFocus();
                AppSettings.setConfigAutoLocation(false);
            }
        });
        if(AppSettings.cityID()>0 && !TextUtils.isEmpty(AppSettings.cityName())){
            autoCompleteTextView.setText(AppSettings.cityName());
            autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check,0);
        }

        initAdapter();

    }

    private void initAdapter() {
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_city_name, cities);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(3);
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setStyleForTextForAutoComplete(int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(autoCompleteTextView.getBackground());
        DrawableCompat.setTint(wrappedDrawable, color);
        autoCompleteTextView.setBackgroundDrawable(wrappedDrawable);
    }

    public void onShowErrors() {
        autoCompleteTextTil.setErrorEnabled(true);
        autoCompleteTextTil.setError("Showcase of error on AutoCompleteTextView ");
    }
}
