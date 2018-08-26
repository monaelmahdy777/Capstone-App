package com.example.monamahdi.capstoneapp.weater_api;


import com.example.monamahdi.capstoneapp.weather_models.Example;
import com.example.monamahdi.capstoneapp.weather_models.ListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("/data/2.5/forecast")
    Call<Example> getWeather (
                            @Query("q") String cityName
                            , @Query("appid") String appId);

}



