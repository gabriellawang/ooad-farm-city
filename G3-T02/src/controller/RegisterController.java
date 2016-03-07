package controller;

import java.util.*;
import java.io.*;

import dataManager.FarmerDataManager;

/**
 * The RegisterController class coordinates the functions available in the FarmerDataManager for the Menu class.
 *
 * @author G3-T02
 */
public class RegisterController {

    private FarmerDataManager farmerDM;

    /**
     * Constructs a RegisterController object and Initialises FarmerDataManager
     */
    public RegisterController() {
        farmerDM = new FarmerDataManager();
    }


    /**
     * This method takes the user's username and 
	 * check whether the username entered exists.
	 *
     * @param username The username user entered
     * @return true if username is taken, false if username is available
     */
        public boolean validUsername(String username) {
        ArrayList<String> nameList = farmerDM.loadFarmers();
        for (String nameList1 : nameList) {
            if (nameList1.equals(username)) {
                return false;
            }
        }
        return true;
    }

    //save the information of the new user into database.

    /**
     * This method creates a new user and save it' information in the database
	 *
     * @param username The username of a farmer's account.
     * @param fullName The full name of the user.
     * @param password The password of a farmer's account.
     * @throws SecurityException Throw exception when User file cannot be created
     * @throws IOException Throw exception when there is no information.csv , friends.csv , requests.csv , inventory.csv , plots.csv
     */
        public void addFarmer(String username, String fullName, String password) throws SecurityException, IOException {
        try {
            farmerDM.createUser(username, fullName, password);
        } catch (SecurityException | IOException e) {
            throw e;
        }
    }
}
