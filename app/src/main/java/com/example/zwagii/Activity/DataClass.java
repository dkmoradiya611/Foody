package com.example.zwagii.Activity;

import java.io.Serializable;

public class DataClass implements Serializable {

    private String title;

    private String imageURL;
    private String caption;
   // private String price;


    private String price;
    private int time;
    private int energy;
    private String score;
    private int numberInCart;

    public DataClass() {
    }

    public DataClass(String title, String imageURL, String caption, String price, int time, int energy, String score) {
        this.title = title;
        this.imageURL = imageURL;
        this.caption = caption;
        this.price = price;
        this.time = time;
        this.energy = energy;
        this.score = score;
        //this.numberInCart = numberInCart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
