/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailey_mediarentalsystem;

import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author dailey
 */
public abstract class Media {
    
    private final int ID;
    private String title;
    private int yearPublished;
    private Boolean available; // rental status defaulted to available (or true)
    
    protected Media(String title, int yearPublished, Boolean available) {
        ID = generateId(); //change id back to generate IS
        this.title = title;
        this.yearPublished = yearPublished;
        this.available = available;
    }
    
    protected Media(String title, int yearPublished) {
        this(title, yearPublished, true);
    }
    
    protected Double calculateRentalFee() { // TODO: calculate rental fee
        return 3.50;
    }
    
    public void changeTitle(String title) {
        this.title = title;
    }
    
    public void changeYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }
    
    public void setAvailablility(Boolean available) {
        this.available = available;
    }
    
    public Boolean isRented() {
        return available == false;
    }
    
    public int getId() {
        return ID;
    }
    
    public String getTitle() {
        return title;
    }
    
    public int getYearPublished() {
        return yearPublished;
    }
    
    public Boolean getAvailability() {
        return available;
    }
    
    public String displayAttributes() {
        return displayAttributes(true);
    }
    
    protected String displayAttributes(Boolean showAvailability) {
        return getClass().getFields().toString();
    }
    
    protected Boolean sameYear() {
        return yearPublished == Calendar.getInstance().get(Calendar.YEAR);
    }
    
    //Generates 5 digit ID
    private int generateId() {
        Random r = new Random();
        String randomId = ""; 
        int ID_LENGTH = 5;
        
        for (int i = 0; i < ID_LENGTH; i++) {
            int next = r.nextInt(10);
            if (next == 0) next = 1;
            randomId += next;
        }
        return Integer.valueOf(randomId);
    }
    
}
