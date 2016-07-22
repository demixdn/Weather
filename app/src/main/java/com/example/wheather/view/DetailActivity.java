package com.example.wheather.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.data.TempData;
import com.example.wheather.R;
import com.example.wheather.view.model.DayWeather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    DayWeather dayItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme();
        setContentView(R.layout.activity_detail);
        findViewById(R.id.btBack).setOnClickListener(v -> finish());
        Type listType = new TypeToken<ArrayList<DayWeather>>(){}.getType();
        ArrayList<DayWeather> weathers = new Gson().fromJson(TempData.TEST_DATA_WEATHER, listType);
        if(weathers!=null && weathers.size()>0)
            dayItem = weathers.get(1);
        showWeatherData(dayItem);
    }

    private void showWeatherData(DayWeather item){
        TextView title = (TextView)findViewById(R.id.tvDayDetailTitle);
        TextView temp = (TextView)findViewById(R.id.tvDayDetailCurrentT);
        TextView tempMin = (TextView)findViewById(R.id.tvDayDetailMinT);
        TextView pressure = (TextView)findViewById(R.id.tvDayDetailPressure);
        TextView humidity = (TextView)findViewById(R.id.tvDayDetailHumidity);
        TextView wind = (TextView)findViewById(R.id.tvDayDetailWindSpeed);
        String titleStr = item.getDateStr().substring(0,1).toUpperCase(Locale.getDefault())+item.getDateStr().substring(1);
        title.setText(titleStr);
        temp.setText(getString(R.string.degree_celsius, String.valueOf(item.getTemp())));
        tempMin.setText(getString(R.string.degree_celsius, String.valueOf(item.getTempMin())));
        pressure.setText(getString(R.string.pressure, item.getPressure()));
        String humidityStr = getString(R.string.humidity, item.getHumidity())+" %";
        humidity.setText(humidityStr);
        wind.setText(getString(R.string.wind, String.valueOf(item.getWindSpeed())));
        int imageId = getResources().getIdentifier(item.getIcon(), "drawable", getPackageName());
        if(imageId==0) imageId=R.drawable.icon_50d;
        ImageView image = (ImageView)findViewById(R.id.ivDayDetailWeather);
        image.setImageResource(imageId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Restore theme key from preference
     * @return theme key, such as {@link AppConst#THEME_DEFAULT} and other
     */
    private String getThemeFromPrefs(){
        return getSharedPreferences(AppConst.THEME_PREFERENCE, MODE_PRIVATE)
                .getString(AppConst.THEME_KEY, AppConst.THEME_DEFAULT);
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
}
