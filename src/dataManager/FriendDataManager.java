package dataManager;

import java.util.*;
import java.io.*;

/**
 * The FriendDataManager class stores the information related to a Data Manager of Farmer objects. It manages a list of friends a farmer has.
 *
 * @author G3-T02
 */
public class FriendDataManager {

    private final String FILEPATH = "data/Farmer/";

	/**
     * This method initialises and returns a list of friends name the specified farmer has.
	 * Or null if no friends can be found
	 *
	 * @param username The username of the user that is currently logging on.
     * @return fList An ArrayList of friends name, returns null if no friends.
     */
    public ArrayList<String> loadFriends(String username) {

        String path = FILEPATH + username + "/friends.csv";
        File fFile = null;
        Scanner sc = null;
        String friendName = "";

        ArrayList<String> fList = new ArrayList<>();

        try {
            fFile = new File(path);
            sc = new Scanner(fFile);
            sc.useDelimiter("\r\n");

            while (sc.hasNext()) {
                fList.add(sc.next());
            }

        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

        return fList;
    }

    /**
     * This method initialises and returns a list of friend request the specified farmer has.
	 * Or null if no friend request can be found
	 *
	 * @param username The username of the user that is currently logging on.
     * @return rList An ArrayList of friend request name, returns null if no friend request
     */
        public ArrayList<String> loadRequests(String username) {

        String path = FILEPATH + username + "/requests.csv";
        File fFile = null;
        Scanner sc = null;
        String requestName = "";

        ArrayList<String> rList = new ArrayList<>();

        try {
            fFile = new File(path);
            sc = new Scanner(fFile);
            sc.useDelimiter("\r\n");

            while (sc.hasNext()) {
                rList.add(sc.next());
            }
        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return rList;
    }

	/**
     * This method updates the list of friends(Farmer) of the specified Farmer object
	 *
     * @param friendList The ArrayList of friends(Farmer) which needs to updated
     * @param username The user name of the user that is currently logging on.
     * @throws IOException Throw exception when there is no friends.csv
     */
	public void saveFriend(ArrayList<String> friendList, String username) throws IOException {

        String path = FILEPATH + username + "/friends.csv";

        PrintStream fileOut = null;

        try {
            fileOut = new PrintStream(new FileOutputStream(path, false));

            for (int i = 0; i < friendList.size(); i++) {

                fileOut.println(friendList.get(i));
            }
        } catch (IOException e) {
            throw new IOException("Fail to update the information! Please try again.");
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }

    }
	
	/**
     * This method updates the friend request list of the specified Farmer object
	 *
     * @param requestList The list of friend request which needs to updated
     * @param username The user name of the user that is currently logging on.
     * @throws IOException Throw exception when there is no requests.csv
     */
	public void saveRequest(ArrayList<String> requestList, String username) throws IOException {

        String path = FILEPATH + username + "/requests.csv";
        PrintStream fileOut = null;

        try {
            fileOut = new PrintStream(new FileOutputStream(path, false));

            for (String requestList1 : requestList) {
                fileOut.println(requestList1);
            }
        } catch (IOException e) {
            throw new IOException("Fail to update the information! Please try again.");
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

}
