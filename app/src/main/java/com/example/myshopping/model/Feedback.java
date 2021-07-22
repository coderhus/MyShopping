package com.example.myshopping.model;


public class Feedback {
    private String id_product;
    private Comment comment;
    private double rate; // 0 -> 5
    public Feedback(){

    }
    public Feedback(String id_product, Comment comment, double rate) {
        this.id_product = id_product;
        this.comment = comment;
        this.rate = rate;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
