package com.ltudttbdd.project.model;

import java.io.Serializable;

public class Notify implements Serializable {
    public int id;
    public String notifyTitle;
    public String notifyContent;
    public int seen;

    public Notify(int id, String notifyTitle, String notifyContent, int seen){
        this.id = id;
        this.notifyTitle = notifyTitle;
        this.notifyContent = notifyContent;
        this.seen = seen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotiTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotiTitle() { return notifyTitle; }

    public void setNotiContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public String getNotiContent() {
        return notifyContent;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }
}
