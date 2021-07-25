package com.example.myshopping.Model;

import java.util.LinkedList;
import java.util.List;

public class Cart {
    private String id_user;
    private int count; // đếm số lượng đồ trong giỏ hàng, có thể có hoặc k vì đếm đc theo
    // list id_product ở dưới
    private List<String> id_product = new LinkedList<>();
    public Cart(){

    }
    public Cart(String id_user, int count, List<String> id_product) {
        this.id_user = id_user;
        this.count = count;
        this.id_product = id_product;
    }
    // trường hợp add từng cái id_product vào ( nút cho vào giỏ hàng)
    public Cart(String id_user,String id_product) {
        this.id_user = id_user;
        this.id_product.add(id_product);
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

    public List<String> getId_product() {
        return id_product;
    }

    public void setId_product(List<String> id_product) {
        this.id_product = id_product;
    }
}

