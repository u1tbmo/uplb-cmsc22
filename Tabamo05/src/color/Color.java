package color;

import java.util.ArrayList;

/**
 * The Color interface is responsible for a list of colors from a 12-color color wheel and method signatures for showing
 * the colors of a color scheme with one, two, three, or four colors.
 */
public interface Color {
    // Initialize an ArrayList of colors
    ArrayList<String> COLORS = new ArrayList<>() {
        {
            // ANSI escape codes for color
            // https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit
            add("\u001B[38;5;226mYellow\u001B[0m");
            add("\u001B[38;5;214mYellow-Orange\u001B[0m");
            add("\u001B[38;5;208mOrange\u001B[0m");
            add("\u001B[38;5;202mRed-Orange\u001B[0m");
            add("\u001B[38;5;9mRed\u001B[0m");
            add("\u001B[38;5;126mRed-Violet\u001B[0m");
            add("\u001B[38;5;92mViolet\u001B[0m");
            add("\u001B[38;5;57mBlue-Violet\u001B[0m");
            add("\u001B[38;5;12mBlue\u001B[0m");
            add("\u001B[38;5;36mBlue-Green\u001B[0m");
            add("\u001B[38;5;34mGreen\u001B[0m");
            add("\u001B[38;5;82mYellow-Green\u001B[0m");
        }
    };

    /**
     * Shows the colors of a colorIdx scheme with one colorIdx.
     *
     * @param scheme   the colorIdx scheme that should be shown
     * @param colorIdx the index of the colorIdx in the colors ArrayList
     */
    void showColors(String scheme, int colorIdx);

    /**
     * Shows the colors of a colorIdx scheme with two colors.
     *
     * @param scheme   the colorIdx scheme that should be shown
     * @param colorIdx the index of the colorIdx in the colors ArrayList
     * @param i        the number of steps to the second colorIdx in the colorIdx scheme from the first colorIdx
     */
    void showColors(String scheme, int colorIdx, int i);

    /**
     * Shows the colors of a colorIdx scheme with three colors.
     *
     * @param scheme   the colorIdx scheme that should be shown
     * @param colorIdx the index of the colorIdx in the colors ArrayList
     * @param i        the number of steps to the second colorIdx in the colorIdx scheme from the first colorIdx
     * @param j        the number of steps to the third colorIdx in the colorIdx scheme from the second colorIdx
     */
    void showColors(String scheme, int colorIdx, int i, int j);

    /**
     * Shows the colors of a colorIdx scheme with four colors.
     *
     * @param scheme   the colorIdx scheme that should be shown
     * @param colorIdx the index of the colorIdx in the colors ArrayList
     * @param i        the number of steps tho the second colorIdx in the colorIdx scheme from the first colorIdx
     * @param j        the number of steps to the third colorIdx in the colorIdx scheme from the second colorIdx
     * @param k        the number of steps to the fourth colorIdx in the colorIdx scheme from the third colorIdx
     */
    void showColors(String scheme, int colorIdx, int i, int j, int k);
}
