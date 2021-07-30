package com.example.myshopping.Model;

public class Chat {

    String chatID,hisid, hisname, lastMessage, image, date;

    public Chat() {
    }

    public Chat(String chatID, String hisid, String hisname, String lastMessage, String image, String date) {
        this.chatID = chatID;
        this.hisid = hisid;
        this.hisname = hisname;
        this.lastMessage = lastMessage;
        this.image = image;
        this.date = date;
    }

    public String getHisid() {
        return hisid;
    }

    public void setHisid(String hisid) {
        this.hisid = hisid;
    }

    public String getHisname() {
        return hisname;
    }

    public void setHisname(String hisname) {
        this.hisname = hisname;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }
    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
