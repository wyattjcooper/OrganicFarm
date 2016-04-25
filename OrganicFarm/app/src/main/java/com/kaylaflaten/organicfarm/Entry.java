package com.kaylaflaten.organicfarm;

/**
 * Entry Object - a crop planted in a bed
 */
public class Entry {
    private String date;
    private String name;
    private String notes;
    private String owner;
    private String harvestDate;
    private boolean finished;
    int section;
    int bed;
    public Entry() {}

    public Entry(String name, String date,String harvestDate, String notes, String owner, boolean finished, int section, int bed) {
        this.name = name;
        this.date = date;
        this.harvestDate = harvestDate;
        this.notes = notes;
        this.finished = finished;
        this.bed = bed;
        this.section = section;
        this.owner = owner;
    }

    public String getDate() { return date;}
    public String getOwner() { return owner;}
    public String getHarvestDate() {return harvestDate;}
    public String getName() { return name; }
    public String getNotes() {return notes;}
    public int getSection() {return section;}
    public int getBed() {return bed;}
    public boolean getFinished() {return finished;}

    public void setDate(String d) { date = d;}
    public void setHarvestDate(String hd) { harvestDate = hd;}
    public void setName(String n) { name = n; }
    public void setOwner(String o) { owner = o; }
    public void setNotes(String no) { notes = no;}
    public void setSection(int se) {section = se;}
    public void setBed(int be) {bed = be;}
    public void setFinished(boolean t) {finished = t;}


}