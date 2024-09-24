package helpers;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class contains helper methods for user input and validation.
 */
public class InputHelpers {

    /**
     * Prompts the user to input an integer.
     *
     * @param sc     the Scanner object to use for input
     * @param prompt the message to display to the user
     * @return the integer input by the user
     */
    public static int inputInteger(Scanner sc, String prompt, int min, int max) {
        int input = -1;
        boolean isValid = false;

        // Ensure the input is valid
        do {
            // Print the prompt
            System.out.print(prompt);

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

    /**
     * Prompts the user to input a float.
     *
     * @param sc     the Scanner object to use for input
     * @param prompt the message to display to the user
     * @return the float input by the user
     */
    public static float inputFloat(Scanner sc, String prompt, float min, float max) {
        float input = -1;
        boolean isValid = false;

        // Ensure the input is valid
        do {
            // Print the prompt
            System.out.print(prompt);

            // Try to get a float input
            try {
                input = sc.nextFloat();
                // Check if the input is within the specified range
                if (min <= input && input <= max) {
                    isValid = true;
                } else {
                    // Print an error message if the input is out of range
                    if (max == Float.MAX_VALUE) {
                        System.out.println("Invalid input. Please enter a float greater than or equal to " + min + ".");
                    } else {
                        System.out.println("Invalid input. Please enter a float between " + min + " and " + max + ".");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a float.");
            } finally {
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
        } while (!isValid);

        // Return the input
        return input;
    }

    /**
     * Prompts the user to input a string.
     *
     * @param sc     the Scanner object to use for input
     * @param prompt the message to display to the user
     * @return the string input by the user
     */
    public static String inputString(Scanner sc, String prompt) {
        System.out.print(prompt);
        String input;
        while (true) {
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a non-empty string.");
                continue;
            }
            return input;
        }
    }
}
