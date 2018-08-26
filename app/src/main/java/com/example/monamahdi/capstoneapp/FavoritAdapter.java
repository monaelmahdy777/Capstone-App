package com.example.monamahdi.capstoneapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by mona.mahdi on 8/20/2018.
 */

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.favoriteViewHolder> {

    private Context context;
    private List<String> nameList;
    private List<String> addressList;

    public FavoritAdapter(List<String> nameList,List<String> addressList, Context context){
        this.nameList = nameList;
        this.addressList = addressList;
        this.context = context;
    }


    @Override
    public favoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new favoriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final favoriteViewHolder holder, final int position) {

        String names = nameList.get(position);
        String addresss = addressList.get(position);

        holder.name.setText(names);
        holder.address.setText(addresss);


    }

    @Override
    public int getItemCount() {
        return nameList.size() ;
    }

    public class favoriteViewHolder extends RecyclerView.ViewHolder {

        private TextView name,address;

        public favoriteViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            address = (TextView)itemView.findViewById(R.id.address);
        }


    }
}
