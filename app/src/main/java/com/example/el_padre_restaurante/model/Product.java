package com.example.el_padre_restaurante.model;

public class Product {
    private int id;
    private String name;
    private String category;
    private String description;
    private float price;
    private String image;

    public Product(int id, String name, String category, String description, float price, String image) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public float getPrice() {return price;}

    public void setPrice(float price) {this.price = price;}

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}

    public String getDis() {
        return
                "Name:" + name + '\n' +
                "Category: " + category + '\n' +
                "Description: " + description + '\n' +
                "Price:" + price;
    }
}
