package com.example.myshopping.Model;

public class Category {
    private String id;
    private String name;
    private String id_parents;
    String imageurl;
    private int gender;   //0 la cho ca 2, //1 la cho nam, //-1 la cho nu
    public Category(){

    }
    public Category( String name, String id_parents, int gender,String imageurl) {
        if(id_parents!="")
        this.id = id_parents+"_"+name;
        else{
            this.id=name;
        }
        this.name = name;
        this.id_parents = id_parents;
        this.gender = gender;
        this.imageurl =imageurl;
    }

    public Category(String name, String id_parents, int gender, int ic_home_fish) {
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

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
