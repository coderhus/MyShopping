package com.example.myshopping.Other;

import android.content.Context;
import android.widget.Toast;

import com.example.myshopping.Model.Cart_item;



import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertItem(Cart_item item) {
        ArrayList<Cart_item> listCard = getListCard();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listCard.size(); i++) {
            if (listCard.get(i).getProducts().getId_products().equals(item.getProducts().getId_products())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            listCard.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listCard.add(item);
        }

        tinyDB.putListObject("CardList", listCard);
        Toast.makeText(context, "Added To Your Card", Toast.LENGTH_SHORT).show();

    }

    public ArrayList<Cart_item> getListCard() {
        return tinyDB.getListObject("CardList");
    }

    public void plusNumberFood(ArrayList<Cart_item> cartItems, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        cartItems.get(position).setNumberInCart(cartItems.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CardList", cartItems);
        changeNumberItemsListener.changed();
    }

    public void MinusNumerFood(ArrayList<Cart_item> cartItems, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (cartItems.get(position).getNumberInCart() == 1) {
            cartItems.remove(position);
        } else {
            cartItems.get(position).setNumberInCart(cartItems.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CardList", cartItems);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<Cart_item> cart_items = getListCard();
        double fee= 0;
        for (int i = 0; i < cart_items.size(); i++) {
            fee = fee + (cart_items.get(i).getProducts().getPrice() * cart_items.get(i).getNumberInCart());
        }
        return fee;
    }

}
