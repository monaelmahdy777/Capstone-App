package com.example.monamahdi.capstoneapp.places_api;



import com.example.monamahdi.capstoneapp.places_models.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterfacePlaces {

    @GET("/maps/api/place/textsearch/json")
    Call<Example> getPlaces(
            @Query("query") String parameters
            , @Query("key") String appId);


}



