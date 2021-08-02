package com.example.myshopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshopping.Model.Notifications;
import com.example.myshopping.R;
import com.example.myshopping.Model.Products;


import java.util.List;


public class NotifiAdapter extends RecyclerView.Adapter<NotifiAdapter.NotifiViewHolder> {

    Context context;
    List<Notifications> notifiList;






    public NotifiAdapter(Context context, List<Notifications> notifiList) {
        this.context = context;
        this.notifiList = notifiList;
    }

    @NonNull
    @Override
    public NotifiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_notifi, parent, false);
        return new NotifiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifiViewHolder holder, int position) {


        holder.photos.setImageResource(notifiList.get(position).getPhoto());
        holder.time.setText(notifiList.get(position).getTime());
        holder.des.setText(String.valueOf(notifiList.get(position).getDescription()));


    }

    @Override
    public int getItemCount() {
        return notifiList.size();
    }


    public static final class NotifiViewHolder extends RecyclerView.ViewHolder{


        ImageView photos;
        TextView des, time;

        public NotifiViewHolder(@NonNull View itemView) {
            super(itemView);

            photos = itemView.findViewById(R.id.image);
            des = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.time);              }
    }

}
