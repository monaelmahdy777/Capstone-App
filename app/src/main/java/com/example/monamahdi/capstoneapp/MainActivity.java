package com.example.monamahdi.capstoneapp;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.monamahdi.capstoneapp.country_api.ApiClientCountry;
import com.example.monamahdi.capstoneapp.country_api.ApiInterfaceCountry;
import com.example.monamahdi.capstoneapp.country_mopdels.ExampleCountries;
import com.example.monamahdi.capstoneapp.places_api.ApiClientPlaces;
import com.example.monamahdi.capstoneapp.places_api.ApiInterfacePlaces;
import com.example.monamahdi.capstoneapp.places_models.Result;
import com.example.monamahdi.capstoneapp.weater_api.ApiClient;
import com.example.monamahdi.capstoneapp.weater_api.ApiInterface;
import com.example.monamahdi.capstoneapp.weather_models.Example;
import com.example.monamahdi.capstoneapp.weather_models.ListResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private Button TV_go;
    private EditText ET_city,ET_country;
    private final static String API_KEY = "";
    private String listResponses;
    private String city,ET_city_string,ET_country_string;
    private final static String API_KEY1 = "";
    private List<Result> results;
    private String listResponses1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        initViews();
        ET_city.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        ET_country.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        TV_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()){
                    ET_city_string = ET_city.getText().toString();
                    ET_country_string = ET_country.getText().toString();
                    if (ET_city_string.isEmpty()) {
                        Toast.makeText(getApplicationContext(),R.string.city_value,Toast.LENGTH_SHORT).show();
                    }else if(ET_country_string.isEmpty()){
                        Toast.makeText(getApplicationContext(),R.string.country_value,Toast.LENGTH_SHORT).show();
                    }else if(ET_country_string.isEmpty()&&ET_city_string.isEmpty()){
                        Toast.makeText(getApplicationContext(),R.string.city_and_country_value,Toast.LENGTH_SHORT).show();
                    }else {
                        LongOperation longOperation = new LongOperation();
                        longOperation.execute("");

                    }
                }else {
                    Toast.makeText(MainActivity.this, R.string.network_fail, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void weatherApi(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        String country_name = ET_country.getText().toString();

        String city_name = ET_city.getText().toString();

        String country_code = CountriesUtility.getCountryCode(country_name);

        String cityAndCountry = city_name + "," + country_code;

        Call<Example> call = apiService.getWeather(cityAndCountry,API_KEY);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                int statusCode = response.code();
                if (response.code() == 404){
                    Toast.makeText(getApplicationContext(),R.string.sorry+" "+response.message(),Toast.LENGTH_SHORT).show();
                }else {
                    List<ListResponse> responseList = response.body().getList();
                    String cityName = response.body().getCity().getName();
                    CashUtiles.setCityName(getApplicationContext(),cityName);
                    HttpUrl url = response.raw().request().url();
                    listResponses = new Gson().toJson(responseList);
                    CashUtiles.setWeatherList(getApplicationContext(),listResponses);
                    Double temp = responseList.get(0).getMain().getTemp();
                    Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                    startActivity(intent);
                    widgetDetails(temp);
//                    RestaurantOperation restaurantOperation = new RestaurantOperation();
//                    restaurantOperation.execute("");
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
            }
        });
    }

    private void widgetDetails(Double temp){
        String widgetTitle = CashUtiles.getCityName(getApplicationContext());

        String widgetBody = convertTempFromKTOC(temp)  ;


        SharedPreferences preferences = getSharedPreferences("pref", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("widget_title", widgetTitle);
        editor.putString("widget_body", widgetBody);
        editor.apply();

        int[] ids = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, CapstoneAppWidget.class));
        CapstoneAppWidget mWidget = new CapstoneAppWidget();
        mWidget.onUpdate(this, AppWidgetManager.getInstance(this), ids);
    }
    private String convertTempFromKTOC(Double temp){
        double newTemp = temp - 273.15;

        String Celsius = new DecimalFormat("##").format(newTemp);

        return Celsius;

    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            weatherApi();
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }




    private void initViews(){
        TV_go = (Button)findViewById(R.id.TV_go);
        ET_city = (EditText)findViewById(R.id.ET_city);
        ET_country = (EditText)findViewById(R.id.ET_country);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
