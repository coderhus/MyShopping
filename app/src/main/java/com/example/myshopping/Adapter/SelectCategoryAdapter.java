package com.example.myshopping.Adapter;

import android.app.Activity;
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
import com.example.myshopping.Activity.PushActivity;
import com.example.myshopping.Model.Category;
import com.example.myshopping.R;

import java.util.List;

public class SelectCategoryAdapter extends RecyclerView.Adapter<SelectCategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categoryList;

    public SelectCategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public SelectCategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_selcect_category, parent, false);
        return new SelectCategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCategoryAdapter.CategoryViewHolder holder, int position) {
        // holder.categoryImage.setImageResource(categoryList.get(position).getImageurl());
        // dùng glide để chèn ảnh từ firebase về
        Glide.with(context).load(categoryList.get(position).getImageurl()).into(holder.categoryImage);
        String cateName = categoryList.get(position).getName();
//        if(cateName.length()>=14)
//            cateName = cateName.substring(0,14)+"...";
        holder.categoryName.setText(cateName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent(context, PushActivity.class);
                mIntent.putExtra("category", categoryList.get(position).getName());
                context.startActivity(mIntent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public  static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryImage;
        TextView categoryName;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.imageView);
            categoryName = itemView.findViewById(R.id.textView);
        }
    }

}
