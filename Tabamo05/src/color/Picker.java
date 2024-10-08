package color;

import java.util.Scanner;

import static color.InputHelper.inputInteger;

/**
 * The Picker class is responsible for showing the main menu of schemes and colors menu.
 */
public class Picker {

    /**
     * Shows the main menu and asks the user for a choice (scheme).
     *
     * @param sc the Scanner object to use for input
     * @return the user's choice
     */
    public int showMainMenu(Scanner sc) {
        System.out.println("What color scheme would you like to choose?");

        // Print every color from the ColorScheme class
        for (int i = 0; i < ColorScheme.COLOR_SCHEMES.size(); i++) {
            System.out.printf("%d | %s\n", i + 1, ColorScheme.COLOR_SCHEMES.get(i));
        }
        System.out.println("0 | Exit");

        return inputInteger(sc, 0, 5);
    }

    /**
     * Shows the colors menu and asks the user for a choice.
     *
     * @param sc the Scanner object to use for input
     * @return the user's choice
     */
    public int showColorsMenu(Scanner sc) {
        System.out.println("What color would you like to choose?");

        // Print every color from the Colors interface
        for (int i = 0; i < Color.COLORS.size(); i++) {
            System.out.printf("%2d | %s\n", i + 1, Color.COLORS.get(i));
        }
        System.out.println(" 0 | Go back");

        return inputInteger(sc, 0, Color.COLORS.size());
    }
}
