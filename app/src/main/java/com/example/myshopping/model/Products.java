package com.example.myshopping.model;

public class Products {
    private String id_products;
    private String id_seller;
    private String name;
    private String describe;
    private String price;
    private Integer photos;

    public Products(){

    }
    public Products(String id_products, String id_seller, String name, String describe, String price, int photos) {
        this.id_products = id_products;
        this.id_seller = id_seller;
        this.name = name;
        this.describe = describe;
        this.price = price;
        this.photos = photos;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPhotos(Integer photos) {
        this.photos = photos;
    }

    public Integer getPhotos() {
        return photos;
    }


}
