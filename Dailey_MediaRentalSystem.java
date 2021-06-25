/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailey_mediarentalsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author dailey
 */
public class Dailey_MediaRentalSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MediaManager mm = new MediaManager();
        
        // MAIN WINDOW SETUP
        JFrame window = new JFrame("Media Rental System");
        JPanel main = new JPanel(); //creates "recycalable" window w/ border
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(50, 75, 75, 75));
        
        //Main Menu Window Sections
        JPanel headerMenu = new JPanel();
        JPanel mainMenu = new JPanel();
        JPanel footerMenu = new JPanel();
        //Exit button set here to be recycled by all view panels
        JButton exitButton = new JButton("Exit");
        
        // HEADER SECTION
         
        //Header section components, layouts, constraints & action listener(s)
        JPanel headerView = new JPanel();
        JTextField userSearchButton = new JTextField();
        JButton searchButton = new JButton("Search Media Title");
        JButton displayInventButton = new JButton("View Media Inventory");
        displayInventButton.setAlignmentX(displayInventButton.CENTER_ALIGNMENT);
        
        headerView.setLayout(new BoxLayout(headerView, BoxLayout.X_AXIS));
        headerView.setAlignmentY(headerView.CENTER_ALIGNMENT);
        headerView.add(searchButton);
        headerView.add(userSearchButton);
        
        headerMenu.setLayout(new BoxLayout(headerMenu, BoxLayout.Y_AXIS));
        headerMenu.add(headerView);
        headerMenu.add(displayInventButton);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = userSearchButton.getText().trim().toUpperCase();
                JOptionPane.showMessageDialog(
                        window, 
                        (mm.searchByTitle(s))
                );
                userSearchButton.setText(null);
            }
        });
        
        main.add(headerMenu);
        
        //Display Inventory Panel
        
        JPanel inventoryView = new JPanel();
        JButton reloadInventButton = new JButton("Reload Inventory");
        reloadInventButton.setAlignmentX(reloadInventButton.CENTER_ALIGNMENT);
        JTextArea inventString = new JTextArea();
        JScrollPane inventPane = new JScrollPane(inventString);
        
        inventPane.setAlignmentY(inventPane.CENTER_ALIGNMENT);
        inventPane.setBorder(BorderFactory.createTitledBorder("Current Media Inventory"));
        
        inventoryView.setLayout(new BoxLayout(inventoryView, BoxLayout.Y_AXIS));
        inventoryView.setAlignmentY(inventoryView.CENTER_ALIGNMENT);
        inventoryView.add(reloadInventButton);
        inventoryView.add(inventPane);
        
        main.add(inventoryView);
        inventoryView.setVisible(false);
        
        displayInventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventString.setText(mm.displayInventory());
                inventString.setSelectionStart(0);
                inventString.setSelectionEnd(0);
                exitButton.setText("Back");
                inventoryView.setVisible(true);
                headerMenu.setVisible(false);
                mainMenu.setVisible(false);
                window.setTitle("Media Inventory");
                inventPane.getVerticalScrollBar().setValue(0);
            
                reloadInventButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s;
                        //Checks that front-end inventory matches back-end
                        if (!mm.displayInventory().equals(inventString.getText())) {
                            inventString.setText(mm.displayInventory());
                            s = "Inventory Updated";
                        } else if ((mm.displayInventory().equals(inventString.getText()))) {
                            s = "Inventory Up To Date";
                        } else {
                            s = "Reload Fail";
                        }
                        JOptionPane.showMessageDialog(window,(s));
                        inventPane.getVerticalScrollBar().setValue(0);
                    }
                });
            }
        });
        
        // MAIN MENU SECTION
        
        //Main section components, layouts, constraints & action listener(s)
        main.add(mainMenu);
        
        //Main buttons/main program functions point of entry
        JButton rentViewButton = new JButton();
        rentViewButton.add(new JLabel(
                "<html><body style='text-align: center'>Rent<br>Media</html>"
                , JLabel.CENTER
        ));
        rentViewButton.setLayout(new BoxLayout(rentViewButton, BoxLayout.Y_AXIS));
        
        JButton loadMediaViewButton = new JButton();
        loadMediaViewButton.add(new JLabel(
                "<html><body style='text-align: center'>Load<br>Media</html>"
                , JLabel.CENTER
        ));
        loadMediaViewButton.setLayout(new BoxLayout(loadMediaViewButton, BoxLayout.Y_AXIS));
        
        JButton createMediaViewButton = new JButton();
        createMediaViewButton.add(new JLabel(
                "<html><body style='text-align: center'>Create<br>Media</html>"
                , JLabel.CENTER
        ));
        createMediaViewButton.setLayout(new BoxLayout(createMediaViewButton, BoxLayout.Y_AXIS));
        
        rentViewButton.setMargin(new Insets(45,20,45,20));
        loadMediaViewButton.setMargin(new Insets(45,20,45,20));
        createMediaViewButton.setMargin(new Insets(45,20,45,20));
          
        mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.X_AXIS));
        mainMenu.setAlignmentY(mainMenu.CENTER_ALIGNMENT);
        mainMenu.setBorder(BorderFactory.createTitledBorder("Select Menu Item"));
        
        mainMenu.add(Box.createRigidArea(new Dimension(1,2)));
        mainMenu.add(rentViewButton);
        mainMenu.add(Box.createRigidArea(new Dimension(2,2)));
        mainMenu.add(loadMediaViewButton);
        mainMenu.add(Box.createRigidArea(new Dimension(2,2)));
        mainMenu.add(createMediaViewButton);
        mainMenu.add(Box.createRigidArea(new Dimension(2,1)));
        
        //RENT MEDIA Form layout & Components
        JPanel rentMediaView = new JPanel();
        JTextField mediaIdInput = new JTextField();
        JButton rentButton = new JButton("Get Media");
        
        rentMediaView.setLayout(new BorderLayout());
        rentMediaView.add(mediaIdInput, BorderLayout.NORTH);
        rentMediaView.add(rentButton, BorderLayout.CENTER);
        rentMediaView.setBorder(BorderFactory.createTitledBorder("Enter Media ID"));
        
        main.add(rentMediaView);
        rentMediaView.setVisible(false); //called on rentButton listener
        
        rentViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitButton.setText("Back");
                rentMediaView.setVisible(true);
                headerMenu.setVisible(false);
                mainMenu.setVisible(false);
                window.setTitle("Media Rental Form");
                window.pack();
                center(window);
                
                rentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {  
                        String s = mediaIdInput.getText().trim(); 
                        mediaIdInput.setText(null);
                        JOptionPane.showMessageDialog(
                                window, 
                                (mm.rentMedia(s))
                        );
                    }  
                });
            }
        });
        
        //LOAD MEDIA Form layout & Components
        JPanel loadMediaView = new JPanel();
        loadMediaView.setAlignmentY(loadMediaView.CENTER_ALIGNMENT);
        JLabel loadMessage = new JLabel(
                "<html><body style='text-align: center'>Files must be loaded in "
                        + "the program's<br>package named: \"Dailey_MediaRentalystem\".<br>"
                        + "Required file type: .txt or .csv<br>"
                        + "<b>note: file name case sensative<b><br><pre></html>");
        
        JPanel loadMediaContainter = new JPanel();
        JPanel loadMeadiaOptionsContainer = new JPanel();
        JPanel csvOptionContainer = new JPanel();
        JPanel txtOptionContainer = new JPanel();
        JTextField csvInput = new JTextField();
        JTextField txtInput = new JTextField();
        JLabel csvLable = new JLabel(".csv");
        JLabel txtLable = new JLabel(".txt");
        JButton loadMediaButton = new JButton("Load Media");
        loadMediaButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        
        csvOptionContainer.add(csvInput);
        csvOptionContainer.add(csvLable);
        txtOptionContainer.add(txtInput);
        txtOptionContainer.add(txtLable);
        csvOptionContainer.setLayout(new BoxLayout(csvOptionContainer, BoxLayout.X_AXIS));
        txtOptionContainer.setLayout(new BoxLayout(txtOptionContainer, BoxLayout.X_AXIS));
        loadMeadiaOptionsContainer.add(csvOptionContainer);
        loadMeadiaOptionsContainer.add(txtOptionContainer);
        loadMeadiaOptionsContainer.add(loadMediaButton);
        loadMeadiaOptionsContainer.setLayout(new BoxLayout(loadMeadiaOptionsContainer, BoxLayout.Y_AXIS));
        loadMediaContainter.add(loadMeadiaOptionsContainer, BorderLayout.CENTER);
        loadMediaContainter.setBorder(BorderFactory.createTitledBorder("Enter Media File Name"));
        loadMediaView.setLayout(new BorderLayout());
        loadMediaView.add(loadMessage, BorderLayout.NORTH);
        loadMessage.setAlignmentX(loadMessage.CENTER_ALIGNMENT); 
        loadMediaView.add(loadMediaContainter, BorderLayout.CENTER);
        
        main.add(loadMediaView);
        loadMediaView.setVisible(false);
        
        loadMediaViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitButton.setText("Back");
                loadMediaView.setVisible(true);
                headerMenu.setVisible(false);
                mainMenu.setVisible(false);
                window.setTitle("Load Media From User Directory");
                window.pack();
                center(window);
            
            
                loadMediaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String a;
                    String b = "";
                    
                    //if form inputs are null program automatically throws error
                    if (csvInput.getText().equals("") && txtInput.getText().equals("")) {
                        a = "Please input name of media file (CASE SENSATIVE)";
                    } else {
                        //checks for .csv input then .txt input
                        if (!csvInput.getText().equals("")) {
                            b = csvInput.getText().trim();
                            //load csv file
                            mm.loadMediaFromFile(b + (b.contains(".csv") ? "" : ".csv").trim());
                        } else if (!txtInput.getText().equals("")) {
                            b = txtInput.getText().trim();
                            //load txt file
                            mm.loadMediaFromFile(b + (b.contains(".txt") ? "" : ".txt").trim());
                        } else {
                            a = "RMS load system unavailable. Try again later";
                        }
                        //check for flag
                        if (mm.mediaLoaded) {
                            a = "Media File: " + b.toUpperCase() + " Loaded Succesfully!"
                                    + "\nSelect \"View Media Inventory\" on home screen "
                                    + "to view updated ineventory";
                        } else {
                            a = "Media file load failed. Please try again later";
                        }
                    }
                    JOptionPane.showMessageDialog(window, (a));
                    //downgrade flag and empty fields
                    mm.downgradeMediaLoaded();
                    txtInput.setText(null);
                    csvInput.setText(null);
                    mm.downgradeMediaLoaded();
                    }
                });
            }
        });
        
        //CREATE MEDIA Form layout & Components;  
        JPanel createMediaView = new JPanel();
        createMediaView.setLayout(new BoxLayout(createMediaView, BoxLayout.Y_AXIS));
        createMediaView.setBorder(BorderFactory.createTitledBorder("Input Media Fields"));
        
        JButton createMediaButton = new JButton("Create New Media");
        createMediaButton.setAlignmentX(createMediaButton.CENTER_ALIGNMENT);
        JTextField mediaTitleInput = new JTextField();
        JTextField mediaYearInput = new JTextField();
        JTextField otherInput = new JTextField();
        JLabel mediaTitleLabel = new JLabel("Select Media Type Above");
        mediaTitleLabel.setAlignmentY(mediaTitleLabel.RIGHT_ALIGNMENT);
        JLabel mediaYearLabel = new JLabel("Select Media Type Above");
        JLabel otherLabel = new JLabel("Select Media Type Above");
        JButton eBookButton = new JButton("EBook");
        JButton cdButton = new JButton("Music CD");
        JButton dvdButton = new JButton("Movie DVD");
        mediaTitleInput.setVisible(false);
        mediaYearInput.setVisible(false);
        otherInput.setVisible(false);
        
        JPanel mediaTypeContainer = new JPanel();
        mediaTypeContainer.setLayout(new GridLayout(1,3));
        mediaTypeContainer.add(eBookButton);
        mediaTypeContainer.add(cdButton);
        mediaTypeContainer.add(dvdButton);
        
        JPanel mediaInputsContainer = new JPanel();
        mediaInputsContainer.setAlignmentX(mediaInputsContainer.CENTER_ALIGNMENT);
        mediaInputsContainer.setLayout(new GridLayout(4,2));
        mediaInputsContainer.add(mediaTitleLabel);
        mediaInputsContainer.add(mediaTitleInput);
        mediaInputsContainer.add(mediaYearLabel);
        mediaInputsContainer.add(mediaYearInput);
        mediaInputsContainer.add(otherLabel);
        mediaInputsContainer.add(otherInput);
        
        createMediaView.add(mediaTypeContainer);
        createMediaView.add(mediaInputsContainer);
        createMediaView.add(createMediaButton);
        
        main.add(createMediaView);
        createMediaView.setVisible(false);
        
        createMediaViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitButton.setText("Back");
                createMediaView.setVisible(true);
                headerMenu.setVisible(false);
                mainMenu.setVisible(false);
                window.setTitle("Create New Media Item (Manual)");
                window.pack();
            }
        });  
                
        eBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaTitleLabel.setText("");
                mediaTitleLabel.setText("EBook Title");
                mediaYearLabel.setText("EBook Publish Year");
                otherLabel.setText("EBook Chapters Count");
                mediaTitleInput.setVisible(true);
                mediaYearInput.setVisible(true);
                otherInput.setVisible(true);
            }
        });

        cdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaTitleLabel.setText("");
                mediaTitleLabel.setText("CD Title");
                mediaYearLabel.setText("CD Publish Year");
                otherLabel.setText("CD Length (minutes)");
                mediaTitleInput.setVisible(true);
                mediaYearInput.setVisible(true);
                otherInput.setVisible(true);
            }
        });

        dvdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaTitleLabel.setText("");
                mediaTitleLabel.setText("DVD Title");
                mediaYearLabel.setText("DVD Publish Year");
                otherLabel.setText("DVD Size (megabytes)");
                mediaTitleInput.setVisible(true);
                mediaYearInput.setVisible(true);
                otherInput.setVisible(true);
            }
        });
        
        createMediaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = "";
                String type = mediaTitleLabel.getText();
                
                if (type.equals("EBook Title")) {
                    type = "EB";
                } else if (type.equals("CD Title")) {
                    type = "CD";
                } else if (type.equals("DVD Title")) {
                    type = "DVD";
                } else {
                    type = "";
                }
                
                String title = mediaTitleInput.getText();
                String yearString = mediaYearInput.getText();
                String other = otherInput.getText();
                
                //If any fields are null program automatically throws error
                if ( !type.equals("") 
                        && !title.equals("")
                        && !yearString.equals("")
                        && !other.equals("")
                        ) {
                    //create new media with attributes
                    int year = Integer.valueOf(yearString);
                    mm.createNewMedia(type, title, year, other, true);

                    if (mm.mediaCreated) {
                        s = mediaTitleLabel.getText() + " \"" 
                                + mediaTitleInput.getText().toUpperCase()
                                + "\" added successfully!";
                        mm.downgradeMediaCreated();
                        if (mm.fileUpdated) { //cvs file has updated
                            s = s + "\n" +  mediaTitleInput.getText().toUpperCase()
                                    + " media file updated on csv file";
                            mm.downgradeFileUpdated();
                        }
                    } else {
                        s = "Media item creation failed; Please try"
                                + "again later.";    
                    }
                } else {
                    s = "All fields must be complete in order to create "
                            + "a new media item";
                } 
                JOptionPane.showMessageDialog(window, (s));
                mediaTitleInput.setText(null);
                mediaYearInput.setText(null);
                otherInput.setText(null);
            }
        });
        
        // FOOTER LAYOUT
        
        exitButton.setAlignmentX(exitButton.CENTER_ALIGNMENT);
        JLabel copyRight = new JLabel("©Copyright MRS 2021");
        copyRight.setAlignmentX(copyRight.CENTER_ALIGNMENT);
        
        footerMenu.setLayout(new BoxLayout(footerMenu, BoxLayout.Y_AXIS));
        footerMenu.setAlignmentY(footerMenu.CENTER_ALIGNMENT);
        footerMenu.add(exitButton);
        footerMenu.add(Box.createRigidArea(new Dimension(2, 5)));
        footerMenu.add(copyRight);
        
        main.add(footerMenu);
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (exitButton.getText()) {
                    case "Exit": 
                        System.exit(0);
                        break;
                    case "Cancel": case "Back":
                        //closes all views and returns to home scree n
                        rentMediaView.setVisible(false);
                        inventoryView.setVisible(false);
                        loadMediaView.setVisible(false);
                        createMediaView.setVisible(false);
                        
                        headerMenu.setVisible(true);
                        mainMenu.setVisible(true);
                        
                        exitButton.setText("Exit");
                        window.setTitle("Media Rental System");
                        break;
                    default:
                }
                window.pack();
                center(window);
            }
        });
        
        //Initial window constraints
        window.getContentPane().add(main);
        center(window);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    
    //Other
    private static void center(Window window) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
        window.setLocation(x, y);
    }
    
}
