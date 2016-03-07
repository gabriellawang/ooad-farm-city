package controller;

import java.util.*;
import java.text.*;
import java.io.*;

import entity.*;
import dataManager.*;
import exception.*;

/**
 * The InventoryController class represents the inventory class for function
 * "Buy Seed" and "Send Gift".
 *
 * @author G3-T02
 */
public class InventoryController {

    private InventoryDataManager iDM;
    private CropDataManager cDM;
    private FarmerDataManager fDM;
    private GiftDataManager gDM;
    private FriendDataManager friDM;

    /**
     * Default constructor.
     */
    public InventoryController() {

        iDM = new InventoryDataManager();
        cDM = new CropDataManager();
        fDM = new FarmerDataManager();
        gDM = new GiftDataManager();
        friDM = new FriendDataManager();
    }

    /**
     * This method takes a Farmer object and return a list of seeds' name and
     * amount of this farmer.
     *
     * @param f an Farmer object that is currently logging on.
     * @return null if data cannot be loaded or the inventory is empty;
     * otherwise a list of seeds' name in this farmer's inventory.
     */
    public ArrayList<String> inventoryList(Farmer f) {
        ArrayList<Inventory> iList = iDM.loadInventory(f.getUsername());
        if (iList == null) {
            return null;
        } else {
            Inventory in = null;
            int amt;
            String name;
            ArrayList<String> inventory = new ArrayList<>();

            for (int i = 0; i < iList.size(); i++) {
                in = iList.get(i);
                amt = in.getAmount();
                name = in.getCropName();
                inventory.add(i + 1 + ". " + amt + " Bags of " + name);
            }
            return inventory;
        }
    }

    /**
     * This method returns the information of all the seeds available in the
     * store.
     *
     * @return null if data cannot be loaded; otherwise a list of seeds'
     * information in the store.
     */
    public ArrayList<String> storeList() {
        ArrayList<Crop> cropList = cDM.loadCrop();
        if (cropList == null) {
            return null;
        }
        ArrayList<String> sList = new ArrayList<>();
        for (int i = 0; i < cropList.size(); i++) {
            Crop c = cropList.get(i);
            String name = c.getName();
            int cost = c.getCost();
            int time = c.getTime();
            int XP = c.getXP();
            String output = i + 1 + ". " + name + " costs: " + cost + " gold\n" + "   Harvests in: "
                    + time + " mins\n" + "   XP Gained: " + XP;
            sList.add(output);
        }
        return sList;
    }

    /**
     * This method returns the name of all the seeds can be sent as gift.
     *
     * @return null if data cannot be loaded; otherwise a list of seed‘s name in
     * the store.
     */
    public ArrayList<String> giftList() {
        ArrayList<Crop> cropList = cDM.loadCrop();
        if (cropList == null) {
            return null;
        }
        ArrayList<String> gList = new ArrayList<>();
        for (Crop c : cropList) {
            String name = c.getName();
            gList.add(name);
        }
        return gList;
    }

    /**
     * This method buys seeds from store and update user's information, returns
     * the message according to different situations.
     *
     * @param choice the serial number of the seed in the store.
     * @param qty the quantity of the seeds farmer wants to buy.
     * @param f An Farmer object that is currently logging on.
     * @return message with reason if the input is invalid or farmer's gold is
     * not enough; message with shopping information if purchase successfully.
     * @throws exception.FarmCityControlException
     * @throws java.io.IOException
     */
    public String buySeed(int choice, int qty, Farmer f) throws FarmCityControlException, IOException {
        ArrayList<Crop> cList = cDM.loadCrop();
        if (choice > cList.size() || choice <= 0) {
            throw new FarmCityControlException("   Invalid choice!");
        } else if (qty <= 0) {
            throw new FarmCityControlException("Invalid quantity!");
        } else {
            Crop c = cList.get(choice - 1);
            int totalCost = qty * c.getCost();
            if (totalCost <= f.getGold()) {
                f.setGold(-totalCost);
                try {
                    fDM.saveFarmer(f);
                    ArrayList<Inventory> iList = iDM.loadInventory(f.getUsername());
                    boolean isAdded = false;
                    for (Inventory iList1 : iList) {
                        if (iList1.getCropName().equals(c.getName())) {
                            iList1.setAmount(qty);
                            isAdded = true;
                        }
                    }
                    if (!isAdded) {
                        iList.add(new Inventory(qty, c.getName()));
                    }
                    iDM.saveInventory(iList, f.getUsername());
                } catch (IOException e) {
                    throw e;
                }
                return "   " + qty + " bags of seeds purchased for " + totalCost + " gold.";
            } else {
                throw new FarmCityControlException("   Your gold is not enough!");
            }
        }
    }

    /**
     * This method sends seeds as gifts to friends and update user's inventory
     * after sending, returns the message depends on whether the sending process
     * is successful.
     *
     * @param f An Farmer object that is currently logging on.
     * @param receiver the Farmer who will receive the gift.
     * @param choice the serial number of the gift in the gift list.
     * @throws exception.FarmCityControlException
     * @throws java.io.IOException
     * @return message with reason if the user cannot send the gift; successful
     * message if the gift can be sent to at least one friend.
     */
    public String sendGift(int choice, String receiver, Farmer f) throws FarmCityControlException, IOException {
        if (receiver.equals(f.getUsername())) {
            throw new FarmCityControlException("You cannot send gift to yourself.");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        ArrayList<String> giftName = giftList();
        String seedChoice = giftName.get(choice - 1);
        // check whether the seed is available in inventory.
        ArrayList<Inventory> iList = iDM.loadInventory(f.getUsername());
        boolean seedAvailable = false;
        for (Inventory iList1 : iList) {
            String seed = iList1.getCropName();
            if (seedChoice.equals(seed)) {
                seedAvailable = true;
            }
        }
        // check whether can reset the record, if can not, check how many receivers today, whether already 5
        if (seedAvailable) {
            ArrayList<Gift> gList = gDM.loadGifts(f.getUsername());
            if (gList == null) {
                throw new FarmCityControlException("File gift.csv cannot be found in the database. Please try again.");
            } else {
                Date cDate = new Date();
                String currentDate = sdf.format(cDate);
                boolean reachLimitation = true;
                if (gList.size() > 0) {
                    Gift lastSent = gList.get(0);
                    String lastSendingDate = lastSent.getDate();
                    if (currentDate.equals(lastSendingDate)) {
                        if (gList.size() < 5) {
                            reachLimitation = false;
                        }

                    } else {
                        gList = new ArrayList<Gift>();
                        try {
                            gDM.saveGifts(gList, f.getUsername());
                        } catch (IOException e) {
                            throw e;
                        }
                        reachLimitation = false;
                    }
                } else {
                    reachLimitation = false;
                }
                if (reachLimitation) {
                    throw new FarmCityControlException("Cannot send to " + receiver + ". Your gift sending amount have reached the maximum today.");
                } else {
                    boolean receiverExist = false;
                    for (Gift gList1 : gList) {
                        String rName = gList1.getReceiver();
                        if (receiver.equals(rName)) {
                            receiverExist = true;
                        }
                    }
                    if (!receiverExist) {
                        ArrayList<String> fList = friDM.loadFriends(f.getUsername());
                        boolean isFriend = false;
                        for (String fName : fList) {
                            if (fName.equals(receiver)) {
                                isFriend = true;
                            }
                        }
                        if (isFriend) {
                            String date = sdf.format(cDate);
                            Gift g = new Gift(receiver, date);
                            gList.add(g);
                            try {
                                gDM.saveGifts(gList, f.getUsername());
                            } catch (IOException e) {
                                throw e;
                            }
                            // Deduction of sender
                            for (Inventory iList1 : iList) {
                                if (iList1.getCropName().equals(seedChoice)) {
                                    iList1.setAmount(-1);
                                }
                            }
                            try {
                                iDM.saveInventory(iList, f.getUsername());
                            } catch (IOException e) {
                                throw e;
                            }
                            // Add seed to receiver's inventory
                            ArrayList<Inventory> rIvty = iDM.loadInventory(receiver);
                            boolean isAdded = false;
                            for (Inventory rIvty1 : rIvty) {
                                if (rIvty1.getCropName().equals(seedChoice)) {
                                    rIvty1.setAmount(1);
                                    isAdded = true;
                                }
                            }
                            if (!isAdded) {
                                rIvty.add(new Inventory(1, seedChoice));
                            }
                            try {
                                iDM.saveInventory(rIvty, receiver);
                            } catch (IOException e) {
                                throw e;
                            }
                            return "Gift sent to " + receiver;
                        } else {
                            throw new FarmCityControlException(receiver + " is not your friend. You cannot send gift to " + receiver + ".");
                        }
                    } else {
                        throw new FarmCityControlException("You have already sent a gift to " + receiver + " today.");
                    }
                }
            }
        } else {
            throw new FarmCityControlException("The gift is not available in your inventory. Please try again");
        }
    }
}
