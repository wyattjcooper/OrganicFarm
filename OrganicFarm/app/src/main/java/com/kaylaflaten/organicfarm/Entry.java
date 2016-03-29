package com.kaylaflaten.organicfarm;

/**
 * Created by WyattCooper on 2/16/16.
 */
public class Entry {
    private String date;
    private String name;
    private String notes;
    private double amount;
    public Entry() {}

    public Entry(String name, String date, String notes, double amount) {
        this.name = name;
        this.date = date;
        this.notes = notes;
        this.amount = amount;
    }

    public String getDate() { return date;}
    public String getName() { return name; }
    public String getNotes() {return notes;}

    public void setDate(String d) { date = d;}
    public void setName(String n) { name = n; }
    public void setNotes(String no) { notes = no;}

}