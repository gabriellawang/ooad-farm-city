package dataManager;

import java.util.*;
import java.io.*;

import entity.*;

/**
 * The FarmerDataManager class stores the information related to a Data Manager of Farmer objects. It manages a list of farmers.
 *
 * @author G3-T02
 */
public class FarmerDataManager {

    private final String FILEPATH = "data/Farmer/";
    private PlotDataManager plotDM;
    private FriendDataManager friendDM;
    private InventoryDataManager inventoryDM;
    private GiftDataManager giftDM;

    /**
     * Constructs a FarmerDataManager object which initialises a list of DataManagers.
     */
    public FarmerDataManager() {

        plotDM = new PlotDataManager();
        friendDM = new FriendDataManager();
        inventoryDM = new InventoryDataManager();
        giftDM = new GiftDataManager();
    }

    /**
     * This method initialises and returns a list of Farmer names
	 * Or null if no farmers can be found
	 *
     * @return An ArrayList of farmer name, returns null if no farmers
     */
    public ArrayList<String> loadFarmers() {

        File farmer = null;

        try {

            farmer = new File(FILEPATH);

            File[] farmerName = farmer.listFiles();
            ArrayList<String> f = new ArrayList<>();

            if (farmerName != null && farmerName.length > 0) {
                for (File aFile : farmerName) {
                    f.add(aFile.getName());
                }
                return f;
            } else {
                return null;
            }

        } catch (NullPointerException e) {
            return null;
        }
    }
	
	/**
     * This method returns a Farmer object with the specified username
	 * Or null if no such Farmer can be found in the list of Farmers managed by the FarmerDataManager object.
	 *
	 * @param username The username of the user that is currently logging on.
     * @return The Farmer object corresponding to the username.
	 * Or null if no such Farmer can be found in the list of Farmer managed by the FarmerDataManager object.
     */
    public Farmer loadFarmerInfo(String username) {

        String path = FILEPATH + "/" + username + "/information.csv";
        File info = null;
        Scanner sc = null;
        String uName = "";
        String fullName = "";
        String password = "";
        int XP = 0;
        int gold = 0;

        try {
            info = new File(path);
            sc = new Scanner(info);
            sc.useDelimiter(",|\r\n");

            sc.nextLine();
            while (sc.hasNext()) {
                uName = sc.next();
                fullName = sc.next();
                password = sc.next();
                XP = Integer.parseInt(sc.next());
                gold = Integer.parseInt(sc.next());
            }
        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }

        Farmer farmer = new Farmer(username, fullName, password, XP, gold);

        return farmer;
    }

	/**
     * This method updates a Farmer object with the specified Farmer object
	 *
	 * @param f The Farmer object of the user that is currently logging on.
     * @throws IOException Throw exception when there is no information.csv
     */
    public void saveFarmer(Farmer f) throws IOException {
        String path = FILEPATH + f.getUsername() + "/information.csv";

        PrintStream fileOut = null;

        try {
            fileOut = new PrintStream(new FileOutputStream(path, false));
            fileOut.println("Username,FullName,Password,XP,Gold");
            fileOut.print(f.getUsername() + ",");
            fileOut.print(f.getFullName() + ",");
            fileOut.print(f.getPassword() + ",");
            fileOut.print(f.getXP() + ",");
            fileOut.println(f.getGold());
        } catch (IOException e) {
            throw new IOException("Fail to update the information! Please try again.");

        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }
	
    /**
     * This method creates a new Farmer in the database
	 *
     * @param username The username of a user(Farmer) account.
     * @param fullName The full name of the user.
     * @param password The password of a farmer's account.
     * @throws SecurityException Throw exception when User file cannot be created
     * @throws IOException Throw exception when there is no information.csv , friends.csv , requests.csv , inventory.csv , plots.csv
     */
	public void createUser(String username, String fullName, String password) throws SecurityException, IOException {
        String path = FILEPATH + username;

        File file = new File(path);
        try {
            file.mkdir();
        } catch (SecurityException e) {
            throw new SecurityException("A security manager dosen't allow the directory to be created. Please contact system administrator.");
        }

        File info = new File(path + "/information.csv");
        File frd = new File(path + "/friends.csv");
        File req = new File(path + "/requests.csv");
        File ivty = new File(path + "/inventory.csv");
        File pt = new File(path + "/plots.csv");

        try {
            // Create information.csv
            Farmer f = new Farmer(username, fullName, password);
            saveFarmer(f);

            // Create plots.csv
            ArrayList<Plot> pList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                pList.add(new Plot());
            }
            plotDM.savePlot(pList, username);

            // Create friends.csv
            friendDM.saveFriend(new ArrayList<String>(), username);

            // Create requests.csv
            friendDM.saveRequest(new ArrayList<String>(), username);

            // Create inventory.csv
            inventoryDM.saveInventory(new ArrayList<Inventory>(), username);

            // Create gift.csv
            giftDM.saveGifts(new ArrayList<Gift>(), username);
        } catch (IOException e) {
            throw new IOException("Fail to update the information! Please try again.");
        }

    }

}
