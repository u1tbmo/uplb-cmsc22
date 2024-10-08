package color;

import java.util.ArrayList;

/**
 * The ColorScheme class is responsible for showing the colors of a color scheme with one, two, three, or four colors
 * based on a color scheme.
 */
public class ColorScheme implements Color {

    // Constants
    public static final int MONOLOGOUS = 0;
    public static final int COMPLEMENTARY = 1;
    public static final int ANALOGOUS = 2;
    public static final int TRIADIC = 3;
    public static final int TETRADIC = 4;
    public static final ArrayList<String> COLOR_SCHEMES = new ArrayList<>() {
        {
            add("Monologous");
            add("Complementary");
            add("Analogous");
            add("Triadic");
            add("Tetradic");
        }
    };


    // Constant for the maximum number of colors
    private static final int MAX_COLORS = 4;

    /**
     * Shows the colors of a scheme given the index of the scheme in COLOR_SCHEME and the index of the color in
     * COLORS
     *
     * @param schemeIdx the index of the scheme
     * @param colorIdx  the index of the color
     */
    public void showColorsHelper(int schemeIdx, int colorIdx) {
        String scheme = COLOR_SCHEMES.get(schemeIdx);
        String firstColor = Color.COLORS.get(colorIdx);
        switch (schemeIdx) {
            case MONOLOGOUS:
                System.out.printf("Info: The %s color for %s is: ", scheme, firstColor);
                showColors(COLOR_SCHEMES.getFirst(), colorIdx);
                break;
            case COMPLEMENTARY:
                System.out.printf("Info: The %s colors for %s are: ", scheme, firstColor);
                showColors(COLOR_SCHEMES.getFirst(), colorIdx, 6);
                break;
            case ANALOGOUS:
                System.out.printf("Info: The %s colors for %s are: ", scheme, firstColor);
                showColors(COLOR_SCHEMES.get(schemeIdx - 1), colorIdx, 1, 1);
                break;
            case TRIADIC:
                System.out.printf("Info: The %s colors for %s are: ", scheme, firstColor);
                showColors(COLOR_SCHEMES.get(schemeIdx - 1), colorIdx, 4, 4);
                break;
            case TETRADIC:
                System.out.printf("Info: The %s colors for %s are: ", scheme, firstColor);
                showColors(COLOR_SCHEMES.get(schemeIdx - 1), colorIdx, 2, 4, 2);
                break;
            default:
                break;
        }
    }

    /**
     * Obtains the colors for a given color scheme and color index.
     *
     * @param scheme     the color scheme to use for obtaining the colors
     * @param colorIndex the first color in the scheme
     * @return all colors in the scheme
     */
    public String[] getColors(String scheme, int colorIndex) {
        ArrayList<String> selectedColors = new ArrayList<>();

        // Switch based on the scheme
        switch (scheme) {
            case "Monologous":
                // For Monologous: select the color itself
                return new String[]{COLORS.get(colorIndex)};

            case "Complementary":
                // For Complementary: select two colors opposite to each other on the wheel
                selectedColors.add(COLORS.get(colorIndex));
                selectedColors.add(COLORS.get((colorIndex + 6) % COLORS.size()));
                break;

            case "Analogous":
                // For Analogous: select 3 consecutive colors
                for (int i = 0; i < 3; i++) {
                    int index = (colorIndex + i) % COLORS.size(); // Circular indexing
                    selectedColors.add(COLORS.get(index));
                }
                break;

            case "Triadic":
                // For Triadic: select 3 colors evenly spaced on the wheel (every 4th color)
                for (int i = 0; i < 3; i++) {
                    int index = (colorIndex + i * 4) % COLORS.size();
                    selectedColors.add(COLORS.get(index));
                }
                break;

            case "Tetradic":
                // For Tetradic: select 4 colors spaced by 3 positions each
                for (int i = 0; i < 4; i++) {
                    int index = (colorIndex + i * 3) % COLORS.size();
                    selectedColors.add(COLORS.get(index));
                }
                break;

            default:
                System.out.println("Invalid scheme.");
                return new String[]{};
        }

        // Convert the List to an array and return it
        return selectedColors.toArray(new String[0]);
    }

    /**
     * Shows the colors of a color scheme with one colorIdx.
     *
     * @param scheme   the color scheme that should be shown
     * @param colorIdx the index of the colorIdx in the colors ArrayList
     */
    @Override
    public void showColors(String scheme, int colorIdx) {
        System.out.println(Color.COLORS.get(colorIdx));
    }

    /**
     * Shows the colors of a color scheme with two colors.
     *
     * @param scheme   the color scheme that should be shown
     * @param colorIdx the index of the colorIdx in the colors ArrayList
     * @param i        the number of steps to the second colorIdx in the color scheme from the first colorIdx
     */
    @Override
    public void showColors(String scheme, int colorIdx, int i) {
        // Store the list of colors in a list
        ArrayList<String> colors = new ArrayList<>();
        colors.add(Color.COLORS.get(colorIdx));

        int[] steps = {i};
        int currentIndex = colorIdx;

        // Get the colors using steps
        for (int step : steps) {
            currentIndex = (currentIndex + step) % Color.COLORS.size();
            colors.add(Color.COLORS.get(currentIndex));
        }

        // Join the arrayList then print
        System.out.println(String.join(", ", colors));
    }

    /**
     * Shows the colors of a color scheme with three colors.
     *
     * @param scheme   the color scheme that should be shown
     * @param colorIdx the index of the colorIdx in the colors ArrayList
     * @param i        the number of steps to the second colorIdx in the color scheme from the first colorIdx
     * @param j        the number of steps to the third colorIdx in the color scheme from the second colorIdx
     */
    @Override
    public void showColors(String scheme, int colorIdx, int i, int j) {
        // Store the list of colors in a list
        ArrayList<String> colors = new ArrayList<>();
        colors.add(Color.COLORS.get(colorIdx));

        int[] steps = {i, j};
        int currentIndex = colorIdx;

        // Get the colors using steps
        for (int step : steps) {
            currentIndex = (currentIndex + step) % Color.COLORS.size();
            colors.add(Color.COLORS.get(currentIndex));
        }

        // Join the arrayList then print
        System.out.println(String.join(", ", colors));
    }

    /**
     * Shows the colors of a color scheme with four colors.
     *
     * @param scheme   the color scheme that should be shown
     * @param colorIdx the index of the colorIdx in the colors ArrayList
     * @param i        the number of steps tho the second colorIdx in the color scheme from the first colorIdx
     * @param j        the number of steps to the third colorIdx in the color scheme from the second colorIdx
     * @param k        the number of steps to the fourth colorIdx in the color scheme from the third colorIdx
     */
    @Override
    public void showColors(String scheme, int colorIdx, int i, int j, int k) {
        // Store the list of colors in a list
        ArrayList<String> colors = new ArrayList<>();
        colors.add(Color.COLORS.get(colorIdx));

        int[] steps = {i, j, k};
        int currentIndex = colorIdx;

        // Get the colors using steps
        for (int step : steps) {
            currentIndex = (currentIndex + step) % Color.COLORS.size();
            colors.add(Color.COLORS.get(currentIndex));
        }

        // Join the arrayList then print
        System.out.println(String.join(", ", colors));
    }
}
