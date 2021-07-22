package com.example.myshopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopping.R;
import com.example.myshopping.activity.DetailsActivity;
import com.example.myshopping.model.Products;


import java.util.List;


public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.PopularProductViewHolder> {

    Context context;
    List<Products> popularProductList;



    public PopularProductAdapter(Context context, List<Products> popularProductList) {
        this.context = context;
        this.popularProductList = popularProductList;
    }

    @NonNull
    @Override
    public PopularProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.popular_product_row_item, parent, false);
        return new PopularProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductViewHolder holder, int position) {

        holder.photos.setImageResource(popularProductList.get(position).getPhotos());
        holder.name.setText(popularProductList.get(position).getName());
        holder.price.setText(popularProductList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailsActivity.class);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return popularProductList.size();
    }


    public static final class PopularProductViewHolder extends RecyclerView.ViewHolder{


        ImageView photos;
        TextView price, name;

        public PopularProductViewHolder(@NonNull View itemView) {
            super(itemView);

            photos = itemView.findViewById(R.id.product_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);



        }
    }

}
