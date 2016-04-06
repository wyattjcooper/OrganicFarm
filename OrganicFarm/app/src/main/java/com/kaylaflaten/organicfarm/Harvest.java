package com.kaylaflaten.organicfarm;

/**
 * Created by WyattCooper on 3/7/16.
 */
public class Harvest {
    private String date;
    private Double amount;
    private String notes;
    private String name;
    private String pid;
    private String owner;
    String image;
    public int section;
    public int bed;

    public Harvest() {}
    public Harvest(String name1, String parentID1, String date1, String owner1, Double amount1, String notes1, int section1, int bed1 ) {
        name = name1;
        pid = parentID1;
        date = date1;
        amount = amount1;
        notes = notes1;
        section = section1;
        bed = bed1;
        owner = owner1;
    }

    public String getName() { return name;}
    public String getPID() { return pid;}
    public String getDate() { return date;}
    public String getOwner() { return owner;}
    public String getImage() {return image;}
    public Double getAmount() {return amount;}
    public String getNotes() {return notes;}
    public int getSection() {return section;}
    public int getBed() {return bed;}

    public void setName(String name1) { name = name1;}
    public void setImage(String image1) {image = image1;}
    public void setPID(String parentID1) { pid = parentID1;}
    public void setOwner(String o) { owner = o;}
    public void setDate(String d) { date = d;}
    public void setAmount(double a) {amount = a;}
    public  void setNotes(String no) {notes = no;}
    public  void setSection(int s) {section = s;}
    public void setBed(int b) {bed = b;}
}
