package com.ltudttbdd.project.model;

public class User {
    public int id;
    public String email;
    public String name;
    public String phone;
    public String address;

    public User(int _id, String _email, String _name, String _phone, String _address) {
        this.id = _id;
        this.email = _email;
        this.name = _name;
        this.phone = _phone;
        this.address = _address;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String priceproduct) {
        this.email = priceproduct;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String imageproduct) {
        this.phone = imageproduct;
    }

    public String getAddress() {  return address; }

    public void setAddress(String _address) {
        this.address = _address;
    }
}
