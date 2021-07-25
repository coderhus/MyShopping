package com.example.myshopping.Model;

import java.io.Serializable;

public class Cart_item implements Serializable {

    private String id_orders;
    private Products products;
    private int numberInCart;
    private int sale;


    public Cart_item( Products products) {
        this.products = products;
        this.sale = 0;
    }
    public Cart_item(String id_orders, Products products, int numberInCart) {
        this.id_orders = id_orders;
        this.products = products;
        this.numberInCart = numberInCart;
        this.sale = 0;
    }

    public String getId_orders() {
        return id_orders;
    }

    public void setId_orders(String id_orders) {
        this.id_orders = id_orders;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}
