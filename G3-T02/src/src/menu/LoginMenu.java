package menu;

import java.util.*;

import controller.LoginController;

/**
 * The LoginMenu class represents the menu class for function "Log In".
 *
 * @author G3-T02
 */
public class LoginMenu {

    private LoginController loginCtrl;
    private MainMenu mainMenu;

    /**
     * Default constructor.
     */
    public LoginMenu() {

        loginCtrl = new LoginController();
        mainMenu = new MainMenu();
    }

    /**
     * This method will display the login menu.
     */
    public void display() {

        Scanner sc = new Scanner(System.in);
        String username;
        String password;
        boolean loginSuccessfully = true;

        do {
            loginSuccessfully = true;
            System.out.print("Enter your username > ");
            username = sc.nextLine();
            System.out.print("Enter your password > ");
            password = sc.nextLine();
            if (!loginCtrl.checkUsername(username)) {
                loginSuccessfully = false;
                System.out.println("The username you enter does not exist. Please try again.");
            } else if (!loginCtrl.checkPassword(username, password)) {
                loginSuccessfully = false;
                System.out.println("Wrong Password! Please try again.");
            }
        } while (!loginSuccessfully);
        System.out.println();

        mainMenu.display(loginCtrl.retrieveFarmer(username));
    }
}
