package com.example.el_padre_restaurante.model;

import java.util.ArrayList;

public class UserReport {
    private int id;
    private String username;

    public UserReport(int id, String username) {
        this.id = id;
        this.username = username;
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

    @Override
    public String toString() {
        return
                "ID: " + id +'\n' +
                "Username: " + username;
    }
}
