package menu;

import java.util.*;

import entity.*;

/**
 * The MainMenu class represents the menu class for the default menu shown when
 * users login with correct username and password.
 *
 * @author G3-T02
 */
public class MainMenu {

    private FriendMenu friendM;
    private FarmingMenu farmingM;
    private InventoryMenu inventoryM;

    /**
     * Default constructor.
     */
    public MainMenu() {

        friendM = new FriendMenu();
        farmingM = new FarmingMenu();
        inventoryM = new InventoryMenu();
    }

    /**
     * This method will display the main menu.
     *
     * @param farmer The user currently logging on.
     */
    public void display(Farmer farmer) {
        int choice = -1;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("== Farm City :: Main Menu ==");
            System.out.println("Welcome, " + farmer.getFullName() + "!");
            System.out.println();
            System.out.println("1. My Friends");
            System.out.println("2. My Farm");
            System.out.println("3. My Inventory");
            System.out.println("4. Logout");
            System.out.print("Enter your choice > ");

            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! The input must be integer.");
                System.out.println();
                display(farmer);
                return;
            }

            System.out.println();
            if (choice > 4 || choice < 1) {
                System.out.println("Invalid input! Please enter again with [1],[2],[3] or [4].");
            }

            if (choice == 1) {
                friendM.display(farmer);
            } else if (choice == 2) {
                farmingM.display(farmer);
            } else if (choice == 3) {
                inventoryM.display(farmer);
            }

        } while (choice != 4);
        System.out.println();
    }
}
