package com.minh.getdata;

public class User {
    int id;
    String name, email, address, phone, website, company;

    public User(int id, String name, String email, String address, String phone, String website, String company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }
}
