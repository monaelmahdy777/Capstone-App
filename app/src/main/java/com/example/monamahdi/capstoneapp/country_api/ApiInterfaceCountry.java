package com.example.monamahdi.capstoneapp.country_api;

import com.example.monamahdi.capstoneapp.country_mopdels.ExampleCountries;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiInterfaceCountry {

    @GET("name/{name}")
    Call<ExampleCountries> getCode(@Path("name") String name);

}



