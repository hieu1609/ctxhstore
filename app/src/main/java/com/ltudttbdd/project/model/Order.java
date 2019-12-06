package com.ltudttbdd.project.model;

import java.io.Serializable;

public class Order implements Serializable {
        public int idorder;
        public String name;
        public int count;
        public int price;
        public String date;
        public Order(int idorder, String name,int count, int price, String date) {
            this.idorder = idorder;
            this.name = name;
            this.count = count;
            this.price = price;
            this.date = date;
        }

        public int getIdorder() {
            return idorder;
        }

        public void setIdorder(int idorder) {
            this.idorder = idorder;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) { this.count = count; }

        public int getPrice(){
            return price;
        }
        public void setPrice(int price) { this.price = price;}



}
