package com.ltudttbdd.project.model;

import java.util.ArrayList;

public class Notify {
    public String notifyTitle;
    public String notifyContent;
    public int seen;

    public Notify(String notifyTitle, String notifyContent, int seen){
        this.notifyTitle = notifyTitle;
        this.notifyContent = notifyContent;
        this.seen = seen;
    }

    public String getNotiTitle() { return notifyTitle; }

    public String getNotiContent() {
        return notifyContent;
    }
}
