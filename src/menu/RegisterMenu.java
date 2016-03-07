package menu;

import java.util.*;
import java.io.*;

import controller.RegisterController;

/**
 * The RegisterMenu class represents the menu class for function "Register".
 *
 * @author G3-T02
 */
public class RegisterMenu {

    private RegisterController registerCtrl;

    /**
     * Default constructor.
     */
    public RegisterMenu() {

        registerCtrl = new RegisterController();
    }

    /**
     * This method will display the register menu.
     */
    public void display() {

        Scanner sc = new Scanner(System.in);
        boolean registerSuccessfully = true;

        String username = "";
        String fullName = "";
        String password = "";
        String confirmation = "";

        do {
			registerSuccessfully = true;
			
            System.out.println("== Farm City :: Registration ==");
            System.out.print("Enter your username > ");
            username = sc.nextLine();

            // check whether it is only alphanumeric.
            if (!username.matches("[a-zA-Z0-9]+")) {

                System.out.println("Invalid username! Username should contain only alphanumeric characters.");
                registerSuccessfully = false;

            } else if (!registerCtrl.validUsername(username)) {
			//compare with other usernames in database to check whether it's unique.

                System.out.println("Username already exists! Username is required to be unique.");
                System.out.println();
                registerSuccessfully = false;

            } else {

                System.out.print("Enter your full name > ");
                fullName = sc.nextLine();
                System.out.print("Enter your password > ");
                password = sc.nextLine();

                do {
                    System.out.print("Confirm your password > ");
                    confirmation = sc.nextLine();

                    if (!password.equals(confirmation)) { //check whether the confirmation matches the password.
                        System.out.println("Password does not match! Please confirm your password again.");
                    }
                } while (!password.equals(confirmation));
            }

        } while (!registerSuccessfully);

        //add the new farmer.
        try {
            registerCtrl.addFarmer(username, fullName, password);
        } catch (SecurityException | IOException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }

        System.out.println("Hi, " + username + "! Your account is successfully created!");
        System.out.println();
    }

}
