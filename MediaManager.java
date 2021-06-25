/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailey_mediarentalsystem;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Class handles all data movement between media object classes and front end GUI
 * Consist of a list of media items that are handles within each instance of the GUI
 * An inner class is added as the file handler to control the flow of data to and
 * from the back-end directory 
 * @author dailey
 */
public class MediaManager {
    
    private FileHandler fh = new FileHandler();
    
    String default_File = "Media.csv";
    
    Boolean mediaLoaded = false;
    Boolean fileUpdated = false;
    Boolean mediaCreated = false;
    
    private List<Media> mediaInventory = new ArrayList<Media> (
            Arrays.asList(
                // TEST DATA
                new EBook("Title", 1999, 12, true),
                new MusicCD("Title", 1992, 4, true),
                new MovieDVD("Title", 1995, 400.50, false),
                new EBook("An EBook of Greatness", 1999, 12, true),
                new MusicCD("The Greatest CD", 1992, 4, true),
                new MovieDVD("Others Dont Compare", 1995, 400.50, false)
            )
    );
    
    //Used as test case to load test data 
    public void loadFromList() {
        for (Media m : mediaInventory) {
            fh.updateMediaFile(m, default_File, true);
        }
    }
    //Dispalys all inventory items in list
    public String displayInventory() {
        String inventory = "";
        for (int i = 0; i < mediaInventory.size(); i++ ) {
            inventory += mediaInventory.get(i).displayAttributes() + "\n";
        } 
        return inventory;
    }
    
    //User supplied user directory 
    public void loadMediaFromFile(String fileName) {
        default_File = fileName;
        fh.loadMediaFile(fileName);
    }
    
    public String rentMedia(String input) {
        int idInput = Integer.valueOf(input);
        
        int mediaIndex = findMedia(idInput);
        if ( mediaIndex > -1) {
            Media media = mediaInventory.get(mediaIndex);
            
            if (!media.isRented()) {
                media.setAvailablility(false);
                fh.updateMediaFile(media, default_File, true);
                return"Media Rental Confirmation:\n"
                        + media.displayAttributes(false) 
                        + "\n\nThank you come again!";
            } else {
                return "Media Titled \"" + media.getTitle() + "\" not available for rent";
            }
        } else {
            return "Media ID " + idInput + " not found in inventory";
        }
    }
    
    public void createNewMedia(String type, String title, int year, String other, Boolean available) {
        Media media = null;
        switch (type) {
            case "EB": 
                media = new EBook(title, year, Integer.valueOf(other), available);
                verifyNewMedia(media);
                break;
            case "CD":
                media = new MusicCD(title, year, Integer.valueOf(other), available);
                verifyNewMedia(media);
                break;
            case "DVD":
                media = new MovieDVD(title, year, Double.valueOf(other), available);
                verifyNewMedia(media);
                break;
            default:
        };
    }
    
    private void verifyNewMedia(Media media) {
        Boolean verified = false;
        if (media != null) {
            mediaInventory.add(media);
            
            if (mediaInventory.contains(media)) {
                mediaCreated = true;
                fh.updateMediaFile(media, default_File, true); //update CSV File 
            }
        }
    }
    
    private void overWriteMedia() {
        for (Media m : mediaInventory) {
            fh.updateMediaFile(m, default_File, true);
        }
    }
    
    //Checks inventory for title and returns all media items with title
    public String searchByTitle(String title) { 
        List<Media> items = new ArrayList<Media> ( Arrays.asList());
        int mediaIndex = findMedia(title);
        
        if ( mediaIndex > -1) {
            for (int i = 0; i < mediaInventory.size(); i++) {
                if (title.equals(mediaInventory.get(i).getTitle().toUpperCase())) {
                    items.add(mediaInventory.get(i));
                };
            }
            
            String s = "Media found with title: \n\"" + title + "\"\n";
            for (Media m : items) {
                s = s + m.displayAttributes() + "\n";
            }
            return s;
        } else {
            return "Media title \"" + title + "\" not found in inventory";
        }
    }
    
    private int findMedia(String title) {
        int size = mediaInventory.size();
        for (int i = 0; i < size; i++) {
            if (title.equals(mediaInventory.get(i).getTitle().toUpperCase()))  return i;
        }
        return -1;
    }
    
    private int findMedia(int id) {
        int size = mediaInventory.size();
        for (int i = 0; i < size; i++) {
            if (id == mediaInventory.get(i).getId()) return i;
        }
        return -1;
    }
    
    // Downgrade Flags to be recycled by instance
    
    public void downgradeMediaLoaded() {
        mediaLoaded = false;
    }
    
    public void downgradeMediaCreated() {
        mediaCreated = false;
    }
    
    public void downgradeFileUpdated() {
        fileUpdated = false;
    }
    
    //Inner class used to help organize file reading/writing functions
    private class FileHandler {
        
        //Load data from CSV file
        private void loadMediaFile(String fileName) {
            Path pathToFile = Paths.get(fileName);
            int originalSize = mediaInventory.size();
            int progressCount = 0;
            int successCount = 0;
                    
            try {    
                BufferedReader br = Files.newBufferedReader(pathToFile); 
                String line = br.readLine();
                while (line != null) {  
                    String[] attributes = line.split(",");
                    progressCount++;
                    
                    //first element holds media type to control creation of media
                    switch (attributes[0].toUpperCase()) { 
                        case "EB": 
                            mediaInventory.add(new EBook(attributes));
                            if (mediaInventory.size() == (originalSize + successCount) + 1) {
                                successCount++;
                            }
                            break;
                        case "CD":
                            mediaInventory.add(new MusicCD(attributes));
                            if (mediaInventory.size() == (originalSize + successCount) + 1) {
                                successCount++;
                            }
                            break;
                        case "DVD":
                            mediaInventory.add(new MovieDVD(attributes));
                            if (mediaInventory.size() == (originalSize + successCount) + 1) {
                                successCount++;
                            }
                            break;
                        default:
                    };
                    line = br.readLine();
                }           
                //progress is counted to throw an error if all media not loaded
                if (progressCount == successCount) mediaLoaded = true;
            } catch (IOException e) {  
                e.printStackTrace();
            }  
        }
        
        //Overwrites current media file or creates a file if not available
        private void updateMediaFile(Media media, String fileName, Boolean overwrite) {
            FileWriter fileWriter;
            
            try {
                fileWriter = new FileWriter(fileName, overwrite);
                BufferedWriter out = new BufferedWriter(fileWriter);
                //convert media object to csv string before writing
                out.write(convertToCSVData(media));
                out.newLine();
                out.flush();
                out.close();
                fileUpdated = true;
            } catch (IOException e) {
                System.out.println("File write error");
            }
        }
        
        //Convert media object to CSV for data upload
        private String convertToCSVData(Media media) {
            String mediaCSV_String = "";
            String delimiter = ",";
            String className = media.getClass().getSimpleName();
            
            if (className.equals("EBook")) {
                mediaCSV_String = "EB" + delimiter
                            + media.getTitle() + delimiter
                            + String.valueOf(media.getYearPublished()) + delimiter
                            + String.valueOf(((EBook) media).getNumOfChapters()) + delimiter
                            + String.valueOf(media.getAvailability());
            } else if (className.equals("MusicCD")) {
                mediaCSV_String = "CD" + delimiter
                            + media.getTitle() + delimiter
                            + String.valueOf(media.getYearPublished()) + delimiter
                            + String.valueOf(((MusicCD) media).getLength()) + delimiter
                            + String.valueOf(media.getAvailability());
            } else if (className.equals("MovieDVD")) {
                mediaCSV_String = "DVD" + delimiter
                            + media.getTitle() + delimiter
                            + String.valueOf(media.getYearPublished()) + delimiter
                            + String.valueOf(((MovieDVD) media).getSize()) + delimiter
                            + String.valueOf(media.getAvailability());
            } else {
              mediaCSV_String = "";
            }
            return mediaCSV_String;
        }
    }
    
}
