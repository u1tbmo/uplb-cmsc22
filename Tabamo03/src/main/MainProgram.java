/**
 * A program that allows the user to manage customer records of restaurants.
 * <p>
 * <b>Note:</b> This program is written in JDK 21 and may require JDK 21 or higher.
 *
 * @author Euan Jed S. Tabamo
 * @created 12 September 2024 20:45
 * @since 21.0.4
 */

package main;

import restaurant.Restaurant;

import java.util.Scanner;

/**
 * The main class
 */
public class MainProgram {
    public static void main(String[] args) {
        // Create a Scanner instance
        Scanner sc = new Scanner(System.in);

        // Create a Restaurant with name EliBee
        Restaurant elibee = new Restaurant("EliBee");

        // Ask the user for their name
        String username;
        boolean usernameIsValid = false;
        do {
            System.out.print("Enter your name: ");
            username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Please enter a valid nonempty string name.");
            } else {
                usernameIsValid = true;
            }
        } while (!usernameIsValid);

        // Welcome the user to the restaurant's customer records program.
        elibee.welcomeUser(username);

        int choice;
        do {
            choice = elibee.manageCustomerRecords(sc);
            switch (choice) {
                // Add Customer
                case 1:
                    elibee.addCustomer(sc);
                    break;
                // Search Customer
                case 2:
                    elibee.findCustomer(sc);
                    break;
                // View Customer Records
                case 3:
                    elibee.viewCustomerRecord();
                    break;
                // Delete Customer
                case 4:
                    elibee.deleteCustomer(sc);
                    break;
                // Exit
                case 0:
                    System.out.printf("Thank you, %s, for using %s's Customer Record Manager. Goodbye!\n", username, elibee.getRestaurantName());
                    break;
                // Invalid choice
                default:
                    System.out.println("Please enter a valid choice.");
                    break;
            }
        } while (choice != 0);

        // Close the scanner
        sc.close();
    }
}
