package com.example.monamahdi.capstoneapp;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monamahdi.capstoneapp.places_api.ApiClientPlaces;
import com.example.monamahdi.capstoneapp.places_api.ApiInterfacePlaces;
import com.example.monamahdi.capstoneapp.places_models.Example;
import com.example.monamahdi.capstoneapp.places_models.Result;
import com.example.monamahdi.capstoneapp.weather_models.ListResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantsFragment extends Fragment {

    private View view;
    private final static String API_KEY = "AIzaSyBcPGMy2ThOkbeXjdm2oSZN6irAsU5cArM";
    private RecyclerView recycler_view;
    private RestaurantsAdapter restaurantsAdapter;
//    private ArrayList<Result> resultArrayList;
    private final static String API_KEY1 = "AIzaSyBcPGMy2ThOkbeXjdm2oSZN6irAsU5cArM";
    private List<Result> results;
    private String listResponses1;
    String mRecyclerPositionKey;
    Parcelable listState;
    public RestaurantsFragment() {
        // Required empty public constructor
    }


    public static RestaurantsFragment newInstance(String param1, String param2) {
        RestaurantsFragment fragment = new RestaurantsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(mRecyclerPositionKey);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurants, container, false);
//        callingPlacesApi();
        initViews();
        RestaurantOperation restaurantOperation = new RestaurantOperation();
        restaurantOperation.execute("");

        return view;
    }

    private void initViews(){
        recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);

    }


    private void callingPlacesApi(){
        final ApiInterfacePlaces apiService = ApiClientPlaces.getClient().create(ApiInterfacePlaces.class);

        String cityName = CashUtiles.getCityName(getContext());
        String param = "restaurants" + "+" + "in" + "+" + cityName;
        Call<Example> call = apiService.getPlaces(param,API_KEY1);



        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                int statusCode = response.code();
                results = response.body().getResults();
                listResponses1 = new Gson().toJson(results);
                CashUtiles.setRestaurantsList(getContext(),listResponses1);
                restaurantsAdapter = new RestaurantsAdapter(results,getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recycler_view.setLayoutManager(linearLayoutManager);
                recycler_view.setAdapter(restaurantsAdapter);
                recycler_view.getLayoutManager().onRestoreInstanceState(listState);
                restaurantsAdapter.notifyDataSetChanged();
                restaurantsAdapter.notifyItemChanged(0);
                restaurantsAdapter.notifyItemChanged(0,results);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
            }
        });
    }

    private class RestaurantOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            callingPlacesApi();
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(mRecyclerPositionKey,
                recycler_view.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        assert savedInstanceState != null;
//        listState = savedInstanceState.getParcelable(mRecyclerPositionKey);
//
//    }

}
