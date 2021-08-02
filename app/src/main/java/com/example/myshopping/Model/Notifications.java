package com.example.myshopping.Model;

public class Notifications {
    
    private String time;
    private String description;
    private int type;
    private String id;
    private  Integer photo;

    public Notifications(String time, String description, int type, String id, Integer  photo) {
        this.time = time;
        this.description = description;
        this.type = type;
        this.id = id;
        this.photo = photo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer  getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }
}
