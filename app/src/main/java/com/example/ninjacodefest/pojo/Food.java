package com.example.ninjacodefest.pojo;

public class Food {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Food() {
    }

    public Food(String name, String imageurl, String price, String status) {
        this.name = name;
        this.imageurl = imageurl;
        this.price = price;
        this.status = status;
    }

    String name;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    String imageurl;
    String price;
    String status;
}
