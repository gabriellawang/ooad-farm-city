package menu;

import java.util.*;
import java.io.*;

import controller.*;
import entity.*;
import exception.*;

/**
 * The InventoryMenu class represents the menu class for function "Buy Seed" and
 * "Send Gift".
 *
 * @author G3-T02
 */
public class InventoryMenu {

    private InventoryController inventoryCtrl;
    private FarmerController farmerCtrl;

    /**
     * Default constructor.
     */
    public InventoryMenu() {

        inventoryCtrl = new InventoryController();
        farmerCtrl = new FarmerController();
    }

    /**
     * This method will display the My Inventory menu.
     *
     * @param f an Farmer object that is currently logging on.
     */
    public void display(Farmer f) {

        Scanner sc = new Scanner(System.in);
        String choice;

        do {
            welcome(f);

            System.out.println("My Seeds:");
            ArrayList<String> iList = inventoryCtrl.inventoryList(f);
            if (iList == null) {
                System.out.println("The Inventory Database cannot be found.");
                System.out.println();
            } else if (iList.isEmpty()) {
                System.out.println("You have no seed in your inventory.");
                System.out.println();
            } else {
                for (String iList1 : iList) {
                    System.out.println(iList1);
                }
                System.out.println();
            }

            System.out.print("[M]ain | [B]uy | [G]ift | Select choice > ");
            choice = sc.nextLine();

            switch (choice) {
                case "B":
                    //Buy seeds
                    processBuySeed(f);
                    System.out.println();
                    break;
                case "G":
                    //Send gift
                    processSendGift(f);
                    System.out.println();
                    break;
                case "M":
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid Input! Please try again.");
                    System.out.println();
                    break;
            }
        } while (!choice.equals("M"));

    }

    /**
     * This method will display the basic information of farmer's inventory.
     *
     * @param f an Farmer object that is currently logging on.
     */
    public void welcome(Farmer f) {
        System.out.println("== Farm City :: My Inventory ==");
        System.out.println("Welcome, " + f.getFullName() + "!");
        System.out.println("Rank: " + farmerCtrl.currentRank(f) + "\t\t\tGold: " + f.getGold());
        System.out.println();
    }

    /**
     * This method will display the information of buying seeds.
     *
     * @param f an Farmer object that is currently logging on.
     */
    public void processBuySeed(Farmer f) {
        Scanner sc = new Scanner(System.in);

        welcome(f);
        System.out.println("Seeds Available:");

        ArrayList<String> sList = inventoryCtrl.storeList();
        for (String sList1 : sList) {
            System.out.println(sList1);
        }
        System.out.println();

        System.out.print("[M]ain | Select choice > ");
        String input = sc.nextLine();

        if (input.equals("M")) {
            return;
        } else if (input.length() != 1) {
            System.out.println("Invalid Input!");
            return;
        }

        int choice;
        int qty;
        try {
            choice = Integer.parseInt(input);
            System.out.print("   Enter quantity > ");
            qty = sc.nextInt();
            System.out.println(inventoryCtrl.buySeed(choice, qty, f));
            System.out.println();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input! Must be Integer.");
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input! Please choose a seed.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println();
        } catch (FarmCityControlException e) {
            System.out.print(e.getMessage());
            System.out.println();
        }

    }

    /**
     * This method will display the information of sending gift.
     *
     * @param f an Farmer object that is currently logging on.
     */
    public void processSendGift(Farmer f) {
        Scanner sc = new Scanner(System.in);

        System.out.println("== Farm City :: Send a Gift ==");
        System.out.println("Welcome, " + f.getFullName());
        System.out.println("Rank: " + farmerCtrl.currentRank(f) + "\t\t\tGold: " + f.getGold());
        System.out.println();

        ArrayList<String> gList = inventoryCtrl.giftList();

        if (gList == null) {
            System.out.println("File cannot be found in the database. Please try again.");
            return;
        }

        System.out.println("Gifts Available:");
        for (int i = 0; i < gList.size(); i++) {
            System.out.println(i + 1 + ". 1 Bag of " + gList.get(i) + " Seeds");
        }

        System.out.print("[M]ain | Select choice > ");
        String input = sc.nextLine();

        if (input.equals("M")) {
            return;
        } else if (input.length() != 1) {
            System.out.println("Invalid Input!");
            return;
        }

        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input!");
            return;
        }

        if (choice > gList.size() || choice < 1) {
            System.out.println("Invalid Input!");
            return;
        }

        System.out.print("Send to > ");
        String receiver = sc.nextLine();
        String[] rArr = receiver.split(",");
        ArrayList<String> rList = new ArrayList<>();
        for (String r : rArr) {
            rList.add(r.trim());
        }

        for (String r : rList) {
            String result;
            try {
                result = inventoryCtrl.sendGift(choice, r, f);
                System.out.println(result);
            } catch (FarmCityControlException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println();
    }
}
