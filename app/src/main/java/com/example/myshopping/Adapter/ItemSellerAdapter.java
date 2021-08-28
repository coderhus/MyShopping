package com.example.myshopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopping.Model.Products;
import com.example.myshopping.R;

import java.util.List;

public class ItemSellerAdapter extends RecyclerView.Adapter<ItemSellerAdapter.ItemViewHolder>{

    Context context;
    List<Products> list;

    public ItemSellerAdapter(Context context, List<Products> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_seller, parent, false);
        return new ItemSellerAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemSellerAdapter.ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  static class ItemViewHolder extends RecyclerView.ViewHolder{
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
