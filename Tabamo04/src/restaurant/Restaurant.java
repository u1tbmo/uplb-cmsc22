package restaurant;

import customer.RegularCustomer;
import customer.Renter;
import helpers.ArrayHelpers;
import helpers.InputHelpers;
import helpers.StringHelpers;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import static helpers.StringHelpers.PADDING;

/**
 * Represents a restaurant that contains records for regular customers and renters.
 *
 * <p> A {@code Restaurant} can add, remove, and update records of regular customers and renters.
 */
public class Restaurant {

    // Static Attributes
    private final static int MAX_CUSTOMERS = 50;

    // Attributes
    private final String restaurantName;
    private final RegularCustomer[] regularCustomers;
    private final Renter[] renters;
    private float totalSales;
    private int regularCustomerQty;
    private int renterQty;

    // Constructor

    /**
     * Creates a new {@code Restaurant} instance with 0 sales, regular customers, and renters.
     *
     * @param restaurantName the name of the restaurant
     */
    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
        this.regularCustomers = new RegularCustomer[MAX_CUSTOMERS];
        this.renters = new Renter[MAX_CUSTOMERS];
        this.totalSales = 0;
        this.regularCustomerQty = 0;
        this.renterQty = 0;
    }

    // Methods

    /**
     * Presents the user with options to manage customer records.
     *
     * @param sc the Scanner object to use for input
     */
    public void manageCustomerRecords(Scanner sc) {
        int input;
        do {
            System.out.println();
            System.out.println("═══ Customer Records Management ═══════════════════");
            System.out.println("1 | Add customer");
            System.out.println("2 | Search for customer");
            System.out.println("3 | View records");
            System.out.println("4 | Simulate sales");
            System.out.println("5 | Update customer");
            System.out.println("6 | Remove customer");
            System.out.println("0 | Exit");

            input = InputHelpers.inputInteger(sc, "Enter choice: ", 0, Integer.MAX_VALUE);
            System.out.println();
            processManageCustomerRecords(sc, input);
        } while (input != 0);
    }

    /**
     * Given a choice, calls the appropriate method for the program.
     *
     * @param sc     the Scanner object to use for input
     * @param choice the integer representing the method to call
     */
    private void processManageCustomerRecords(Scanner sc, int choice) {
        switch (choice) {
            case 1:
                addCustomer(sc);
                break;
            case 2:
                findCustomer(sc);
                break;
            case 3:
                viewCustomerRecord();
                break;
            case 4:
                simulateSales();
                break;
            case 5:
                modifyCustomer(sc);
                break;
            case 6:
                deleteCustomer(sc);
                break;
            case 0:
                System.out.printf("Thank you for using %s's Customer Record Manager. Goodbye!\n", this.restaurantName);
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    /**
     * Welcomes the user to the restaurant's customer records program.
     *
     * @param username the name of the user to greet
     */
    public void welcomeUser(String username) {
        System.out.printf("Welcome to %s, %s! Choose from the options below.\n", this.restaurantName, username);
    }

    /**
     * Asks the user to input the desired customer type.
     *
     * @param sc the Scanner object to use for input
     * @return the customer type selected by the user
     */
    private char inputCustomerType(Scanner sc) {
        String customerType;
        while (true) {
            System.out.println("Select Customer Type: ");
            System.out.println("C | Regular Customer");
            System.out.println("R | Renter");
            customerType = InputHelpers.inputString(sc, "Enter choice: ");
            if (!customerType.equals("C") && !customerType.equals("R")) {
                System.out.println("Invalid choice. Please try again.");
            } else {
                break;
            }
        }
        return customerType.charAt(0);
    }

    /**
     * Adds a customer to the restaurant records.
     *
     * @param sc the Scanner object to use for input
     */
    public void addCustomer(Scanner sc) {
        System.out.println("═══ Create Customer Record ════════════════════════");

        // If the restaurant is full, then the restaurant cannot add another customer
        int totalCustomers = this.regularCustomerQty + this.renterQty;
        if (totalCustomers == MAX_CUSTOMERS) {
            System.out.println("Sorry! The restaurant's customer record limit has been reached.");
            return;
        }

        // Get the desired customer type to add to the restaurant records
        char customerType = inputCustomerType(sc);

        // Ask for the customer's customer code and other details based on customer type
        if (customerType == 'C') {
            addRegularCustomer(sc);
        } else {
            addRenter(sc);
        }
    }

    /**
     * Adds a regular customer to the restaurant records.
     *
     * @param sc the Scanner object to use for input
     */
    private void addRegularCustomer(Scanner sc) {
        // Ask for the customer's name
        String lastName = InputHelpers.inputString(sc, "Enter last name:  ");
        String firstName = InputHelpers.inputString(sc, "Enter first name: ");

        // Ask for loyalty points
        int loyaltyPoints = InputHelpers.inputInteger(sc, "Enter loyalty points: ", 0, Integer.MAX_VALUE);

        // Add to the restaurant's records
        RegularCustomer c = new RegularCustomer(firstName, lastName, this, loyaltyPoints);
        this.regularCustomers[this.regularCustomerQty] = c;
        this.regularCustomerQty++;

        // View the state of the new regular customer
        c.viewState();
    }

    /**
     * Adds a renter to the restaurant records.
     *
     * @param sc the Scanner object to use for input
     */
    private void addRenter(Scanner sc) {
        // Ask for the customer's name
        String lastName = InputHelpers.inputString(sc, "Enter last name:  ");
        String firstName = InputHelpers.inputString(sc, "Enter first name: ");

        // Ask for deposit
        float deposit = InputHelpers.inputFloat(sc, "Enter deposit (at least Php3000): ", 3000, Float.MAX_VALUE);

        // Add to the restaurant's records
        Renter r = new Renter(firstName, lastName, this, deposit);
        this.renters[this.renterQty] = r;
        this.renterQty++;

        // View the state of the new renter
        r.viewState();
    }

    /**
     * Searches for a customer in the restaurant records and displays the state of the customer.
     *
     * @param sc the Scanner object to use for input
     */
    public void findCustomer(Scanner sc) {
        System.out.println("═══ Search Customer Record ════════════════════════");

        // If the record is empty, then there is nothing to search.
        int totalCustomers = this.regularCustomerQty + this.renterQty;
        if (totalCustomers == 0) {
            System.out.println("Sorry! There are no customer records to search.");
            return;
        }

        // Ask for a customer code
        int customerCode = -1;
        boolean customerCodeIsValid = false;
        do {
            try {
                customerCode = InputHelpers.inputInteger(sc, "Enter customer code: ", 1001, 2999);
                customerCodeIsValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!customerCodeIsValid);

        // Search the record for the customer code and view the state if it exists
        int index;
        if (customerCode >= 1001 && customerCode <= 1999) {
            index = ArrayHelpers.indexOfRegularCustomerCode(this.regularCustomers, customerCode);
            findRegularCustomer(index);
        } else {
            index = ArrayHelpers.indexOfRenterCode(this.renters, customerCode);
            findRenter(index);
        }
    }

    /**
     * Searches for a regular customer in the restaurant records and displays the state of the customer.
     *
     * @param index the index of the regular customer in the restaurant records
     */
    private void findRegularCustomer(int index) {
        // The record does not exist if the index is -1
        if (index == -1) {
            System.out.println("Sorry! There is no existing customer record for that code.");
            return;
        }
        // View the state otherwise
        this.regularCustomers[index].viewState();
    }

    /**
     * Searches for a renter in the restaurant records and displays the state of the renter.
     *
     * @param index the index of the renter in the restaurant records
     */
    private void findRenter(int index) {
        // The record does not exist if the index is -1
        if (index == -1) {
            System.out.println("Sorry! There is no existing customer record for that code.");
            return;
        }
        // View the state otherwise
        this.renters[index].viewState();
    }

    /**
     * Displays the state of the restaurant and its customer records in a 3-column format.
     */
    public void viewCustomerRecord() {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println();

        // View restaurant state
        viewState();

        // Check if regular customer records exist
        if (this.regularCustomerQty != 0) {
            // Initialize columns for regular customers
            String[] columns = {"Code", "Name"};

            // Calculate column widths for regular customers
            int[] columnWidths = new int[columns.length];
            int[] maxColumnLengths = StringHelpers.calculateMaxLengths(this.regularCustomers);
            for (int i = 0; i < columns.length; i++) {
                columnWidths[i] = Math.max(columns[i].length(), maxColumnLengths[i]) + PADDING;
            }

            // Print each regular customer record
            System.out.println("┄┄┄ Regular Customers ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
            System.out.printf("%-" + columnWidths[0] + "s", "Code");
            System.out.printf("%-" + columnWidths[1] + "s", "Name");
            System.out.println("Loyalty Points");
            for (int i = 0; i < this.regularCustomerQty; i++) {
                RegularCustomer c = this.regularCustomers[i];
                System.out.printf("%-" + columnWidths[0] + "s", c.getCustomerCode());
                System.out.printf("%-" + columnWidths[1] + "s", c.getFirstName() + " " + c.getLastName());
                System.out.printf("%d", c.getLoyaltyPoints());
                System.out.println();
            }
            System.out.println();
        } else {
            System.out.println("┄┄┄ Regular Customers ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
            System.out.println("Sorry! No records to show.");
            System.out.println();
        }

        if (this.renterQty != 0) {
            // Initialize columns for renters
            String[] columns = {"Code", "Name"};

            // Calculate column widths for renters
            int[] columnWidths = new int[columns.length];
            int[] maxColumnLengths = StringHelpers.calculateMaxLengths(this.renters);
            for (int i = 0; i < columns.length; i++) {
                columnWidths[i] = Math.max(columns[i].length(), maxColumnLengths[i]) + PADDING;
            }

            // Print the renter records
            System.out.println("┄┄┄ Renters ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
            System.out.printf("%-" + columnWidths[0] + "s", "Code");
            System.out.printf("%-" + columnWidths[1] + "s", "Name");
            System.out.println("Deposit");
            for (int i = 0; i < this.renterQty; i++) {
                Renter r = this.renters[i];
                System.out.printf("%-" + columnWidths[0] + "d", r.getCustomerCode());
                System.out.printf("%-" + columnWidths[1] + "s", r.getFirstName() + " " + r.getLastName());
                System.out.printf("%.2f", r.getDeposit());
                System.out.println();
            }
            System.out.println();
        } else {
            System.out.println("┄┄┄ Renters ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
            System.out.println("Sorry! No records to show.");
            System.out.println();
        }
    }

    /**
     * Deletes a customer from the restaurant records.
     *
     * @param sc the Scanner object to use for input
     */
    public void deleteCustomer(Scanner sc) {
        System.out.println("═══ Remove Customer Record ════════════════════════");

        // If the record is empty, then there is nothing to view.
        int totalCustomers = this.regularCustomerQty + this.renterQty;
        if (totalCustomers == 0) {
            System.out.println("Sorry! There are no customer records to view.");
            return;
        }

        // Ask for a customer code
        int customerCode = -1;
        boolean customerCodeIsValid = false;
        do {
            try {
                customerCode = InputHelpers.inputInteger(sc, "Enter customer code: ", 1001, 2999);
                customerCodeIsValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!customerCodeIsValid);

        // Check if the record contains the code
        int index;
        if (customerCode >= 1000 && customerCode <= 1999) {
            index = ArrayHelpers.indexOfRegularCustomerCode(this.regularCustomers, customerCode);
            deleteRegularCustomer(sc, index, customerCode);
        } else {
            index = ArrayHelpers.indexOfRenterCode(this.renters, customerCode);
            deleteRenter(sc, index, customerCode);
        }
    }

    /**
     * Deletes a regular customer from the restaurant records.
     *
     * @param sc           the Scanner object to use for input
     * @param index        the index of the regular customer in the restaurant records
     * @param customerCode the customer code of the regular customer
     */
    private void deleteRegularCustomer(Scanner sc, int index, int customerCode) {
        // View the state if it exists
        findRegularCustomer(index);

        // Do not continue if the record does not exist
        if (index == -1) {
            return;
        }

        // Confirm the deletion
        String confirmation;
        System.out.print("Are you sure you want to delete this customer record? (Y to confirm): ");
        confirmation = sc.nextLine().trim();
        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.printf("Cancelled deletion of customer record %d.\n", customerCode);
            return;
        }

        // Iterate over the record starting from the element to delete
        // Shift elements to the left to delete the record
        for (int i = index; i < this.regularCustomerQty - 1; i++) {
            this.regularCustomers[i] = this.regularCustomers[i + 1];
        }

        // Decrement the qty for the restaurant
        this.regularCustomerQty--;

        // Decrement the qty for regular customer class
        RegularCustomer.decrementTotalCount();

        System.out.printf("Successfully deleted customer record %d.\n", customerCode);
    }

    /**
     * Deletes a renter from the restaurant records.
     *
     * @param sc           the Scanner object to use for input
     * @param index        the index of the renter in the restaurant records
     * @param customerCode the customer code of the renter
     */
    private void deleteRenter(Scanner sc, int index, int customerCode) {
        // View the state if it exists
        findRenter(index);

        // Do not continue if the record does not exist
        if (index == -1) {
            return;
        }

        // Confirm the deletion
        String confirmation;
        System.out.print("Are you sure you want to delete this customer record? (Y to confirm): ");
        confirmation = sc.nextLine().trim();
        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.printf("Cancelled deletion of customer record %d.\n", customerCode);
            return;
        }

        // Iterate over the record starting from the element to delete
        // Shift elements to the left to delete the record
        for (int i = index; i < this.renterQty - 1; i++) {
            this.renters[i] = this.renters[i + 1];
        }

        // Decrement the qty for the restaurant
        this.renterQty--;

        // Decrement the qty for renter class
        Renter.decrementTotalCount();

        System.out.printf("Successfully deleted customer record %d.\n", customerCode);
    }

    /**
     * Modifies a customer's name in the restaurant records.
     *
     * @param sc the Scanner object to use for input
     */
    public void modifyCustomer(Scanner sc) {
        System.out.println("═══ Update Customer Record ════════════════════════");

        // If the record is empty, then there is nothing to view.
        int totalCustomers = this.regularCustomerQty + this.renterQty;
        if (totalCustomers == 0) {
            System.out.println("Sorry! There are no customer records to view.");
            return;
        }

        // Ask for a customer code
        int customerCode = -1;
        boolean customerCodeIsValid = false;
        do {
            try {
                customerCode = InputHelpers.inputInteger(sc, "Enter customer code: ", 1001, 2999);
                customerCodeIsValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine();
            }
        } while (!customerCodeIsValid);

        // Check if the record contains the code
        int index;
        if (customerCode >= 1000 && customerCode <= 1999) {
            index = ArrayHelpers.indexOfRegularCustomerCode(this.regularCustomers, customerCode);
            modifyRegularCustomer(sc, index, customerCode);
        } else {
            index = ArrayHelpers.indexOfRenterCode(this.renters, customerCode);
            modifyRenter(sc, index, customerCode);
        }
    }

    /**
     * Modifies a regular customer's name in the restaurant records.
     *
     * @param sc           the Scanner object to use for input
     * @param index        the index of the regular customer in the restaurant records
     * @param customerCode the customer code of the regular customer
     */
    private void modifyRegularCustomer(Scanner sc, int index, int customerCode) {
        final String keepString = "---";

        // View the state if it exists
        findRegularCustomer(index);

        // Do not continue if the record does not exist
        if (index == -1) {
            return;
        }

        // Ask for the customer's new name
        String newLastName = InputHelpers.inputString(sc, "Enter new last name ('---' to keep):  ");
        String newFirstName = InputHelpers.inputString(sc, "Enter new first name ('---' to keep): ");

        // Get the RegularCustomer instance
        RegularCustomer c = this.regularCustomers[index];

        // Modify info if the info is new
        c.setFirstName((newFirstName.equals(keepString)) ? c.getFirstName() : newFirstName);
        c.setLastName((newLastName.equals(keepString)) ? c.getLastName() : newLastName);

        // Update the record
        this.regularCustomers[index] = c;

        System.out.printf("Successfully updated customer %d!", customerCode);
        System.out.println();
    }

    private void modifyRenter(Scanner sc, int index, int customerCode) {
        final String keepString = "---";

        // View the state if it exists
        findRenter(index);

        // Do not continue if the record does not exist
        if (index == -1) {
            return;
        }

        // Ask for the customer's new name
        String newLastName = InputHelpers.inputString(sc, "Enter new last name ('---' to keep):  ");
        String newFirstName = InputHelpers.inputString(sc, "Enter new first name ('---' to keep): ");

        // Get the RegularCustomer instance
        Renter r = this.renters[index];

        // Modify info if the info is new
        r.setFirstName((newFirstName.equals(keepString)) ? r.getFirstName() : newFirstName);
        r.setLastName((newLastName.equals(keepString)) ? r.getLastName() : newLastName);

        // Update the record
        this.renters[index] = r;

        System.out.printf("Successfully updated customer %d!", customerCode);
        System.out.println();
    }

    /**
     * Adds the given amount to the total sales of the restaurant.
     *
     * @param amount the amount to add to the total sales
     */
    public void addToSales(float amount) {
        this.totalSales += amount;
    }

    /**
     * Simulates two customer transactions.
     * <p>
     * The first customer transaction is a regular customer who orders a meal worth Php75.
     * The second customer transaction is a renter who orders a meal worth Php85.
     * The total sales of the restaurant is updated after each transaction.
     * The third customer transaction is a renter who buys with an amount worth Php4500.
     */
    public void simulateSales() {
        final float firstTransactionPayment = 75f;
        final float secondTransactionPayment = 85f;
        final float thirdTransactionPayment = 4500f;

        if (RegularCustomer.obtainTotalCount() == 0) {
            System.out.println("Sorry! Sales cannot be simulated without a regular customer record.");
            return;
        } else if (Renter.obtainTotalCount() == 0) {
            System.out.println("Sorry! Sales cannot be simulated without a renter record.");
            return;
        }
        System.out.println("═══ Simulation Start ══════════════════════════════");
        System.out.println();

        // Create a random instance
        Random random = new Random();

        // Choose a random Regular Customer
        int index = random.nextInt(0, this.regularCustomerQty);
        RegularCustomer c = this.regularCustomers[index];
        System.out.printf("┄┄┄ %s trying to buy food worth P%.2f", c.getFirstName(), firstTransactionPayment);
        System.out.println();
        c.buy(firstTransactionPayment);

        // View the state of the restaurant and records
        System.out.println();
        viewCustomerRecord();

        // Choose a random Renter
        index = random.nextInt(0, this.renterQty);
        Renter r = this.renters[index];
        System.out.printf("┄┄┄ %s trying to buy food worth P%.2f", r.getFirstName(), secondTransactionPayment);
        System.out.println();
        r.buy(secondTransactionPayment);

        // View the state of the restaurant and records
        System.out.println();
        viewCustomerRecord();

        // Choose a random Renter
        index = random.nextInt(0, this.renterQty);
        Renter r2 = this.renters[index];
        System.out.printf("┄┄┄ %s trying to buy food worth P%.2f", r2.getFirstName(), thirdTransactionPayment);
        System.out.println();
        r2.buy(thirdTransactionPayment);

        // View the state of the restaurant and records
        System.out.println();
        viewCustomerRecord();

        System.out.println("═══ Simulation End ════════════════════════════════");
    }

    /**
     * Displays the state of the restaurant.
     */
    public void viewState() {
        // Print restaurant name and sales
        System.out.println("┄┄┄ Restaurant Record ┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
        int restaurantNameLength = Math.max("Name".length(), this.restaurantName.length()) + PADDING;
        System.out.printf("%-" + restaurantNameLength + "s", "Name");
        System.out.println("Total Sales");
        System.out.printf("%-" + restaurantNameLength + "s", this.restaurantName);
        System.out.printf("%.2f", this.totalSales);
        System.out.println();
        System.out.println();
    }
}
