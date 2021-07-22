package com.example.myshopping.model;
import java.util.LinkedList;
import java.util.List;

public class Orders_item {
    private String id_orders;
    private List<Products> products = new LinkedList<>();
    public Orders_item(){

    }
    public Orders_item(String id_orders, List<Products> products) {
        this.id_orders = id_orders;
        this.products = products;
    }

    public String getId_orders() {
        return id_orders;
    }

    public void setId_orders(String id_orders) {
        this.id_orders = id_orders;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
