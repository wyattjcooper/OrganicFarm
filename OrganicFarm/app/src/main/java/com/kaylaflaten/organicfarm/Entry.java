package com.kaylaflaten.organicfarm;

/**
 * Created by WyattCooper on 2/16/16.
 */
public class Entry {
    private String date;
    private String name;
    private String text;
    public Entry() {}
    public Entry(String name, String date, String text) {
        this.name = name;
        this.date = date;
        this.text = text;
    }

    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public String getText() {
        return text;
    }
}

