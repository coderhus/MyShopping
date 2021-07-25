package com.example.myshopping.Model;
public class Category_item {
    private String id_category;
    private String id_product;
    public Category_item(){

    }
    public Category_item(String id_category, String id_product) {
        this.id_category = id_category;
        this.id_product = id_product;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }
}
