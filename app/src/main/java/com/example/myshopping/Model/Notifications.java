package com.example.myshopping.Model;

public class Notifications {
    
    private String time;
    private String description;
    private int type;
    private String id;
    private String photo;

    public Notifications() {
    }

    public Notifications(String time, String description, int type, String id, String photo) {
        this.time = time;
        this.description = description;
        this.type = type;
        this.id = id;
        this.photo = photo;
    }

    public Notifications(String time, String description, int type, String id, int profile) {
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
