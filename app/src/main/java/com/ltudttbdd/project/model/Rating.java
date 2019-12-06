package com.ltudttbdd.project.model;

import java.io.Serializable;

public class Rating implements Serializable {
        public int idproduct;
        public String iduser;
        public String comment;
        public float rate;

        public Rating(int idproduct, String iduser, String comment, float rate) {
            this.idproduct = idproduct;
            this.iduser = iduser;
            this.comment = comment;
            this.rate = rate;
        }

        public int getIdproduct() {
            return idproduct;
        }

        public void setIdproduct(int idproduct) {
            this.idproduct = idproduct;
        }

        public String getIduser() {
            return iduser;
        }

        public void setIduser(String iduser) {
            this.iduser = iduser;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public float getRate() {
        return rate;
    }

        public void setRate(float rate) { this.rate = rate; }
    }


