package com.example.myshopping.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Cart {
    private String id_user;
    private int count; // đếm số lượng đồ trong giỏ hàng, có thể có hoặc k vì đếm đc theo
    // list id_product ở dưới
    private HashMap<String, Products> list_Products = new HashMap<String, Products>();
  //  private List<Products> list_Products = new LinkedList<>();
    public Cart(){

    }
    public Cart(String id_user, int count, HashMap<String, Products> list_Products) {
        this.id_user = id_user;
        this.count = count;
        this.list_Products = list_Products;
    }
    // trường hợp add từng cái id_product vào ( nút cho vào giỏ hàng)
    public Cart(String id_user,Products product) {
        this.id_user = id_user;
        this.list_Products.put(product.getId_products(),product);
    }
    // trường hợp add từng cái id_product vào ( nút cho vào giỏ hàng)
    public void addProduct(Products product){
        this.list_Products.put(product.getId_products(),product);
        this.count++;
    }
    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HashMap<String, Products> getList_Products() {
        return list_Products;
    }

    public void setId_product(HashMap<String, Products> list_Products) {
        this.list_Products = list_Products;
    }
}

