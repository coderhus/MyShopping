package com.example.myshopping.model;
public class Users {
    private String id;
    private String name;
    private long created_at; // lấy cái đổi time ở app trước (timestamp)
    private String gmail;
    private String phone;
    private String address;
    private int gender;
    private String birtday; // có thể đổi sang định dạng Date
    private String photo; // link ảnh photo up trên storage
    public Users(){

    }

    public Users(String id, String name, long created_at, String gmail, String phone, String address, int gender, String birtday, String photo) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.gmail = gmail;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.birtday = birtday;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirtday() {
        return birtday;
    }

    public void setBirtday(String birtday) {
        this.birtday = birtday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
