package com.example.wheather.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.wheather.R;
import com.example.wheather.data.AppSettings;
import com.example.wheather.data.mapper.DataConst;
import com.example.wheather.data.DataManager;
import com.example.wheather.data.gps.GPSTracker;
import com.example.wheather.data.utils.LogUtils;
import com.example.wheather.data.utils.Message;
import com.example.wheather.view.fragments.HistoryFragment;
import com.example.wheather.view.fragments.WeatherWeekFragment;
import com.example.wheather.view.widget.TabletDrawerLayout;
import com.google.gson.Gson;

import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabletDrawerLayout drawerLayout;
    private NavigationView navigationView;
    boolean isDrawerLocked = false;


    ProgressDialog progressDialog;
    CompositeSubscription subscription = new CompositeSubscription();
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGPS();
        initUI();
        initModel();
    }

    private void initGPS() {
        gpsTracker = new GPSTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            AppSettings.setCoordLon(longitude);
            AppSettings.setCoordLat(latitude);
            LogUtils.E("Coords lat:"+latitude+"; lon"+longitude);
            gpsTracker.stopUsingGPS();
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public void onDestroy(){
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
        if(gpsTracker!=null)
            gpsTracker.stopUsingGPS();
        super.onDestroy();
    }

    private void initModel() {
        loadInitialFragment();
    }

    private void loadInitialFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        fragment = new WeatherWeekFragment();
        navigationView.getMenu().getItem(1).setChecked(true);
        String tag = AppConst.FRAGMENT_WEEK;
        fragmentManager.beginTransaction()
                .add(R.id.detail_container, fragment, tag)
                .commit();
    }

    private void initUI(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
        findViewById(R.id.btNavExitApp).setOnClickListener(v -> finish());
    }

    private String getFragmentFromPrefs(){
        return getSharedPreferences(AppConst.THEME_PREFERENCE, MODE_PRIVATE)
                .getString(AppConst.EXTRA_FRAGMENT_KEY, AppConst.FRAGMENT_WEEK);
    }

    private void saveFragment(String fragmentValue){
        getSharedPreferences(AppConst.THEME_PREFERENCE, MODE_PRIVATE).edit()
                .putString(AppConst.EXTRA_FRAGMENT_KEY, fragmentValue).apply();
    }

    private void initDrawer(Toolbar toolbar) {
        drawerLayout = (TabletDrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        isDrawerLocked = checkIsTablet();
        if(isDrawerLocked){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            drawerLayout.setScrimColor(Color.TRANSPARENT);
            drawerLayout.setTablet(true);
            float navWidth = (float) getResources().getDimensionPixelSize(R.dimen.nav_width);
            offsetTabletViews(navWidth);
            drawerLayout.setDrawerListener(null);
        }else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void offsetTabletViews(float navWidth) {
        View mainContent = findViewById(R.id.main_content);
        mainContent.setX(navWidth);
        int deviceWidth = getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams layoutParams = mainContent.getLayoutParams();
        layoutParams.width = deviceWidth - (int) navWidth;
        mainContent.setLayoutParams(layoutParams);
        View container = findViewById(R.id.detail_container);
        CoordinatorLayout.LayoutParams coorparams = (CoordinatorLayout.LayoutParams)container.getLayoutParams();
        coorparams.width = deviceWidth - (int) navWidth;
        container.setLayoutParams(coorparams);
    }

    private boolean checkIsTablet(){
        try {
            return getResources().getBoolean(R.bool.isTablet);
        } catch (Resources.NotFoundException ignore) {
            return false;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(isDrawerLocked) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void onBackPressed() {
        LogUtils.E("onBackPressed");
        if(isDrawerLocked){
            if(AppSettings.isShowOnExit()){
                Message.AlertClose(this).show();
            }else {
                super.onBackPressed();
            }
        } else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if(AppSettings.isShowOnExit()){
                    Message.AlertClose(this).show();
                }else {
                    super.onBackPressed();
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        String tag = null;
        if (id == R.id.nav_day) {
            if(DataManager.getInstance().getFirstDay()!=null) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                String data = new Gson().toJson(DataManager.getInstance().getFirstDay());
                intent.putExtra(AppConst.EXTRA_DAY_KEY, data);
                getSharedPreferences(AppConst.THEME_PREFERENCE, Context.MODE_PRIVATE).edit().putString(AppConst.THEME_KEY, DataConst.Theme.getThemeBy(DataManager.getInstance().getFirstDay().getConditionId())).apply();
                startActivity(intent);
            }
            return true;
        } else if (id == R.id.nav_week) {
            item.setChecked(true);
            saveFragment(AppConst.FRAGMENT_WEEK);
            fragment = new WeatherWeekFragment();
            tag = AppConst.FRAGMENT_WEEK;
        } else if (id == R.id.nav_history) {
            item.setChecked(true);
            saveFragment(AppConst.FRAGMENT_HISTORY);
            fragment = new HistoryFragment();
            tag = AppConst.FRAGMENT_HISTORY;
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        fragmentManager.beginTransaction()
                .add(R.id.detail_container, fragment, tag)
                .commit();
        if(!isDrawerLocked) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

}
