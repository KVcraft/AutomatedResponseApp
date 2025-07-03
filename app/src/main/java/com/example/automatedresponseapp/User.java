package com.example.automatedresponseapp;

public class User {
    public String customerName, email, farmName, username, password, contact;

    public User() {
    }

    public User(String customerName, String email, String farmName, String username, String password, String contact) {
        this.customerName = customerName;
        this.email = email;
        this.farmName = farmName;
        this.username = username;
        this.password = password;
        this.contact = contact;
    }
}
