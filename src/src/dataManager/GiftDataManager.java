package dataManager;

import java.util.*;
import java.io.*;

import entity.Gift;

/**
 * The GiftDataManager class stores the information related to a Data Manager of Gift objects. It manages a list of gifts.
 *
 * @author G3-T02
 */
public class GiftDataManager {

    private final String FILEPATH = "data/Farmer/";
	
	/**
     * This method initialises and returns a list of Gift objects.
	 *
	 * @param username The username of the user that is currently logging on.
     * @return An ArrayList of Gift, returns null if no gifts.
     */
    public ArrayList<Gift> loadGifts(String username) {

        String path = FILEPATH + "/" + username + "/gift.csv";
        File info = null;
        Scanner sc = null;
        String receiver = "";
        String date = "";
        ArrayList<Gift> gList = new ArrayList<>();

        try {
            info = new File(path);
            sc = new Scanner(info);
            sc.useDelimiter(",|\r\n");

            sc.nextLine();
            while (sc.hasNext()) {
                receiver = sc.next();
                date = sc.next();
                gList.add(new Gift(receiver, date));
            }
        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return gList;
    }
	
	/**
     * This method update the list Gift object of the specified Farmer object
	 *
     * @param gList The ArrayList of Gift which needs to updated
     * @param username The username of the user that is currently logging on.
     * @throws IOException Throw exception when there is no gift.csv
     */
    public void saveGifts(ArrayList<Gift> gList, String username) throws IOException {
        String path = FILEPATH + "/" + username + "/gift.csv";

        PrintStream fileOut = null;
        try {
            fileOut = new PrintStream(new FileOutputStream(path, false));

            fileOut.println("receiver,date");
            for (Gift g : gList) {
                fileOut.print(g.getReceiver());
                fileOut.print(",");
                fileOut.println(g.getDate());
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
