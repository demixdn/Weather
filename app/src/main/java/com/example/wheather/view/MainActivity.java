package com.example.wheather.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.wheather.data.LogUtils;
import com.example.wheather.view.fragments.HistoryFragment;
import com.example.wheather.view.fragments.SettingsFragment;
import com.example.wheather.view.fragments.WeatherDayFragment;
import com.example.wheather.view.fragments.WeatherWeekFragment;
import com.example.wheather.view.widget.TabletDrawerLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabletDrawerLayout drawer;
    boolean isDrawerLocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.activity_main);
        initUI();
        initModel();
    }

    private void initModel() {

        loadInitialFragment();
    }

    private void loadInitialFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        String tag = getIntent().getStringExtra(AppConst.EXTRA_FRAGMENT_KEY);
        if(tag==null){
            fragment = new WeatherWeekFragment();
        }else {
            switch (tag) {
                case AppConst.FRAGMENT_DAY:
                    fragment = new WeatherDayFragment();
                    break;
                default:
                    fragment = new WeatherWeekFragment();
                    break;
            }
        }
        fragmentManager.beginTransaction()
                .add(R.id.detail_container, fragment, tag)
                .commit();
    }

    private void initUI(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
        findViewById(R.id.btNavExitApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void setTheme(){
        SharedPreferences preferences = getSharedPreferences(AppConst.THEME_PREFERENCE, MODE_PRIVATE);
        String theme = preferences.getString(AppConst.THEME_KEY, AppConst.THEME_DEFAULT);
        switch (theme){
            case AppConst.THEME_DEFAULT:
                setTheme(R.style.AppTheme_NoActionBar);
                break;
            case AppConst.THEME_VIOLET:
                setTheme(R.style.AppTheme_NoActionBar_Violet);
                break;
            case AppConst.THEME_BLY:
                setTheme(R.style.AppTheme_NoActionBar_Bly);
                break;
            case AppConst.THEME_CYAN:
                setTheme(R.style.AppTheme_NoActionBar_Cyan);
                break;
            case AppConst.THEME_TURQUOISE:
                setTheme(R.style.AppTheme_NoActionBar_Turquoise);
                break;
            case AppConst.THEME_YELLOW:
                setTheme(R.style.AppTheme_NoActionBar_Yellow);
                break;
            default:
                setTheme(R.style.AppTheme_NoActionBar);
                break;
        }
    }
    private void saveTheme(String themeValue){
        getSharedPreferences(AppConst.THEME_PREFERENCE, MODE_PRIVATE).edit()
                .putString(AppConst.THEME_KEY, themeValue).apply();
    }

    private void initDrawer(Toolbar toolbar) {
        drawer = (TabletDrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        isDrawerLocked = checkIsTablet();
        if(isDrawerLocked){
            View mainContent = findViewById(R.id.main_content);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            drawer.setScrimColor(Color.TRANSPARENT);
            drawer.setTablet(true);
            float navWidth = (float) getResources().getDimensionPixelSize(R.dimen.nav_width);
            mainContent.setX(navWidth);
            int deviceWidth = getResources().getDisplayMetrics().widthPixels;
            ViewGroup.LayoutParams layoutParams = mainContent.getLayoutParams();
            layoutParams.width = deviceWidth - (int) navWidth;
            mainContent.setLayoutParams(layoutParams);
            drawer.setDrawerListener(null);
        }else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        }
        navigationView.setNavigationItemSelectedListener(this);
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
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void onBackPressed() {
        LogUtils.E("onBackPressed");
        if(isDrawerLocked){
            super.onBackPressed();
        } else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        String tag = null;
        saveTheme(AppConst.THEME_DEFAULT);
        if (id == R.id.nav_day) {
//            fragment = new WeatherDayFragment();
//            tag = AppConst.FRAGMENT_DAY;
            saveTheme(AppConst.THEME_CYAN);
            Intent dayIntent = new Intent(MainActivity.this, MainActivity.class);
            dayIntent.putExtra(AppConst.EXTRA_FRAGMENT_KEY, AppConst.FRAGMENT_DAY);
            startActivity(dayIntent);
            finish();
            return true;
        } else if (id == R.id.nav_week) {
            fragment = new WeatherWeekFragment();
            tag = AppConst.FRAGMENT_WEEK;
        } else if (id == R.id.nav_history) {
            fragment = new HistoryFragment();
            tag = AppConst.FRAGMENT_HISTORY;
        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
            tag = AppConst.FRAGMENT_SETTINGS;
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
