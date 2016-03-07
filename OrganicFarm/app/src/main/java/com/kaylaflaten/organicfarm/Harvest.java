package com.kaylaflaten.organicfarm;

/**
 * Created by WyattCooper on 3/7/16.
 */
public class Harvest {
    private String date;
    private Double amount;
    private boolean finished;
    private String notes;
    public int section;
    public int bed;

    public Harvest() {}
    public Harvest(String date1, Double amount1, boolean finished1, String notes1, int section1, int bed1 ) {
        date = date1;
        amount = amount1;
        finished = finished1;
        notes = notes1;
        section = section1;
        bed = bed1;
    }

    public String getDate() { return date;}
    public Double getAmount() {return amount;}
    public boolean getFinished() {return finished;}
    public String getNotes() {return notes;}
    public int getSection() {return section;}
    public int getBed() {return bed;}

}
