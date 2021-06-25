/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailey_mediarentalsystem;

import java.util.Calendar;

/**
 *
 * @author dailey
 */
public class MovieDVD extends Media {
    
    private double size;
    
    public MovieDVD(String title, int yearPublished, Double size, Boolean available) {
        super(title, yearPublished, available);
        this.size = size;
    }
    
    public MovieDVD(String title, int yearPublished, Double size) {
        this(title, yearPublished, size, true);
    }
    
    public MovieDVD(String[] dvd) {
        this(dvd[1], Integer.valueOf(dvd[2]), Double.valueOf(dvd[3]), Boolean.valueOf(dvd[4]));
    }
    
    public void setSize(Double size) {
        this.size = size;
    }
    
    public Double getSize() {
        return size;
    }
    
    public static Double getSize(Media media) {
        return ((MovieDVD) media).getSize();
    }
    
    public String displayAttributes() {
        return displayAttributes(true);
    }
    
    @Override
    public String displayAttributes(Boolean showAvailability) {
        String s = "\n- Movie DVD -" 
                + "\nMedia ID: " +  getId()
                + "\nTitle: " + getTitle() 
                + "\nPublished: " + getYearPublished()
                + "\nSize: " + size + "MB"
                + "\nRental fee: $" + String.format("%.2f", calculateRentalFee());
        
        if(showAvailability) {
            s = s +"\nAvailability: "+ (isRented() ? "NOT AVAILABLE" : "Available for Rent");
        }
        return  s;
    }
    
}
