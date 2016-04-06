package com.kaylaflaten.organicfarm;

/**
 * Created by WyattCooper on 4/5/16.
 */
public class User {
    private String email;
    private String firstName;
    private String lastName;
    int admin;

    public User(String em, String fn, String ln, int ad){
        email = em;
        firstName = fn;
        lastName = ln;
        admin = ad;
    }

    public void setFirstName(String fn){firstName = fn;}
    public void setLastName(String ln){lastName = ln;}
    public void setEmail(String em){email = em;}
    public void setAdmin(int ad){admin = ad;}


    public String getEmail(){return email;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public int getAdmin(){return admin;}


}
