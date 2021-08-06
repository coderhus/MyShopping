
package com.example.myshopping.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshopping.Activity.DetailsActivity;
import com.example.myshopping.Model.Rate;
import com.example.myshopping.R;
import com.example.myshopping.Model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder> {

    Context context;
    List<Products> categoryProductList;



    public CategoryProductAdapter(Context context, List<Products> categoryProductFoodList) {
        this.context = context;
        this.categoryProductList = categoryProductFoodList;
    }

    @NonNull
    @Override
    public CategoryProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_category_row_item, parent, false);
        return new CategoryProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryProductViewHolder holder, int position) {
        Glide.with(context).load(categoryProductList.get(position).getPhotos()).into(holder.photos);
        //holder.photos.setImageResource(categoryProductList.get(position).getPhotos());
        holder.name.setText(categoryProductList.get(position).getName());
        holder.price.setText(String.valueOf(categoryProductList.get(position).getPrice()+"$"));
        //holder.rating.setText(categoryProductList.get(position).getId_products());
        //holder.rating.setText("5");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rate").child(categoryProductList.get(position).getId_products());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Rate rate = snapshot.getValue(Rate.class);
                if(rate.getCount()==0) {
                    holder.rating.setText("Chưa có đánh giá");
                }
                else{
                    holder.rating.setText(String.valueOf(rate.getRate()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.restorantName.setText(categoryProductList.get(position).getName_seller());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("object",  categoryProductList.get(position));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryProductList.size();
    }


    public static final class CategoryProductViewHolder extends RecyclerView.ViewHolder{


        ImageView photos;
        TextView price, name, rating, restorantName;

        public CategoryProductViewHolder(@NonNull View itemView) {
            super(itemView);

            photos = itemView.findViewById(R.id.product_image);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            restorantName = itemView.findViewById(R.id.restorant_name);



        }
    }

}
