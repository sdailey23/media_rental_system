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
public class MusicCD extends Media {
    
    private int length;
    
    public MusicCD(String title, int yearPublished, int length, Boolean available) {
        super(title, yearPublished, available);
        this.length = length;
    }
    
    public MusicCD(String title, int yearPublished, int length) {
        this(title, yearPublished, length, true);
    }
    
    public MusicCD(String[] cd) {
        this(cd[1], Integer.valueOf(cd[2]), Integer.valueOf(cd[3]), Boolean.valueOf(cd[4]));
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public int getLength() {
        return length;
    }
    
    public static int getLength(Media media) {
        return ((MusicCD) media).getLength();
    }
    
    @Override
    public Double calculateRentalFee() {
        Double fee = length * 0.10;
        if (sameYear()) return fee + 1;
        return fee;
    }
    
    public String displayAttributes() {
        return displayAttributes(true);
    }
    
    @Override
    public String displayAttributes(Boolean showAvailability) {
        String s = "\n- Music CD -" 
                + "\nMedia ID: " +  getId()
                + "\nTitle: " + getTitle() 
                + "\nPublished: " + getYearPublished()
                + "\nLength " + length + "min "
                + "\nRental fee: $" + String.format("%.2f", calculateRentalFee());
        
        if(showAvailability) {
            s = s +"\nAvailability: "+ (isRented() ? "NOT AVAILABLE" : "Available for Rent");
        }
        return s;
    }
    
}
