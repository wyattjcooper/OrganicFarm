package com.kaylaflaten.organicfarm;

/**
 * Created by WyattCooper on 2/16/16.
 */
public class Entry {
    private String date;
    private String name;
    private String notes;
    public Entry() {}
    public Entry(String name, String date, String notes) {
        this.name = name;
        this.date = date;
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }
    public String getName() { return name; }
    public String getNotes() {
        return notes;
    }
}
