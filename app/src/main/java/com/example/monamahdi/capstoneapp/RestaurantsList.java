package com.example.monamahdi.capstoneapp;

/**
 * Created by mona.mahdi on 8/19/2018.
 */

public class RestaurantsList {
    public String restaurantsName;
    public String restaurantsRate;

    public String getRestaurantsName() {
        return restaurantsName;
    }

    public void setRestaurantsName(String restaurantsName) {
        this.restaurantsName = restaurantsName;
    }

    public String getRestaurantsRate() {
        return restaurantsRate;
    }

    public void setRestaurantsRate(String restaurantsRate) {
        this.restaurantsRate = restaurantsRate;
    }

    public String getRestaurantsPhoto() {
        return restaurantsPhoto;
    }

    public void setRestaurantsPhoto(String restaurantsPhoto) {
        this.restaurantsPhoto = restaurantsPhoto;
    }

    public String restaurantsPhoto;

    public RestaurantsList(){

    }

    public RestaurantsList(String restaurantsName,String restaurantsRate,String restaurantsPhoto){
        this.restaurantsName = restaurantsName;
        this.restaurantsRate = restaurantsRate;
        this.restaurantsPhoto = restaurantsPhoto;
    }
}
