package com.example.myshopping.model;

public class    Category {
    private String id;
    private String name;
    private String id_parents;
    Integer imageurl;
    private int gender;   //0 la cho ca 2, //1 la cho nam, //-1 la cho nu
    public Category(){

    }
    public Category( String name, String id_parents, int gender,Integer imageurl) {
        this.id = id_parents+"."+name;
        this.name = name;
        this.id_parents = id_parents;
        this.gender = gender;
        this.imageurl =imageurl;
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

    public String getId_parents() {
        return id_parents;
    }

    public void setId_parents(String id_parents) {
        this.id_parents = id_parents;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Integer getImageurl() {
        return imageurl;
    }

    public void setImageurl(Integer imageurl) {
        this.imageurl = imageurl;
    }
}
