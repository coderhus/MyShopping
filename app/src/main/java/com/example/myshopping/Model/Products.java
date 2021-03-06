package com.example.myshopping.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Products implements Serializable {
    private String id_products;
    private String id_seller;
    private String name;
    private String name_seller;
    private String describe;
    private double price;
    private int quanity;
    private String photos;

    public Products(){

    }

    public Products(String id_products, String id_seller, String name, String name_seller, String describe, double price, int quanity, String photos) {
        this.id_products = id_products;
        this.id_seller = id_seller;
        this.name = name;
        this.name_seller = name_seller;
        this.describe = describe;
        this.price = price;
        this.quanity = quanity;
        this.photos = photos;
    }

    public String getName_seller() {
        return name_seller;
    }

    public void setName_seller(String name_seller) {
        this.name_seller = name_seller;
    }

    public void addQuanity(int quanity){
        this.quanity+=quanity;
    }
    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public String getId_products() {
        return id_products;
    }

    public void setId_products(String id_products) {
        this.id_products = id_products;
    }

    public String getId_seller() {
        return id_seller;
    }

    public void setId_seller(String id_seller) {
        this.id_seller = id_seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
}
