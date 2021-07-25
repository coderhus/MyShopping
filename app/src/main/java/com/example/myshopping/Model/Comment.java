package com.example.myshopping.Model;

public class Comment {
    private String id_user;
    private String text;
    private String time; // lay theo thoi gian thuc te tren app khi dang
    // private String photo // thêm ảnh đánh giá nếu làm đc

    public Comment(){

    }
    public Comment(String id_user, String text, String time) {
        this.id_user = id_user;
        this.text = text;
        this.time = time;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
