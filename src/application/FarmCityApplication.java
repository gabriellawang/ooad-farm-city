package application;

import menu.*;

/**
 * This will run the FarmCityApplication.
 * FarmCityApplication contains the main method and enables the user to see the Welcome Page. It then invokes the display() method of the WelcomePage class for the default menu to be shown.
 *
 * @author G3-T02
 */
public class FarmCityApplication {

    /**
     * The main method.
     *
     * @param args
     */
    public static void main(String[] args) {

        WelcomePage wp = new WelcomePage();
        wp.display();
    }
}
