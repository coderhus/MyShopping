package com.example.myshopping.Model;

public class MessageModel {
        String chatid,sender, receiver, message, date, type;
        public MessageModel() {
        }

    public MessageModel(String chatid, String sender, String receiver, String message, String date, String type) {
        this.chatid = chatid;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
        this.type = type;
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


