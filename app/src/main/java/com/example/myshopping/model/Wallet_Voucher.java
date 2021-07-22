package com.example.myshopping.model;

import java.util.LinkedList;
import java.util.List;

public class Wallet_Voucher {
    private String id_user;
    private double money;
    private List<String> listVoucher = new LinkedList<>();

    public Wallet_Voucher(){

    }
    public Wallet_Voucher(String id_user, double money, List<String> listVoucher) {
        this.id_user = id_user;
        this.money = money;
        this.listVoucher = listVoucher;
    }
    //Trường hợp người dùng add voucher vào thì gọi hàm này
    public Wallet_Voucher(String id_user,String listVoucher) {
        this.id_user = id_user;
        this.listVoucher.add(listVoucher);
    }
    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<String> getListVoucher() {
        return listVoucher;
    }

    public void setListVoucher(List<String> listVoucher) {
        this.listVoucher = listVoucher;
    }
}
