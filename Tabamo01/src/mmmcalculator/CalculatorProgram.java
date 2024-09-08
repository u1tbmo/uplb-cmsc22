/**
 * A program that calculates the mean, median, and mode of an array of positive integers input by the user.
 * <p>
 * <b>Note:</b> This program is written in JDK 21 and may require JDK 21 or higher.
 * <p>
 *
 * <b>Date Created:</b> 29 August 2024 21:50
 *
 * @author Euan Jed S. Tabamo
 * @since 21.0.4
 */

package mmmcalculator;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A simple calculator object which provides methods for calculating the mean,
 * median, and mode of an array of positive integers.
 */
public class CalculatorProgram {
    /**
     * Default constructor of this class.
     */
    CalculatorProgram() {
    }

    /**
     * Main method
     *
     * @param args arguments for main method
     */
    public static void main(String[] args) {
        // Create an instance of the Scanner class
        Scanner sc = new Scanner(System.in);

        // Create an instance of the CalculatorProgram class
        CalculatorProgram calculator = new CalculatorProgram();

        // Main Menu Loop
        String choice = "";
        while (!choice.equals("0")) {
            // Call the main menu to ask for a choice
            choice = calculator.mainMenu(sc).trim();

            // Choice 1: Input and Calculate
            if (choice.equals("1")) {
                System.out.println("-- Mean, Median, and Mode Calculator --");

                // Get an array from the user
                int[] numberArray = calculator.inputNumbers(sc);

                System.out.println("-- Results ----------------------------");

                // Calculate and print the mean
                // If the mean is an integer, then print the mean as an integer
                float mean = calculator.calculateMean(numberArray);
                System.out.println(((int) mean == mean) ? ("Mean: " + (int) mean) : ("Mean: " + mean));

                // Calculate the median
                // If the median is an integer, then print the median as an integer
                // NOTE: Might be unnecessary in this exercise since the median is always an
                // integer. However, this code is now "future-proof" if the input size is changed
                float median = calculator.calculateMedian(numberArray);
                System.out.println(((int) median == median) ? ("Median: " + (int) median) : ("Median: " + median));

                // Calculate and print the mode/s
                int[] modes = calculator.calculateMode(numberArray);
                System.out.print("Mode: ");
                if (modes.length != 0) {
                    for (int i = 0; i < modes.length; i++) {
                        System.out.print(modes[i]);
                        if (i < modes.length - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                } else {
                    System.out.println("None");
                }
                System.out.println("---------------------------------------");
            } else if (choice.equals("0")) {
                System.out.println("---------------------------------------");
                System.out.println("Goodbye!");
                System.out.println("---------------------------------------");
            } else {
                System.out.println("Please enter a valid choice.\n");
            }
        }
        // Close the scanner to prevent resource leaks
        sc.close();
    }

    /**
     * Prints a menu and returns the user's choice.
     *
     * @param scanner the Scanner instance to read from
     * @return the input String from the user
     */
    private String mainMenu(Scanner scanner) {
        System.out.println("-- Main Menu --------------------------");
        System.out.println("1 | Input numbers and calculate");
        System.out.println("0 | Exit");
        System.out.println("---------------------------------------");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();
        System.out.println();
        return choice;
    }

    /**
     * Returns the mean of an array of positive integers.
     *
     * @param numberArray the array of positive integers to compute the mean of
     * @return the mean of the array of integers
     */
    public float calculateMean(int[] numberArray) {
        int sum = 0;
        for (int number : numberArray) {
            sum += number;
        }
        return (float) sum / numberArray.length;
    }

    /**
     * Returns the median of an array of positive integers.
     * <p>
     * For arrays with odd length, returns the middle value.
     * For arrays with even length, returns the mean of the two middle values.
     *
     * @param numberArray the array of positive integers to compute the median of
     * @return the median of the array of integers
     */
    public float calculateMedian(int[] numberArray) {
        int[] sortedArray = sortNumberArray(numberArray);
        int len = sortedArray.length;

        // Case 1: Odd Length Array
        if (len % 2 != 0) {
            return sortedArray[(len / 2)];
        }
        // Case 2: Even Length Array
        // NOTE: This is not really necessary for this exercise only, but it is
        // better to correctly implement how the median works for even length arrays
        else {
            return (float) (sortedArray[(len / 2) - 1] + sortedArray[(len / 2)]) / 2;
        }
    }

    /**
     * Returns the mode(s) of an array of positive integers.
     * <p>
     * If there is no mode, returns an empty list. Otherwise, returns a list of all
     * the modes.
     *
     * @param numberArray the array of positive integers to compute the mode of
     * @return the mode of the array of integers
     */
    public int[] calculateMode(int[] numberArray) {
        int[] setArray = createSetArray(numberArray);
        int[] countsArray = new int[setArray.length];
        for (int i = 0; i < countsArray.length; i++) {
            countsArray[i] = countOccurrencesInArray(numberArray, setArray[i]);
        }

        // Find the maximum frequency
        int maximumCount = -1;
        for (int number : countsArray) {
            if (number > maximumCount) {
                maximumCount = number;
            }
        }

        // Special Case: If the maximumCount is 1, there is no mode.
        if (maximumCount == 1) {
            return new int[0];
        }

        // Count the number of modes
        int numberOfModes = 0;
        for (int count : countsArray) {
            if (count == maximumCount) {
                numberOfModes++;
            }
        }

        // Create and fill the array with the modes
        int[] modes = new int[numberOfModes];
        int modeCount = 0;
        for (int i = 0; i < countsArray.length; i++) {
            if (countsArray[i] == maximumCount) {
                modes[modeCount++] = setArray[i];
            }
        }
        return modes;
    }

    /**
     * Returns a copy of an array and removes duplicate elements.
     *
     * @param numberArray the array of integers to remove duplicate elements of
     * @return a new array with all unique integers
     */
    private int[] createSetArray(int[] numberArray) {
        // Create a new array at least the size of the number array
        int len = numberArray.length;
        int[] temp = new int[len];

        // Fill the array with the numbers of number array, not repeating duplicates.
        int current = 0;
        for (int number : numberArray) {
            if (!arrayContains(temp, number)) {
                temp[current++] = number;
            } else {
                len--;
            }
        }

        // Create the actual array with the new length and fill with the values of temp
        // NOTE: Can be replaced with System.arraycopy()
        int[] setArray = new int[len];
        for (int i = 0; i < len; i++) {
            setArray[i] = temp[i];
        }

        return setArray;

    }

    /**
     * Returns a boolean value indicating if an integer is in an array.
     *
     * @param numberArray  the array of integers to search
     * @param numberToFind the integer to search for in the array
     * @return a boolean value indicating the presence of the integer in the array
     */
    private boolean arrayContains(int[] numberArray, int numberToFind) {
        for (int number : numberArray) {
            if (number == numberToFind) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of times an element occurs in an array
     *
     * @param numberArray   the array of integers used for counting
     * @param numberToCount the integer to count
     * @return the number of times the number occurs in the array
     */
    private int countOccurrencesInArray(int[] numberArray, int numberToCount) {
        int count = 0;
        for (int number : numberArray) {
            if (number == numberToCount) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns a sorted array of integers using selection sort in ascending order.
     *
     * @param numberArray the array of integers to sort in ascending order
     * @return the sorted array
     */
    private int[] sortNumberArray(int[] numberArray) {
        // Create a copy of the original array
        int[] numberArrayCopy = numberArray.clone();

        // Selection Sort
        int len = numberArray.length;
        for (int i = 0; i < len; i++) {
            // Find the minimum value in the array starting from the unsorted part of the array
            int positionOfMinimum = i;
            for (int j = i + 1; j < len; j++) {
                if (numberArrayCopy[positionOfMinimum] > numberArrayCopy[j]) {
                    positionOfMinimum = j;
                }
            }
            // Swap the minimum value found to the sorted part of the array (the bottom)
            int temp = numberArrayCopy[positionOfMinimum];
            numberArrayCopy[positionOfMinimum] = numberArrayCopy[i];
            numberArrayCopy[i] = temp;
        }

        return numberArrayCopy;
    }

    /**
     * Returns an array of integers obtained from the scanner.
     *
     * @param scanner the Scanner instance to read from
     * @return an array of integers
     */
    private int[] inputNumbers(Scanner scanner) {
        // Create an array with size INPUT_QUANTITY
        final int INPUT_QUANTITY = 5;
        int[] array = new int[INPUT_QUANTITY];

        // Get INPUT_QUANTITY numbers
        int i = 0;
        while (i < INPUT_QUANTITY) {
            System.out.printf("[%d] Enter an integer: ", i + 1);
            // Make sure all numbers are positive integers
            try {
                array[i] = scanner.nextInt();
                if (array[i] < 0) {
                    System.out.println("\tOops! Please enter a positive integer!");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("\tOops! Please enter a positive integer!");
                continue;
            } finally {
                // Clear the scanner buffer
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
            i++;
        }

        return array;
    }
}
