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
public class EBook extends Media {
    
    public int numOfChapters;
    
    public EBook(String title, int yearPublished, int numOfChapters, Boolean available) {
        super(title, yearPublished, available);
        this.numOfChapters = numOfChapters;
    }
    
    public EBook(String title, int yearPublished, int numOfChapters) {
        this(title, yearPublished, numOfChapters, true);
    }
    
    public EBook(String[] eBook) {
        this(eBook[1], Integer.valueOf(eBook[2]), Integer.valueOf(eBook[3]), Boolean.valueOf(eBook[4]));
    }
    
    public void setNumOfChapters(int numOfChapters) {
        this.numOfChapters = numOfChapters;
    }
    
    public int getNumOfChapters() {
        return numOfChapters;
    }
    
    public static int getNumOfChapters(Media media) {
        return ((EBook) media).getNumOfChapters();
    }
    
    @Override
    public Double calculateRentalFee() {
        Double fee = numOfChapters * 0.10;
        if (sameYear()) return fee + 1;
        return fee;
    }
    
    public String displayAttributes() {
        return displayAttributes(true);
    }
    
    @Override
    public String displayAttributes(Boolean showAvailability) {
        String s = "\n- EBook -" 
                + "\nMedia ID: " +  getId()
                + "\nTitle: " + getTitle() 
                + "\nPublished: " + getYearPublished()
                + "\nChapters: " + numOfChapters
                + "\nRental fee: $" + String.format("%.2f", calculateRentalFee());
        
        if(showAvailability) {
            s = s + "\nAvailability: "+ (isRented() ? "NOT AVAILABLE" : "Available for Rent");
        }
        return s;
    }
    
}
