
package com.example.myshopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopping.R;
import com.example.myshopping.Model.Products;


import java.util.List;


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

        holder.photos.setImageResource(categoryProductList.get(position).getPhotos());
        holder.name.setText(categoryProductList.get(position).getName());
        holder.price.setText(String.valueOf(categoryProductList.get(position).getPrice()+"$"));
        holder.rating.setText(categoryProductList.get(position).getId_products());
        holder.restorantName.setText(categoryProductList.get(position).getId_seller());

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
