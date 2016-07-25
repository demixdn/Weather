package com.example.wheather.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wheather.R;
import com.example.wheather.data.mapper.DataConst;
import com.example.wheather.view.AppConst;
import com.example.wheather.view.DetailActivity;
import com.example.wheather.view.model.DayWeather;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

/**
 * Created by Aleksandr on 25.07.2016 in Weather.
 */
public class WeekRecyclerAdapter extends RecyclerView.Adapter<WeekRecyclerAdapter.WeekViewHolder> {

    List<DayWeather> items;
    Context context;

    public WeekRecyclerAdapter(Context context, List<DayWeather> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public WeekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        WeekViewHolder weekViewHolder = new WeekViewHolder(root);
        return weekViewHolder;
    }

    @Override
    public void onBindViewHolder(WeekViewHolder holder, int position) {
        DayWeather item = items.get(position);
        int imageId = context.getResources().getIdentifier(item.getIcon(), "drawable", context.getPackageName());
        if(imageId==0) imageId = R.drawable.icon_50d;
        holder.icon.setImageResource(imageId);
        holder.icon.setBackgroundColor(context.getResources().getColor(DataConst.Theme.getPrimary(item.getConditionId())));
        if(context.getResources().getBoolean(R.bool.isTablet)){
            holder.temp.setVisibility(View.VISIBLE);
            holder.temp.setText(context.getString(R.string.degree_celsius,item.getTemp()));
            holder.temp.setBackgroundColor(context.getResources().getColor(DataConst.Theme.getPrimary(item.getConditionId())));
        }else {
            holder.temp.setVisibility(View.GONE);
        }
        String titleStr = item.getDateString().substring(0,1).toUpperCase(Locale.getDefault())+item.getDateString().substring(1);
        holder.date.setText(titleStr);
        holder.date.setBackgroundColor(context.getResources().getColor(DataConst.Theme.getPrimaryDark(item.getConditionId())));
        holder.tempLong.setText(context.getString(R.string.degree_celsius_long, item.getTemp()));
        holder.tempLong.setBackgroundColor(context.getResources().getColor(DataConst.Theme.getPrimaryDark(item.getConditionId())));
        holder.pressure.setText(context.getString(R.string.pressure, item.getPressure()));
        holder.pressure.setBackgroundColor(context.getResources().getColor(DataConst.Theme.getPrimaryDark(item.getConditionId())));
        holder.itemView.setTag(item);
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(this::goToDetail);
    }

    private void goToDetail(View v) {
        DayWeather vitew = (DayWeather)v.getTag();
        Intent intent = new Intent(context, DetailActivity.class);
        String data = new Gson().toJson(vitew);
        intent.putExtra(AppConst.EXTRA_DAY_KEY, data);
        context.getSharedPreferences(AppConst.THEME_PREFERENCE, Context.MODE_PRIVATE)
                .edit().putString(AppConst.THEME_KEY, DataConst.Theme.getThemeBy(vitew.getConditionId())).apply();
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class WeekViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView temp;
        TextView date;
        TextView tempLong;
        TextView pressure;

        public WeekViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView)itemView.findViewById(R.id.ivItemIcon);
            date = (TextView)itemView.findViewById(R.id.tvItemDay);
            temp = (TextView)itemView.findViewById(R.id.tvItemTemp);
            tempLong = (TextView)itemView.findViewById(R.id.tvItemTempLong);
            pressure = (TextView)itemView.findViewById(R.id.tvItemPressure);
        }
    }
}
