package com.example.monamahdi.capstoneapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monamahdi.capstoneapp.places_models.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {

    private View view;
    List<String> namesList = new ArrayList<>();
    List<String> rateList = new ArrayList<>();
    private RecyclerView recycler_view;
    private FavoritAdapter favoritAdapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);
        retrieveDataFromDB();

        return view;
    }

    private void retrieveDataFromDB(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("restaurants");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String name = messageSnapshot.child("name").getValue().toString();
                    if (!namesList.contains(name)) {
                        namesList.add(name);
                    }
                    String rate = messageSnapshot.child("formattedAddress").getValue().toString();
                    if (!rateList.contains(rate)) {
                        rateList.add(rate);
                    }

                }
                recycler_view.setHasFixedSize(true);
                favoritAdapter = new FavoritAdapter(namesList,rateList,getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recycler_view.setLayoutManager(linearLayoutManager);
                recycler_view.setAdapter(favoritAdapter);
                favoritAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersRef.addListenerForSingleValueEvent(eventListener);
    }

}
