package color;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The InputHelper class is responsible for handling user input.
 */
public class InputHelper {

    /**
     * Prompts the user to input an integer.
     *
     * @param sc the Scanner object to use for input
     * @param min the minimum integer value
     * @param max the maximum integer value
     * @return the integer input by the user
     */
    public static int inputInteger(Scanner sc, int min, int max) {
        int input = -1;
        boolean isValid = false;

        // Ensure the input is valid
        do {
            // Print the prompt
            System.out.print("Select an option: ");

            // Try to get an integer input
            try {
                input = sc.nextInt();
                // Check if the input is within the specified range
                if (min <= input && input <= max) {
                    isValid = true;
                } else {
                    // Print an error message if the input is out of range
                    if (max == Integer.MAX_VALUE) {
                        System.out.println("Invalid input. Please enter an integer greater than or equal to " + min + ".");
                    } else {
                        System.out.println("Invalid input. Please enter an integer between " + min + " and " + max + ".");
                    }
                }
            } catch (InputMismatchException e) {
                // Print an error message if the input is not an integer
                System.out.println("Sorry! Please enter an integer.");
            } finally {
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
        } while (!isValid);

        // Return the input
        return input;
    }

}
