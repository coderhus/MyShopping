package com.example.myshopping.Model;

public class Rate {
    int count=0;
    double rate=0;
    public Rate(){

    }
    public Rate(int count, double rate) {
        this.count = count;
        this.rate = rate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    public void updateRate(double new_rate){
        if(rate!=-1) {
            double sum = this.rate * this.count + new_rate;
            this.count++;
            this.rate = sum / count;
            this.rate = Math.round((this.rate * 100) / 100);
        }
    }
}
