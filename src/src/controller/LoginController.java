package controller;

import java.util.*;

import dataManager.*;
import entity.*;

/**
 * The LoginController class coordinates the functions available in the FarmerDataManager for the Menu class.
 *
 * @author G3-T02
 */
public class LoginController {

    private FarmerDataManager farmerDM;

    /**
     * Constructs a LoginController object and Initialises Data Managers 
     */
    public LoginController() {

        farmerDM = new FarmerDataManager();
    }

    /**
     * This method takes the user's username and 
	 * check whether the username entered exists.
	 *
     * @param username The username user entered
     * @return true if username is taken, false if username is available
     */
        public boolean checkUsername(String username) {
        ArrayList<String> nameList = farmerDM.loadFarmers();
        for (String u : nameList) {
            if (u.equals(username)) {
                return true;
            }
        }
        return false;
    }

	
    /**
     * This method takes the user's username and password and 
	 * authenticate if password is correct
	 *
     * @param username The username user entered
     * @param password The password user entered
     * @return true if password is correct, false if password is wrong
     */
        public boolean checkPassword(String username, String password) {
        Farmer f = farmerDM.loadFarmerInfo(username);
        String realPassword = f.getPassword();
        return password.equals(realPassword);
    }

    /**
     * This method takes a username and return the Farmer object corresponding to the username
	 *
     * @param username The username of Farmer object
     * @return null if Farmer not found, otherwise the Farmer object, 
     */
    public Farmer retrieveFarmer(String username) {
        Farmer f = farmerDM.loadFarmerInfo(username);
        return f;
    }
}
