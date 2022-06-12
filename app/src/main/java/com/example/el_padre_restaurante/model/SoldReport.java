package com.example.el_padre_restaurante.model;

public class SoldReport {
    String username;
    float price;
    String name;

    public SoldReport(String username, float price, String name) {
        this.username = username;
        this.price = price;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Username: " + username + '\n' +
                "Price: " + price +'\n' +
                "Name: " + name +'\n';
    }
}
