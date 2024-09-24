/**
 * A program that allows the user to manage customer records of restaurants.
 * <p>
 * <b>Note:</b> This program is written in JDK 21 and may require JDK 21 or higher.
 *
 * @author Euan Jed S. Tabamo
 * @created 21 September 2024 19:45
 * @since 21.0.4
 */
package main;

import helpers.InputHelpers;
import restaurant.Restaurant;

import java.util.Scanner;

/**
 * The MainProgram class contains the main method for the program.
 */
public class MainProgram {
    /**
     * The main method for the program.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        // Create a scanner
        Scanner sc = new Scanner(System.in);

        // Ask for a username
        String username = InputHelpers.inputString(sc, "Enter your name: ");

        // Create a restaurant
        Restaurant r = new Restaurant("Quatro"); // haha
        r.welcomeUser(username);
        r.manageCustomerRecords(sc);

        // Close the scanner
        sc.close();
    }
}
