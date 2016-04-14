package com.kaylaflaten.organicfarm;

/**
 * Created by Kayla Flaten on 4/13/2016.
 */
public class ToDoEntry {

    private String title;
    private String date;
    private String notes;
    private boolean finished;

    public ToDoEntry() { }

    public ToDoEntry(String title, String date, String notes, boolean finished) {
        this.title = title;
        this.date = date;
        this.notes = notes;
        this.finished = finished;
    }

    public String getTitle() {return title;}
    public String getDate() {return date;}
    public String getNotes() {return notes;}

    public void setTitle(String t) {title = t;}
    public void setDate(String d) {date = d;}
    public void setNotes(String n) {notes = n;}
    public void setFinished(boolean tr) {finished = tr;}
}
