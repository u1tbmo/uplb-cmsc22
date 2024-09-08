/**
 * A program that allows the user to store songs and manage them through a library
 * through a Command-Line Interface.
 * <p>
 * <b>Note:</b> This program is written in JDK 21 and may require JDK 21 or higher.
 *
 * @author Euan Jed S. Tabamo
 * @since 21.0.4
 * @created 05 September 2024 19:43
 */

package musiclibrary;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A simple music library class that allows for the management of songs in a library with
 * view capabilities for all songs and filtered by artist.
 */
public class MusicLibrary {

    // Constants
    private static final int MAX_SONGS = 20;
    private static final int MAX_COLUMNS = 2;
    private static final int SONG_COL = 0;
    private static final int ARTIST_COL = 1;
    private static final int STRING_PADDING = 4;

    // Used to find the longest length song names and artists in a library + string padding
    private static int[] findMaxStringLengths(String[][] library, String artistFilter) {
        // Initialize an array of two values
        int[] maxStringLengths = new int[2];

        // Find the longest length songName String in the array
        int max = 0;
        for (String[] rows : library) {
            // If there is a filter, if the artist of the row is not the artist of the filter,
            // then don't consider them for measuring the max length String.
            if (artistFilter != null) {
                if (!rows[ARTIST_COL].equalsIgnoreCase(artistFilter)) {
                    continue;
                }
            }
            if (rows[SONG_COL].length() > max) {
                max = rows[SONG_COL].length();
            }
        }
        // Add additional padding to the length
        maxStringLengths[SONG_COL] = max + STRING_PADDING;

        // Find the longest length artist String in the array
        max = 0;
        for (String[] rows : library) {
            // If we find a length that is greater than our max, set the max to that length
            if (rows[ARTIST_COL].length() > max) {
                max = rows[ARTIST_COL].length();
            }
        }
        // Add additional padding to the length
        maxStringLengths[ARTIST_COL] = max + STRING_PADDING;

        return maxStringLengths;
    }

    // Prints a menu and returns the choice of the user
    private static int menu(Scanner sc) {
        // Print the menu choices and ask for an integer as a choice
        int choice;
        while (true) {
            System.out.println();
            System.out.println("-- Main Menu --------------------------");
            System.out.println("1 | View Library");
            System.out.println("2 | View Artist's Songs");
            System.out.println("3 | Add Song");
            System.out.println("4 | Update Song");
            System.out.println("0 | Exit");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                // Tell the user to enter a valid number when nextInt() throws an InputMismatchException
                System.out.println();
                System.out.println("Please enter a valid number.");
            } finally {
                // Clear the buffer to remove excess newlines
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
        }
        System.out.println();
        return choice;
    }

    /**
     * Prints a 2-column view of the music library
     *
     * @param library the two-dimensional array containing songs and artists
     */
    public static void viewLibrary(String[][] library) {
        // If the music library is empty, then there are no songs to display.
        if (library.length == 0) {
            System.out.println("No songs saved. Please add songs!");
            return;
        }

        // Get the max string lengths for song names and artists with padding
        // This is to align the song names and artists for printing under the headers
        int[] maxStringLengths = MusicLibrary.findMaxStringLengths(library, null);
        int songMaxLen = maxStringLengths[SONG_COL];
        int artistMaxLen = maxStringLengths[ARTIST_COL];

        int initialSongMaxLen = "SONG NAME    ".length(); // 13
        int initialArtistMaxLen = "ARTIST    ".length();  // 10

        // NOTE: Can be replaced with the Math.max() method
        songMaxLen = (initialSongMaxLen > songMaxLen) ? initialSongMaxLen : songMaxLen;
        artistMaxLen = (initialArtistMaxLen > artistMaxLen) ? initialArtistMaxLen : artistMaxLen;

        // Format the header using the String.format() method
        String header = "";
        header += String.format("%-" + songMaxLen + "s", "SONG NAME    ");
        header += String.format("%-" + artistMaxLen + "s", "ARTIST    ");

        // Print header
        System.out.println(header);

        // Print the songs and artists using the String.format() method
        for (String[] row : library) {
            String songName = row[SONG_COL];
            String artist = row[ARTIST_COL];
            String rowDetails = "";
            rowDetails += String.format("%-" + songMaxLen + "s", songName);
            rowDetails += String.format("%-" + artistMaxLen + "s", artist);
            System.out.println(rowDetails);
        }
    }

    /**
     * Prints a 2-column view of the music library of only the artist specified by the user
     *
     * @param library the two-dimensional array containing songs and artists
     * @param sc      the scanner to read from
     */
    public static void viewArtistSongs(String[][] library, Scanner sc) {
        // If the music library is empty, then there are no songs to display.
        if (library.length == 0) {
            System.out.println("No songs saved. Please add songs!");
            return;
        }

        // Ask for the artist name
        // Use trim() to remove excess whitespace and isEmpty() to check if the string is empty
        String artistName;
        do {
            System.out.print("Enter artist name: ");
            artistName = sc.nextLine().trim();
            if (artistName.isEmpty()) {
                System.out.println("Please enter an artist name.");
            }

        } while (artistName.isEmpty());


        // Get the max string lengths for song names and artists with padding
        // This is to align the song names and artists for printing under the headers
        int[] maxStringLengths = MusicLibrary.findMaxStringLengths(library, artistName);
        int songMaxLen = maxStringLengths[SONG_COL];
        int artistMaxLen = maxStringLengths[ARTIST_COL];

        // Get the length of the headers
        int initialSongMaxLen = "SONG NAME    ".length();
        int initialArtistMaxLen = "ARTIST    ".length();

        // NOTE: Can be replaced with Math.max()
        songMaxLen = (initialSongMaxLen > songMaxLen) ? initialSongMaxLen : songMaxLen;
        artistMaxLen = (initialArtistMaxLen > artistMaxLen) ? initialArtistMaxLen : artistMaxLen;

        // Format the header
        String header = "";
        header += String.format("%-" + songMaxLen + "s", "SONG NAME    ");
        header += String.format("%-" + artistMaxLen + "s", "ARTIST    ");


        // Print the songs and artists
        int count = 0;
        for (String[] row : library) {
            String songName = row[SONG_COL];
            String artist = row[ARTIST_COL];

            // If the artist name does not match the input artist name, then skip to the next song
            if (!artist.equalsIgnoreCase(artistName)) { // Usage of equalsIgnoreCase() to ignore case sensitivity
                continue;
            }

            // Print header if a song exists
            if (count == 0) {
                System.out.println(header);
            }

            // Update the count for every song from the artist
            count++;
            String rowDetails = "";
            rowDetails += String.format("%-" + songMaxLen + "s", songName);
            rowDetails += String.format("%-" + artistMaxLen + "s", artist);
            System.out.println(rowDetails);
        }

        // If count is 0, then there are no songs for the input artist
        if (count == 0) {
            System.out.println("No songs exist for that artist.");
        }
    }

    /**
     * Allows the user to add a song and artist to the library
     *
     * @param library the two-dimensional array containing songs and artists
     * @param sc      the scanner to read from
     * @return the updated library
     */
    public static String[][] addSong(String[][] library, Scanner sc) {
        String songName;
        String songArtist;
        String[][] newLibrary;

        System.out.println("-- Adding Song ------------------------");

        // If the music library is empty, then initialize a 2d array with 1 row and 2 columns
        if (library.length == 0) {
            newLibrary = new String[1][MAX_COLUMNS];
        } else if (library.length == MAX_SONGS) {
            System.out.println("Maximum number of songs have been added.");
            return library;
        }
        // Otherwise, copy the old array to a new array with an additional row using the Arrays.copyOf() method
        else {
            newLibrary = Arrays.copyOf(library, library.length + 1); //
            newLibrary[library.length] = new String[2];
        }

        // Ask for the song details and artist
        // Use trim() to remove excess whitespace and isEmpty() to check if the string is empty
        do {
            System.out.print("Enter song name:   ");
            songName = sc.nextLine().trim();
            if (songName.isEmpty()) {
                System.out.println("Please enter a song name.");
            }

        } while (songName.isEmpty());

        do {
            System.out.print("Enter song artist: ");
            songArtist = sc.nextLine().trim();
            if (songArtist.isEmpty()) {
                System.out.println("Please enter a song artist.");
            }

        } while (songArtist.isEmpty());

        // If the song name already exists in the library and the artist name is the same, then print an error message
        for (String[] row : library) {
            if (row[SONG_COL].equals(songName) && row[ARTIST_COL].equalsIgnoreCase(songArtist)) {
                System.out.println("Song already exists in the library.");
                return library;
            }
        }

        // Add the song and artist to the library
        newLibrary[newLibrary.length - 1][SONG_COL] = songName;
        newLibrary[newLibrary.length - 1][ARTIST_COL] = songArtist;

        System.out.println("Added the song to the library!");

        // Return the new library
        return newLibrary;
    }

    // A method that asks the user for the song name and artist to update the library
    private static void updateSongDetails(String[][] library, int index, Scanner sc) {
        // Initialize song name and artist name
        String songName;
        String artistName;

        System.out.println("-- Update Song ------------------------");

        // Ask for the song details and artist
        // Use trim() to remove excess whitespace and isEmpty() to check if the string is empty
        do {
            System.out.print("Enter updated song name (Enter '---' to keep):   ");
            songName = sc.nextLine().trim();
            if (songName.isEmpty()) {
                System.out.println("Please enter a song name.");
            }

        } while (songName.isEmpty());

        do {
            System.out.print("Enter updated song artist (Enter '---' to keep): ");
            artistName = sc.nextLine().trim();
            if (artistName.isEmpty()) {
                System.out.println("Please enter a song artist.");
            }

        } while (artistName.isEmpty());


        // If the song name already exists in the library and the artist name is the same, then print an error message
        for (String[] row : library) {
            if (row[SONG_COL].equals(songName) && row[ARTIST_COL].equalsIgnoreCase(artistName)) {
                System.out.println("Song already exists in the library.");
                return;
            }
        }

        // Update the song and artist in the library
        // If the input is "---", then retain the old information
        library[index][SONG_COL] = (songName.equals("---")) ? library[index][SONG_COL] : songName;
        library[index][ARTIST_COL] = (artistName.equals("---")) ? library[index][ARTIST_COL] : artistName;
        System.out.println("Updated the song in the library!");
    }

    /**
     * Allows the user to update a song's details given its song.
     * If there are multiple artists with the same song, then the artist is asked for clarification.
     *
     * @param library the two-dimensional array containing songs and artists
     * @param sc      the scanner to read from
     */
    public static void updateSong(String[][] library, Scanner sc) {
        // Initialize the song name and the artist name
        String songName;
        String artistName;

        // If the music library is empty, then there are no songs to display.
        if (library.length == 0) {
            System.out.println("No songs saved. Please add songs!");
            return;
        }

        System.out.println("-- Update Song ------------------------");

        // Print the library
        MusicLibrary.viewLibrary(library);

        // Ask for the song name to update
        // Use trim() to remove excess whitespace and isEmpty() to check if the string is empty
        do {
            System.out.print("Enter song name to update: ");
            songName = sc.nextLine().trim();
            if (songName.isEmpty()) {
                System.out.println("Please enter a song name.");
            }

        } while (songName.isEmpty());

        // Count the number of times the song name appears in the library
        int count = 0;
        for (String[] row : library) {
            if (row[SONG_COL].equals(songName)) {
                count++;
            }
        }
        // If the song name does not exist in the library, then print an error message
        if (count == 0) {
            System.out.println("Song does not exist in the library.");
            return;
        }
        // If the song name appears more than once in the library, then ask for the artist name
        if (count > 1) {
            do {
                System.out.print("Enter artist name: ");
                artistName = sc.nextLine().trim();
                if (artistName.isEmpty()) {
                    System.out.println("Please enter an artist name.");
                }

            } while (artistName.isEmpty());
        }
        // If the song name appears only once in the library, then update the song details
        else {
            artistName = library[0][ARTIST_COL];
        }
        // Find the song in the library
        for (int i = 0; i < library.length; i++) {
            if (library[i][SONG_COL].equals(songName)) {
                // If the song name appears more than once in the library, then check the artist name
                if (count > 1) {
                    if (library[i][ARTIST_COL].equalsIgnoreCase(artistName)) {
                        MusicLibrary.updateSongDetails(library, i, sc);
                    } else {
                        System.out.println("Song with that artist does not exist in the library.");
                    }
                }
                // If the song name appears only once in the library, then update the song details
                else {
                    MusicLibrary.updateSongDetails(library, i, sc);
                }

                return;
            }
        }
    }

    /**
     * The main method
     *
     * @param args the array of command line arguments
     */
    public static void main(String[] args) {
        // Create a Scanner instance
        Scanner sc = new Scanner(System.in);

        // Create a 2D array to store the songs in
        String[][] library = new String[0][0];

        // Welcome the user to the Music Library program
        System.out.println("Welcome to the Music Library!");

        // Call the menu and get the choice from the user
        int choice;
        do {
            choice = MusicLibrary.menu(sc);
            switch (choice) {
                case 1: // View Library
                    MusicLibrary.viewLibrary(library);
                    break;
                case 2: // View Artist's Songs
                    MusicLibrary.viewArtistSongs(library, sc);
                    break;
                case 3: // Add Song
                    library = MusicLibrary.addSong(library, sc);
                    break;
                case 4: // Update Song
                    MusicLibrary.updateSong(library, sc);
                    break;
                case 0: // Exit
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
                    break;
            }
        } while (choice != 0);

        // Close the scanner to avoid resource leaks
        sc.close();
    }
}
