package com.example.wheather.view;

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
import com.example.wheather.data.LogUtils;
import com.example.wheather.view.fragments.HistoryFragment;
import com.example.wheather.view.fragments.SettingsFragment;
import com.example.wheather.view.fragments.WeatherDayFragment;
import com.example.wheather.view.fragments.WeatherWeekFragment;
import com.example.wheather.view.widget.TabletDrawerLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabletDrawerLayout drawerLayout;
    private NavigationView navigationView;
    boolean isDrawerLocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.E("OnCreate");
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
        Fragment fragment;
        String tag = getFragmentFromPrefs();
        if(tag==null){
            fragment = new WeatherWeekFragment();
        }else {
            switch (tag) {
                case AppConst.FRAGMENT_DAY:
                    fragment = WeatherDayFragment.getInstance(AppConst.THEME_TURQUOISE);
                    navigationView.getMenu().getItem(0).setChecked(true);
                    break;
                case AppConst.FRAGMENT_WEEK:
                    fragment = new WeatherWeekFragment();
                    navigationView.getMenu().getItem(1).setChecked(true);
                    break;
                case AppConst.FRAGMENT_HISTORY:
                    fragment = new HistoryFragment();
                    navigationView.getMenu().getItem(2).setChecked(true);
                    break;
                case AppConst.FRAGMENT_SETTINGS:
                    fragment = new SettingsFragment();
                    navigationView.getMenu().getItem(3).setChecked(true);
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
        findViewById(R.id.btNavExitApp).setOnClickListener(v -> finish());


    }

    private void setTheme(){
        String theme = getThemeFromPrefs();
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

    /**
     * Restore theme key from preference
     * @return theme key, such as {@link AppConst#THEME_DEFAULT} and other
     */
    private String getThemeFromPrefs(){
        return getSharedPreferences(AppConst.THEME_PREFERENCE, MODE_PRIVATE)
                .getString(AppConst.THEME_KEY, AppConst.THEME_DEFAULT);
    }

    private String getFragmentFromPrefs(){
        return getSharedPreferences(AppConst.THEME_PREFERENCE, MODE_PRIVATE)
                .getString(AppConst.EXTRA_FRAGMENT_KEY, AppConst.FRAGMENT_WEEK);
    }

    /**
     * Save theme key for restart activity
     * @param themeValue theme key. The value must be from
        {@link AppConst#THEME_DEFAULT}
        {@link AppConst#THEME_VIOLET}
        {@link AppConst#THEME_BLY}
        {@link AppConst#THEME_CYAN}
        {@link AppConst#THEME_TURQUOISE}
        {@link AppConst#THEME_YELLOW}
     */
    private void saveTheme(String themeValue){
        getSharedPreferences(AppConst.THEME_PREFERENCE, MODE_PRIVATE).edit()
                .putString(AppConst.THEME_KEY, themeValue).apply();
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
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        String tag = null;
        if (id == R.id.nav_day) {
            //saveFragment(AppConst.FRAGMENT_DAY);
            saveTheme(AppConst.THEME_CYAN);
            //restartActivity();
            startActivity(new Intent(MainActivity.this, DetailActivity.class));
            return true;
        } else if (id == R.id.nav_week) {
            item.setChecked(true);
            saveFragment(AppConst.FRAGMENT_WEEK);
            if(getThemeFromPrefs().compareTo(AppConst.THEME_DEFAULT)==0){
                fragment = new WeatherWeekFragment();
                tag = AppConst.FRAGMENT_WEEK;
            } else {
                saveTheme(AppConst.THEME_DEFAULT);
                restartActivity();
                return true;
            }
        } else if (id == R.id.nav_history) {
            item.setChecked(true);
            saveFragment(AppConst.FRAGMENT_HISTORY);
            if(getThemeFromPrefs().compareTo(AppConst.THEME_DEFAULT)==0){
                fragment = new HistoryFragment();
                tag = AppConst.FRAGMENT_HISTORY;
            } else {
                saveTheme(AppConst.THEME_DEFAULT);
                restartActivity();
                return true;
            }
        } else if (id == R.id.nav_settings) {
            item.setChecked(true);
            saveFragment(AppConst.FRAGMENT_SETTINGS);
            if(getThemeFromPrefs().compareTo(AppConst.THEME_DEFAULT)==0){
                fragment = new SettingsFragment();
                tag = AppConst.FRAGMENT_SETTINGS;
            } else {
                saveTheme(AppConst.THEME_DEFAULT);
                restartActivity();
                return true;
            }
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

    private void restartActivity() {
        Intent dayIntent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(dayIntent);
        finish();
    }


}
