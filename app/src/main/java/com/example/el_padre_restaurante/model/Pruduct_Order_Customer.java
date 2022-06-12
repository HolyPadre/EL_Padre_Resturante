package com.example.el_padre_restaurante.model;

import java.util.ArrayList;

public class Pruduct_Order_Customer {
    private  int id;
    private  String name;
    private  String imageURL;


    public  void addPruduct(int id,String name, String imageURL ){

    }

    public Pruduct_Order_Customer(int id,String name, String imageURL) {
        this.id = id;
        this.name = name;

        this.imageURL = imageURL;

    }

    public  int getId() {
        return id;
    }

    public  String getName() {
        return name;
    }



    public  String getImageURL() {
        return imageURL;
    }
}