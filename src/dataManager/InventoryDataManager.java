package dataManager;

import java.util.*;
import java.io.*;

import entity.Inventory;

/**
 * The InventoryDataManager class stores the information related to a Data Manager of Inventory objects. It manages a list of inventories.
 *
 * @author G3-T02
 */
public class InventoryDataManager {

    private final String FILEPATH = "data/Farmer/";

    /**
     * This method initialises and returns a list of Inventory objects.
	 *
	 * @param username The username of the user that is currently logging on.
     * @return iList An ArrayList of Inventory, returns null if no inventories.
     */
	public ArrayList<Inventory> loadInventory(String username) {

        String path = FILEPATH + username + "/inventory.csv";
        Scanner sc = null;
        File iFile = null;
        int amount = 0;
        String cropName = "";

        ArrayList<Inventory> iList = new ArrayList<>();

        try {
            iFile = new File(path);
            sc = new Scanner(iFile);
            sc.useDelimiter(",|\r\n");

            sc.nextLine();
            while (sc.hasNext()) {
                amount = Integer.parseInt(sc.next());
                cropName = sc.next();
                iList.add(new Inventory(amount, cropName));
            }

        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

        return iList;
    }

	/**
     * This method updates the list Inventory object of the specified Farmer object
	 *
     * @param iList The list of Inventory which needs to updated
     * @param username The username of the user that is currently logging on.
     * @throws IOException Throw exception when there is no inventory.csv
     */
	public void saveInventory(ArrayList<Inventory> iList, String username) throws IOException {
        String path = FILEPATH + username + "/inventory.csv";

        PrintStream fileOut = null;
        try {
            fileOut = new PrintStream(new FileOutputStream(path, false));
            fileOut.println("amount,cropName");

            for (int i = 0; i < iList.size(); i++) {
                Inventory ivty = iList.get(i);
                if (ivty.getAmount() < 1) {
                    iList.remove(i);
                    i--;
                }
            }

            for (Inventory ivty : iList) {
                fileOut.print(ivty.getAmount());
                fileOut.print(",");
                fileOut.println(ivty.getCropName());
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
