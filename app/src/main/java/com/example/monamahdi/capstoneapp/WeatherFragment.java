package com.example.monamahdi.capstoneapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.monamahdi.capstoneapp.weather_models.ListResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.awt.font.TextAttribute;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class WeatherFragment extends Fragment {

    private ArrayList<ListResponse> weatherDataArrayList;
    private TextView TV_temp,temp_min,temp_max,temp_min1,temp_max1,temp_min2,temp_max2;
    private View view;
    private TextView date,date1,date2;
    private TextView city_name;
    String cityName;
    String temperature;

    public WeatherFragment() {
        // Required empty public constructor
    }


    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_weather, container, false);
        initViews();

        if(CashUtiles.getWeatherList(getContext())!=null){

            Type Type1 = new TypeToken<List<ListResponse>>(){}.getType();

            weatherDataArrayList =  new Gson().fromJson(CashUtiles.getWeatherList(getContext()), Type1);
        }
        cityName = CashUtiles.getCityName(getActivity());
        city_name.setText(cityName);
        for (int i =0 ; i<weatherDataArrayList.size();i++){
            TV_temp.setText(convertTempFromKTOC(weatherDataArrayList.get(0).getMain().getTemp()));
            temp_min.setText(convertTempFromKTOC(weatherDataArrayList.get(1).getMain().getTempMin()));
            temp_max.setText(convertTempFromKTOC(weatherDataArrayList.get(1).getMain().getTempMax()));
            temp_min1.setText(convertTempFromKTOC(weatherDataArrayList.get(2).getMain().getTempMin()));
            temp_max1.setText(convertTempFromKTOC(weatherDataArrayList.get(2).getMain().getTempMax()));
            temp_min2.setText(convertTempFromKTOC(weatherDataArrayList.get(3).getMain().getTempMin()));
            temp_max2.setText(convertTempFromKTOC(weatherDataArrayList.get(3).getMain().getTempMax()));


        }



        return view;


    }

    private void initViews(){
        TV_temp = (TextView)view.findViewById(R.id.TV_temp);
        temp_min = (TextView)view.findViewById(R.id.temp_min);
        temp_max = (TextView)view.findViewById(R.id.temp_max);
        temp_min1 = (TextView)view.findViewById(R.id.temp_min1);
        temp_max1 = (TextView)view.findViewById(R.id.temp_max1);
        temp_min2 = (TextView)view.findViewById(R.id.temp_min2);
        temp_max2 = (TextView)view.findViewById(R.id.temp_max2);
        date = (TextView)view.findViewById(R.id.date);
        date1 = (TextView)view.findViewById(R.id.date1);
        date2 = (TextView)view.findViewById(R.id.date2);
        city_name = (TextView)view.findViewById(R.id.city_name);
    }

    private String convertTempFromKTOC(Double temp){
        double newTemp = temp - 273.15;

        String Celsius = new DecimalFormat("##").format(newTemp);

        return Celsius;

    }


}
