package com.example.el_padre_restaurante.model;

public class DelivarOrder {
    private int id;
    private String username;
    private String address;
    private float price;
    private String name;
    private int c_id;
    private int p_id;

    public DelivarOrder(int id,String username, String address, float price, String name,int u_id,int p_id) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.price = price;
        this.name = name;
        this.c_id = u_id;
        this.p_id = p_id;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }
}
