package menu;

import java.util.*;
import java.io.*;

import entity.Farmer;
import controller.*;
import exception.*;

/**
 * The FarmingMenu class represents the menu class for function "Plant",
 * "Harvest" and "Clear".
 *
 * @author G3-T02
 */
public class FarmingMenu {

    private HarvestController harvestCtrl;
    private FarmerController farmerCtrl;
    private PlantController plantCtrl;
    private ClearController clearCtrl;

    /**
     * Default constructor.
     */
    public FarmingMenu() {

        harvestCtrl = new HarvestController();
        farmerCtrl = new FarmerController();
        plantCtrl = new PlantController();
        clearCtrl = new ClearController();
    }

    /**
     * This method will display the My Farm menu.
     *
     * @param f an Farmer object that is currently logging on.
     */
    public void display(Farmer f) {

        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("== Farm City :: My Farm == ");
            System.out.println("Welcome, " + f.getFullName() + "!");
            System.out.println("Rank: " + farmerCtrl.currentRank(f) + "\t\t\tGold: " + f.getGold());
            System.out.println();

            try {
                plantCtrl.updatePlot(f);
            } catch (FarmCityControlException | IOException e) {
                System.out.print(e.getMessage());
                System.out.println();
                return;
            }

            System.out.println("You have " + farmerCtrl.plotAmt(f) + " plots of land.");
            ArrayList<String> pDisplay = farmerCtrl.plotsDisplay(f);
            for (String pDisplay1 : pDisplay) {
                System.out.println(pDisplay1);
            }

            // Prompts users to enter choice
            System.out.print("[M]ain | [P]lant | C[L]ear | [H]arvest  > ");
            choice = sc.nextLine();
            System.out.println();
            try {
                if (choice.charAt(0) == 'P') {
                    processPlant(f, choice);
                } else if (choice.charAt(0) == 'L') {
                    processClear(f, choice);
                } else if (choice.equals("H")) {
                    processHarvest(f);
                } else if (choice.equals("M")) {

                } else {
                    System.out.println("Invalid Input!");
                    System.out.println();
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid Input!");
                System.out.println();
            }

        } while (!choice.equals("M"));
    }

    /**
     * This method will display the menu for clearing the wilted plot.
     *
     * @param f an Farmer object that is currently logging on.
     * @param str the input the user enters while choosing "Clear".
     */
    public void processClear(Farmer f, String str) {

        if (str.length() != 2) {
            System.out.println("The input should be two characters.");
            System.out.println();
            return;
        }
        String num = str.substring(1, 2);
        int plotNo = 0;
        try {
            plotNo = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! The second character must be integer!");
        }

        int isClear;
        try {
            isClear = clearCtrl.clear(f, plotNo);
        } catch (FarmCityControlException | IOException e) {
            System.out.println(e.getMessage());
            System.out.println();
            return;
        }

        if (isClear == -1) {
            System.out.println("The plot you picked cannot be cleared yet.");
        } else if (isClear == 0) {
            System.out.println("The plot you picked has been cleared.");
            System.out.println("This operation costed you 5 gold coins.");
        } else {
            System.out.println("Your gold is not enough.");
            System.out.println("A super power helps you clear all wilted plots free of cost.");
        }
        System.out.println();
    }

    /**
     * This method will display the menu for harvesting crops.
     *
     * @param f an Farmer object that is currently logging on.
     */
    public void processHarvest(Farmer f) {
        ArrayList<String> display;

        try {
            display = harvestCtrl.harvest(f);
        } catch (FarmCityControlException | IOException e) {
            System.out.println(e.getMessage());
            System.out.println();
            return;
        }

        for (String display1 : display) {
            System.out.println(display1);
        }
        System.out.println();

    }

    /**
     * This method will display the menu for planting seeds.
     *
     * @param f an Farmer object that is currently logging on.
     * @param str the input the user enters while choosing "Plant".
     */
    public void processPlant(Farmer f, String str) {

        Scanner sc = new Scanner(System.in);
        if (str.length() != 2) {
            System.out.println("The input should be two characters.");
            System.out.println();
            return;
        }
        String num = str.substring(1, 2);
        int plotNo = 0;
        try {
            plotNo = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! The second character must be integer!");
        }

        // Check the plot.
        boolean plotAvailable = plantCtrl.plotAvailable(f, plotNo);
        if (!plotAvailable) {
            System.out.println("The plot you choose is not empty or does no exist.");
            System.out.println("You will return to the Farm Page.");
            System.out.println();
            return;
        }

        // Display a list of crop that can be planted.
        ArrayList<String> cropList = plantCtrl.plantList(f);
        if (cropList.isEmpty()) {
            System.out.println("Your inventory is empty. Please buy seeds from store.");
            System.out.println("You will return to the Farm Page.");
            System.out.println();
            return;
        } else {
            System.out.println("Select the crop:");
            for (int i = 0; i < cropList.size(); i++) {
                System.out.println(i + 1 + ". " + cropList.get(i));
            }
        }

        System.out.print("[M]ain | Select Choice > ");
        String choice = sc.nextLine();

        System.out.println();

        if (choice.equals("M")) {
        } else {
            int cropNo;
            try {
                cropNo = Integer.parseInt(choice);
                plantCtrl.plant(f, plotNo, cropList.get(cropNo - 1));

                System.out.println(cropList.get(cropNo - 1) + " planted on plot " + plotNo);
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer to select a crop.");
                System.out.println();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }
}
