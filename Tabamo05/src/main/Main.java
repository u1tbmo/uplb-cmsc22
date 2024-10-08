/**
 * A program that allows the user to use a color picker.
 * <p>
 * <b>Note:</b> This program is written in JDK 21 and may require JDK 21 or higher.
 *
 * @author Euan Jed S. Tabamo
 * @created 04 October 2024, 17:48
 * @since 21.0.4
 */

package main;

import color.ColorScheme;
import color.Picker;

import java.util.HashMap;
import java.util.Scanner;

import static color.InputHelper.inputInteger;

/**
 * The main program class
 */
public class Main {

    // Create a HashMap of colors where the key is the title
    static HashMap<String, String[][]> colors = new HashMap<>();

    /**
     * The main program
     *
     * @param args the main program args
     */
    public static void main(String[] args) {
        // Initialize Scanner
        Scanner sc = new Scanner(System.in);


        // Main Menu Loop
        int choice = -1;
        while (choice != 0) {
            // Print the menu
            System.out.println("Welcome to the Color Picker!");
            System.out.println("1 | Create a color scheme");
            System.out.println("2 | Show all color schemes");
            System.out.println("0 | Exit");

            // Ask for an integer from 0 to 2
            choice = inputInteger(sc, 0, 2);
            switch (choice) {
                case 1:
                    createColorPalette(sc);
                    break;
                case 2:
                    showSavedColorPalette();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    sc.close();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Creates a new color palette and puts it into the global colors HashMap
     *
     * @param sc Scanner object to get user input
     */
    public static void createColorPalette(Scanner sc) {
        ColorScheme cs = new ColorScheme();
        Picker p = new Picker();

        String title;
        int schemeIdx;
        int colorIdx;

        System.out.println("\n--- Create Color Palette --------------------");

        // Ask for a non-empty string as the title
        while (true) {
            System.out.print("Color Pick Title: ");
            title = sc.nextLine();
            if (title.isEmpty()) {
                System.out.println("Please enter a non-empty title.");
            } else {
                break;
            }
        }

        // Color Picker Loop
        do {
            // Ask for the scheme
            schemeIdx = p.showMainMenu(sc);
            if (schemeIdx == 0) {
                return; // Terminate the function if the user selected exit
            }
            // Ask for the color
            colorIdx = p.showColorsMenu(sc);
        } while (colorIdx == 0); // If the user selected to go back, repeat the loop

        // Normalize index inputs since the menu options start from 1
        schemeIdx -= 1;
        colorIdx -= 1;

        // Show the colors
        cs.showColorsHelper(schemeIdx, colorIdx);

        // Get the colors of the color scheme and store into an array
        String[] selectedColors = cs.getColors(ColorScheme.COLOR_SCHEMES.get(schemeIdx), colorIdx);

        // Create the data to put into the HashMap
        String[][] schemeData = new String[2][];
        schemeData[0] = new String[]{ColorScheme.COLOR_SCHEMES.get(schemeIdx)}; // Let the first row be the scheme
        schemeData[1] = selectedColors; // Let the second row be the array of selected colors

        // Add to the HashMap
        colors.put(title, schemeData);
        System.out.println("System: Saved color palette " + title + "!");
        System.out.println("---------------------------------------------\n");
    }

    /**
     * Shows the saved color palettes in the colors HashMap
     */
    public static void showSavedColorPalette() {
        if (colors.isEmpty()) {
            System.out.println();
            System.out.println("You currently do not have any saved color palettes!");
            System.out.println();
            return;
        }

        // Print the saved color schemes
        System.out.println("\n--- Your Color Palettes ---------------------");
        for (String key : colors.keySet()) {
            System.out.println(key);
            System.out.println("  Scheme: " + colors.get(key)[0][0]);
            System.out.print("  Colors: ");
            System.out.println(String.join(", ", colors.get(key)[1]));
        }
        System.out.println("---------------------------------------------\n");
    }

}
