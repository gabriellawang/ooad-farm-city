package controller;

import java.util.*;
import java.io.*;

import entity.*;
import dataManager.*;
import exception.*;

/**
 * The FriendController class coordinates the functions available in the Data Managers for the Menu class.
 * It manages FriendDataManager and FarmerDataManager.
 *
 * @author G3-T02
 */
public class FriendController {

    private FriendDataManager friDM;
    private FarmerDataManager fDM;

    /**
     * Constructs a FarmerController object and Initialises Data Managers 
     */
    public FriendController() {

        friDM = new FriendDataManager();
        fDM = new FarmerDataManager();
    }

    /**
     * This method retrieves the list of user's friends
	 *
     * @param f The Farmer object of user
     * @return An ArrayList of friends(Farmer) user
     */
    public ArrayList<String> showFriends(Farmer f) {

        ArrayList<String> fList = friDM.loadFriends(f.getUsername());
        return fList;
    }

    /**
     * This method retrieves the list of user's friend request user
	 *
     * @param f The Farmer object of user
     * @return An ArrayList of friend request user
     */
    public ArrayList<String> showRequests(Farmer f) {

        return friDM.loadRequests(f.getUsername());
    }

    /**
     * The process of user send friend request
	 *
     * @param f The Farmer object of user
     * @param username The user name of the Farmer, user want to sent request to
     * @throws FarmCityControlException Throw exception when user send request to oneself
     * @throws IOException Throw exception when there is no friends.csv
     */
    public void sendRequest(Farmer f, String username) throws FarmCityControlException, IOException {

        if (username.equals(f.getUsername())) {
            throw new FarmCityControlException("You cannot send request to yourself. Please try again.");
        }

        ArrayList<String> farmerList = fDM.loadFarmers();
        Farmer anotherF = null;
        for (String farmerList1 : farmerList) {
            if (farmerList1.equals(username)) {
                anotherF = fDM.loadFarmerInfo(username);
            }
        }

        if (anotherF == null) { // Check whether the user exists.
            throw new FarmCityControlException("Username dosen't exist. Please try again.");
        } else {
            ArrayList<String> rList = friDM.loadRequests(username);

            for (String rList1 : rList) {
                if (rList1.equals(f.getUsername())) {
                    throw new FarmCityControlException("You have sent the same request before.");
                }
            }
            rList.add(f.getUsername());
            try {
                friDM.saveRequest(rList, username);
            } catch (IOException e) {
                throw e;
            }

        }
    }


    /**
     * The process of user wants to remove a friend
	 *
     * @param f The Farmer object of user
     * @param num The choice corresponding to the friend, user wants to remove.
     * @return Return true if the friend is successfully removed, false if unsuccessful.
     * @throws IOException Throw exception when there is no friends.csv
     */
    public boolean unfriend(Farmer f, int num) throws IOException {

        ArrayList<String> fList = friDM.loadFriends(f.getUsername());
        String fName; // The username of the friend who will be removed from farmer's friend list.
        ArrayList<String> fList2 = new ArrayList<>(); // Friend list of the friend who will be removed.

        if (fList == null || fList.isEmpty()) {
            return false;
        } else {
            if (fList.size() < num) {
                return false;
            } else {
                fName = fList.get(num - 1);
                fList.remove(num - 1);

                fList2 = friDM.loadFriends(fName);
                for (int i = 0; i < fList2.size(); i++) {
                    if (fList2.get(i).equals(f.getUsername())) {
                        fList2.remove(i);
                    }
                }

            }
            try {
                friDM.saveFriend(fList, f.getUsername());
                friDM.saveFriend(fList2, fName);
                return true;
            } catch (IOException e) {
                throw e;
            }

        }
    }

	// Accept the friend request.
    // Move the farmer from request list to friend list.
    // Return null if the selected farmer is already in friend list.
    // Return an empty string if the selected farmer doesn't exist.
    // Return the username of the new friend if accept successfully.

    /**
     * The process of user accepts a friend request
	 *
     * @param f The Farmer object of user
     * @param num The choice corresponding to the request, user wants to accept.
     * @return  
	 * Null if the selected farmer is already in friend list.
     * An empty string if the selected farmer doesn't exist.
     * The username of the new friend if accept successfully.
     * @throws IOException Throw exception when there is no friends.csv
     */
	public String acceptRequest(Farmer f, int num) throws IOException {

        ArrayList<String> fList = friDM.loadFriends(f.getUsername());
        ArrayList<String> rList = friDM.loadRequests(f.getUsername());

        if (num <= fList.size()) {
            return null;
        } else if (num > rList.size() + fList.size()) {
            return "";
        } else {
            int index = num - fList.size() - 1;
            String newFriend = rList.get(index);

            ArrayList<String> anotherFList = friDM.loadFriends(newFriend);
            anotherFList.add(f.getUsername());
            rList.remove(index);
            fList.add(newFriend);
            try {
                friDM.saveFriend(anotherFList, newFriend);
                friDM.saveFriend(fList, f.getUsername());
                friDM.saveRequest(rList, f.getUsername());

            } catch (IOException e) {
                throw e;
            }
            return newFriend;
        }
    }

	// Reject the friend request.
    // Return true if reject sucessfully, otherwise return false.

    /**
     * The process of user reject a friend request
	 *
     * @param f The Farmer object of user
     * @param num The choice corresponding to the request, user wants to reject.
     * @return Return true if the friend is successfully removed, false if unsuccessful.
     * @throws IOException Throw exception when there is no friends.csv
     */
        public boolean rejectRequest(Farmer f, int num) throws IOException {
        ArrayList<String> fList = friDM.loadFriends(f.getUsername());
        ArrayList<String> rList = friDM.loadRequests(f.getUsername());

        if (num <= fList.size() || num > rList.size() + fList.size()) {
            return false;
        } else {
            int i = num - fList.size() - 1;
            rList.remove(i);
            try {
                friDM.saveRequest(rList, f.getUsername());
            } catch (IOException e) {
                throw e;
            }

            return true;
        }
    }
}
