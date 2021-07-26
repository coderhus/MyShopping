package com.example.myshopping.Other;

import android.content.Context;
import android.widget.Toast;

import com.example.myshopping.Model.Cart_item;
import com.example.myshopping.Model.Products;


import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertItem(Products item) {
        ArrayList<Products> listCard = getListCard();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listCard.size(); i++) {
            if (listCard.get(i).getId_products().equals(item.getId_products())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            listCard.get(n).setQuanity(item.getQuanity());
        } else {
            listCard.add(item);
        }

        tinyDB.putListObject("CardList", listCard);


    }

    public ArrayList<Products> getListCard() {
        return tinyDB.getListObject("CardList");
    }

    public void plusNumberFood(ArrayList<Products> cartItems, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        cartItems.get(position).setQuanity(cartItems.get(position).getQuanity() + 1);
        tinyDB.putListObject("CardList", cartItems);
        changeNumberItemsListener.changed();
    }

    public void MinusNumerFood(ArrayList<Products> cartItems, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (cartItems.get(position).getQuanity() == 1) {
            cartItems.remove(position);
        } else {
            cartItems.get(position).setQuanity(cartItems.get(position).getQuanity() - 1);
        }
        tinyDB.putListObject("CardList", cartItems);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<Products> cart_items = getListCard();
        double fee= 0;
        for (int i = 0; i < cart_items.size(); i++) {
            fee = fee + (cart_items.get(i).getPrice() * cart_items.get(i).getQuanity());
        }
        return fee;
    }

}
