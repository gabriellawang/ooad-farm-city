package menu;

import java.util.*;

/**
 * The WelcomeMenu class represents the menu class for the default menu shown
 * when the application is started.
 *
 * @author G3-T02
 */
public class WelcomePage {

    private RegisterMenu rMenu;
    private LoginMenu lMenu;

    /**
     * Default constructor.
     */
    public WelcomePage() {

        rMenu = new RegisterMenu();
        lMenu = new LoginMenu();
    }

    /**
     * This method will display the welcome menu.
     */
    public void display() {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("== Farm City :: Welcome ==");
            System.out.println("Hi,");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice > ");

            try {
                choice = sc.nextInt();
                System.out.println();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter number [1], [2] or [3]");
                System.out.println();
                display();
                return;
            }

            if (choice == 1) {
                rMenu.display();
            } else if (choice == 2) {
                lMenu.display();
            } else if (choice == 3) {

            } else {
                System.out.println("Invalid input! Please enter number [1], [2] or [3]");
            }
        } while (choice != 3);
    }
}
