package com.example.myshopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myshopping.Model.Cart_item;
import com.example.myshopping.Other.ChangeNumberItemsListener;
import com.example.myshopping.Other.ManagementCart;
import com.example.myshopping.R;
import java.util.ArrayList;


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private ArrayList<Cart_item> cartItems;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;


    public CartListAdapter(ArrayList<Cart_item> cartItems, Context context, ChangeNumberItemsListener changeNumberItemsListener) {

        this.cartItems = cartItems;
        managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(cartItems.get(position).getProducts().getName());
        holder.priceItem.setText(String.valueOf(cartItems.get(position).getProducts().getPrice()));
        holder.totalPriceItem.setText(String.valueOf(Math.round((cartItems.get(position).getProducts().getNumberInCart() * cartItems.get(position).getProducts().getPrice()) * 100.0) / 100.0));
        holder.num.setText(String.valueOf(cartItems.get(position).getProducts().getNumberInCart()));
        holder.pic.setImageResource(cartItems.get(position).getProducts().getPhotos());

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.MinusNumerFood(cartItems, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });


        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberFood(cartItems, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, priceItem;
        ImageView pic, plusItem, minusItem;
        TextView totalPriceItem, num;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title2Txt);
            priceItem = itemView.findViewById(R.id.txtPriceItem);
            pic = itemView.findViewById(R.id.picCard);
            totalPriceItem = itemView.findViewById(R.id.txtTotalPriceItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCardBtn);
            minusItem = itemView.findViewById(R.id.minusCardBtn);
        }
    }
}

