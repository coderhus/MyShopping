package com.example.myshopping.Other;

import android.content.Context;
import android.widget.Toast;

import com.example.myshopping.Model.Cart_item;
import com.example.myshopping.Model.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef1 = database.getReference("Cart").child(user.getUid());
    DatabaseReference myRef = database.getReference("Cart").child(user.getUid()).child("list_Products");
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
        Products temp = cartItems.get(position);
        temp.addQuanity(1);
        cartItems.get(position).setQuanity(temp.getQuanity());
        myRef.child(temp.getId_products()).setValue(temp);
        tinyDB.putListObject("CardList", cartItems);
        changeNumberItemsListener.changed();
    }

    public void MinusNumerFood(ArrayList<Products> cartItems, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        Products temp = cartItems.get(position);
        if (temp.getQuanity() == 1) {
            int count = cartItems.size();
            myRef1.child("count").setValue(count-1);
            myRef.child(temp.getId_products()).setValue(null);
            cartItems.remove(position);
        } else {
            temp.addQuanity(-1);
            cartItems.get(position).setQuanity(temp.getQuanity());
            myRef.child(cartItems.get(position).getId_products()).setValue(temp);
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
