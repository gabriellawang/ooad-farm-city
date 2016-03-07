package menu;

import java.util.*;
import java.io.*;

import controller.*;
import entity.*;
import exception.*;

/**
 * The FriendMenu class represents the menu class for function "Accept",
 * "Unfriend", "Request" and "Reject".
 *
 * @author G3-T02
 */
public class FriendMenu {

    private FriendController friendCtrl;

    /**
     * Default constructor.
     */
    public FriendMenu() {
        friendCtrl = new FriendController();
    }

    /**
     * This method will display the My Friend menu.
     *
     * @param f an Farmer object that is currently logging on.
     */
    public void display(Farmer f) {

        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("== Farm City :: My Friends ==");
            System.out.println("Welcome, " + f.getFullName());
            System.out.println();

            System.out.println("My Friends:");
            displayFriends(f);
            System.out.println("My Requests:");
            displayRequests(f);

            System.out.print("[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject > ");
            choice = sc.nextLine();
            System.out.println();

            try {
                if (choice.equals("Q")) {
                    sendRequest(f);
                } else if (choice.equals("M")) {
                    System.out.println();
                } else if (choice.charAt(0) == 'U') {
                    unfriend(f, choice);
                } else if (choice.charAt(0) == 'A') {
                    accept(f, choice);
                } else if (choice.charAt(0) == 'R') {
                    reject(f, choice);
                } else {
                    System.out.println("Invalid Input! Try again.");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter your choice!");
                System.out.println();
            }

        } while (!choice.equals("M"));
    }

    /**
     * This method will display the list of friends' username.
     *
     * @param f an Farmer object that is currently logging on.
     */
    public void displayFriends(Farmer f) {

        ArrayList<String> fList = friendCtrl.showFriends(f);
        if (fList == null) {
            System.out.println("Cannot find the friends.csv in your database.");
            System.out.println();
        } else if (fList.isEmpty()) {
            System.out.println("You have no friend.");
            System.out.println();
        } else {
            for (int i = 0; i < fList.size(); i++) {
                System.out.println(i + 1 + ". " + fList.get(i));
            }
            System.out.println();
        }
    }

    /**
     * This method will display the list of requests' username.
     *
     * @param f an Farmer object that is currently logging on.
     */
    public void displayRequests(Farmer f) {

        ArrayList<String> rList = friendCtrl.showRequests(f);
        ArrayList<String> fList = friendCtrl.showFriends(f);

        if (rList == null) {
            System.out.println("Cannot find the requests.csv in your database.");
            System.out.println();
        } else if (rList.isEmpty()) {
            System.out.println("You have no friend request.");
            System.out.println();
        } else {
            int size = 0;
            if (fList != null) {
                size = fList.size();
            }
            for (int i = 0; i < rList.size(); i++) {
                System.out.println(size + i + 1 + ". " + rList.get(i));
            }
            System.out.println();
        }
    }

    /**
     * This method will display the information when user sends a friend
     * request.
     *
     * @param f an Farmer object that is currently logging on.
     */
    public void sendRequest(Farmer f) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the username > ");
        String name = sc.nextLine();
        name = name.toLowerCase();

        ArrayList<String> fList = friendCtrl.showFriends(f);
        for (String fList1 : fList) {
            if (fList1.equals(name)) {
                System.out.println("This farmer has already been your friend.");
                System.out.println();
                return;
            }
        }

        try {
            friendCtrl.sendRequest(f, name);
        } catch (FarmCityControlException e) {
            System.out.println(e.getMessage());
            System.out.println();
            return;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }

        System.out.println("A friend request is sent to " + name + ".");
        System.out.println();

    }

    /**
     * This method will extract an integer from a string and return the integer.
     *
     * @param choice the string that contains a letter and a number.
     * @return the integer extracted from the string if the input is in correct
     * format; -1 if the input is invalid.
     */
    public int extractInt(String choice) {

        if (choice.length() != 2) {
            System.out.println("The input should be two characters.");
            System.out.println();
            return -1;
        } else {
            int num;
            try {
                num = Integer.parseInt(choice.substring(1, 2));
            } catch (NumberFormatException e) {
                System.out.println("The second character must be integer.");
                System.out.println();
                return -1;
            }
            if (num <= 0) {
                System.out.println("Invalid input! The integer must be bigger than 0.");
                System.out.println();
                return -1;
            } else {
                return num;
            }
        }
    }

    /**
     * This method will display the information when user unfriends with other
     * users.
     *
     * @param f an Farmer object that is currently logging on.
     * @param choice the input the user entered when choosing "Unfriend".
     */
    public void unfriend(Farmer f, String choice) {
        int num = extractInt(choice);
        if (num == -1) {
        } else {
            boolean isRemoved;
            try {
                isRemoved = friendCtrl.unfriend(f, num);
            } catch (IOException e) {
                System.out.print(e.getMessage());
                System.out.println();
                return;
            }

            if (!isRemoved) {
                System.out.println("The friend cannot be found.");
                System.out.println();
            } else {
                System.out.println("Unfriend successfully!");
                System.out.println();
            }
        }
    }

    /**
     * This method will display the information when user accepts friends
     * request.
     *
     * @param f an Farmer object that is currently logging on.
     * @param choice the input the user entered when choosing "Accept".
     */
    public void accept(Farmer f, String choice) {
        int num = extractInt(choice);
        if (num == -1) {
        } else {
            String result;
            try {
                result = friendCtrl.acceptRequest(f, num);
            } catch (IOException e) {
                System.out.print(e.getMessage());
                System.out.println();
                return;
            }

            if (result == null) {
                System.out.println("This farmer is already your friend.");
                System.out.println();
            } else if (result.equals("")) {
                System.out.println("The farmer does not exist.");
                System.out.println();
            } else {
                System.out.println(result + " is now your friend.");
                System.out.println();
            }
        }
    }

    /**
     * This method will display the information when user rejects friend
     * request.
     *
     * @param f an Farmer object that is currently logging on.
     * @param choice the input the user entered when choosing "Reject".
     */
    public void reject(Farmer f, String choice) {
        int num = extractInt(choice);
        if (num == -1) {
        } else {
            boolean isRejected;
            try {
                isRejected = friendCtrl.rejectRequest(f, num);
            } catch (IOException e) {
                System.out.print(e.getMessage());
                System.out.println();
                return;
            }

            if (!isRejected) {
                System.out.println("The request you choose doesn't exist.");
                System.out.println();
            } else {
                System.out.println("Reject Successfully.");
                System.out.println();
            }
        }
    }

}
