package com.example.monamahdi.capstoneapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.monamahdi.capstoneapp.places_api.ApiClientPlaces;
import com.example.monamahdi.capstoneapp.places_api.ApiInterfacePlaces;
import com.example.monamahdi.capstoneapp.places_models.Example;
import com.example.monamahdi.capstoneapp.places_models.Photo;
import com.example.monamahdi.capstoneapp.places_models.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mona.mahdi on 8/18/2018.
 */

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.restaurantsViewHolder> {

    private List<Result> restaurants ;
    private Example example;
    private Context context;
    Result result;
    private FirebaseDatabase mFirebaseInstance;
    private boolean isExisthere = false;
    DatabaseReference mDatabase;
    String restaurantId;

    public RestaurantsAdapter(List<Result> restaurants, Context context){
        this.restaurants = restaurants;
        this.context = context;
    }

    public void updateDataSet(List<Result> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    @Override
    public restaurantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurants_list, parent, false);
        return new restaurantsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final restaurantsViewHolder holder,final int position) {
        result = restaurants.get(position);

        try {
            if(result!=null){
                Photo reference = result.getPhotos().get(0);
                if(reference!=null){
                    String imageReference = reference.getPhotoReference();
                    String fullPath = "https://maps.googleapis.com/maps/api/place/photo" +
                            "?maxwidth=400" +
                            "&photoreference= " + imageReference +
                            "&key=";
                    Glide.with(context)
                            .load(fullPath)
                            .into(holder.thumbnail);
                }else {
                    holder.thumbnail.setBackgroundResource(R.drawable.rest);
                }

            }
        }catch (Exception ex){
            Log.i("",ex.toString());
        }


        holder.name.setText(result.getName());
        holder.rate.setText(result.getRating().toString());

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRestaurantToDB(restaurants.get(position));
                holder.overflow.setBackgroundResource(R.drawable.ic_star_black_24dp);
            }
        });


    }

    @Override
    public int getItemCount() {

            return restaurants.size();
    }

    public class restaurantsViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView name,address,rate;
        private ImageView overflow;

        public restaurantsViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
            overflow = (ImageView)itemView.findViewById(R.id.overflow);
            name = (TextView)itemView.findViewById(R.id.name);
            address = (TextView)itemView.findViewById(R.id.address);
            rate = (TextView)itemView.findViewById(R.id.rate);
        }


    }

    private void addRestaurantToDB(final Result result) {
        mFirebaseInstance = FirebaseDatabase.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference("restaurants");
        restaurantId = mDatabase.push().getKey();
        mDatabase.child(restaurantId).setValue(result);


        mDatabase.child(restaurantId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RestaurantsList restaurantsList = dataSnapshot.getValue(RestaurantsList.class);
                restaurantsList.setRestaurantsName(result.getName());
                restaurantsList.setRestaurantsRate(result.getRating().toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });
        Toast.makeText(context,R.string.added_sussecc,Toast.LENGTH_SHORT).show();
    }



}