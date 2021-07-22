package com.example.myshopping.model;

import java.util.LinkedList;
import java.util.List;

public class Voucher {
    private String id_voucher;
    private List<String> id_category = new LinkedList<>(); // giảm giá ngành hàng nào(chưa biết
    // xử lý giảm giá all
    private double discount;
    // private Date date; hạn sử dụng
    public Voucher(){

    }

    public Voucher(String id_voucher, List<String> id_category, double discount) {
        this.id_voucher = id_voucher;
        this.id_category = id_category;
        this.discount = discount;
    }

    public String getId_voucher() {
        return id_voucher;
    }

    public void setId_voucher(String id_voucher) {
        this.id_voucher = id_voucher;
    }

    public List<String> getId_category() {
        return id_category;
    }

    public void setId_category(List<String> id_category) {
        this.id_category = id_category;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
