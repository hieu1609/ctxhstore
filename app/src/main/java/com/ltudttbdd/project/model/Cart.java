package com.ltudttbdd.project.model;

public class Cart {
    public int idproduct;
    public String nameproduct;
    public long priceproduct;
    public String imageproduct;
    public int numberproduct;

    public Cart(int idproduct, String nameproduct, long priceproduct, String imageproduct, int numberproduct) {
        this.idproduct = idproduct;
        this.nameproduct = nameproduct;
        this.priceproduct = priceproduct;
        this.imageproduct = imageproduct;
        this.numberproduct = numberproduct;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }

    public long getPriceproduct() {
        return priceproduct;
    }

    public void setPriceproduct(long priceproduct) {
        this.priceproduct = priceproduct;
    }

    public String getImageproduct() {
        return imageproduct;
    }

    public void setImageproduct(String imageproduct) {
        this.imageproduct = imageproduct;
    }

    public int getNumberproduct() {
        return numberproduct;
    }

    public void setNumberproduct(int numberproduct) {
        this.numberproduct = numberproduct;
    }
}
