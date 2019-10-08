package com.ltudttbdd.project.model;

import java.io.Serializable;

public class Product implements Serializable {
    public int id;
    public String productName;
    public int price;
    public String productImage;
    public String description;
    public int idCategory;

    public Product(int id, String productName, int price, String productImage, String description, int idCategory) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
        this.description = description;
        this.idCategory = idCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
