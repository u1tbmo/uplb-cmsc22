package restaurant;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a restaurant that manages customer records.
 * <p> A {@code Restaurant} can add, search, view, and delete {@code Customer} records.
 */
public class Restaurant {
    // Static Constants

    public final static int MAX_CUSTOMERS = 50;
    private final static int STRING_PADDING = 2;

    // Attributes
    private final String restaurantName;
    private final Customer[] customerRecords; // Note: the array's reference is final, not its content
    private int customerQty;

    /**
     * Creates a new instance of a {@code Restaurant} with a restaurant name, customer record, and customer quantity.
     *
     * @param restaurantName the name of the {@code Restaurant}
     */
    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
        this.customerRecords = new Customer[MAX_CUSTOMERS];
        this.customerQty = 0;
    }

    // Getter

    /**
     * @return the name of the {@code Restaurant}
     */
    public String getRestaurantName() {
        return restaurantName;
    }

    // Methods

    /**
     * Computes for the correct amount of space needed to print the name column of a record
     *
     * @return the length of space needed for the name column
     */
    private int getMaxNameLengths() {
        // Find the maximum name length
        int max = 0;
        for (int i = 0; i < this.customerQty; i++) {
            int nameCurrent = this.customerRecords[i].getFirstName().length() + this.customerRecords[i].getLastName().length() + 1;
            if (nameCurrent > max) {
                max = nameCurrent;
            }
        }

        // Return the result
        return max + STRING_PADDING;
    }

    /**
     * Checks whether the restaurant's record contains a {@code Customer}
     *
     * @param customerCode the customer code to check for
     * @return the index of the existing customer in the record, otherwise -1
     */
    private int contains(int customerCode) {
        for (int i = 0; i < this.customerQty; i++) {
            if (this.customerRecords[i].getCustomerCode() == customerCode) {
                return i;
            }
        }
        return -1;
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
     * Prints the menu for managing customer records and asks the user for their choice.
     *
     * @param sc the scanner to read from
     * @return the choice of the user
     */
    public int manageCustomerRecords(Scanner sc) {
        int choice = -1;

        // Print the menu
        boolean choiceIsValid = false;
        while (!choiceIsValid) {
            System.out.println();
            System.out.println("- Customer Records Management ----------");
            System.out.println("1 | Add customer");
            System.out.println("2 | Search for customer");
            System.out.println("3 | View customer records");
            System.out.println("4 | Delete customer");
            System.out.println("0 | Exit");
            System.out.print("Choice: ");

            // Ask for an integer until it is valid
            try {
                choice = sc.nextInt();
                choiceIsValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid choice.");
            } finally {
                // Clear the buffer
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
        }

        // Return the choice
        System.out.println();
        return choice;
    }


    /**
     * Adds a {@code Customer} to the record.
     *
     * @param sc the scanner to read from
     */
    public void addCustomer(Scanner sc) {
        System.out.println("- Create Customer Record ---------------");

        // If the restaurant is full, then the restaurant cannot add another customer
        if (this.customerQty == MAX_CUSTOMERS) {
            System.out.println("Sorry! The restaurant's customer record limit has been reached.");
            return;
        }

        // Ask for a customer code
        int customerCode = -1;
        boolean customerCodeIsValid = false;
        do {
            System.out.print("Enter a new customer code: ");
            try {
                customerCode = sc.nextInt();
                if (customerCode < 0) {
                    System.out.println("Please enter a valid positive integer code.");
                    continue;
                } else if (this.contains(customerCode) != -1) {
                    System.out.println("That customer code already exists in the record. Please enter a new customer code.");
                    continue;
                }
                customerCodeIsValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid positive integer code.");
            } finally {
                // Clear the buffer
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
        } while (!customerCodeIsValid);

        // Ask for a first name
        String firstName;
        boolean firstNameIsValid = false;
        do {
            System.out.print("Enter first name: ");
            firstName = sc.nextLine().trim();
            if (firstName.isEmpty()) {
                System.out.println("Please enter a valid nonempty string name.");
            } else {
                firstNameIsValid = true;
            }
        } while (!firstNameIsValid);

        // Ask for a last name
        String lastName;
        boolean lastNameIsValid = false;
        do {
            System.out.print("Enter last name: ");
            lastName = sc.nextLine().trim();
            if (lastName.isEmpty()) {
                System.out.println("Please enter a valid nonempty string name.");
            } else {
                lastNameIsValid = true;
            }
        } while (!lastNameIsValid);

        // Ask for loyalty points
        int loyaltyPoints = 0;
        boolean loyaltyPointsIsValid = false;
        do {
            System.out.print("Enter loyalty points: ");
            try {
                loyaltyPoints = sc.nextInt();
                if (loyaltyPoints < 0) {
                    System.out.println("Please enter a valid positive integer.");
                }
                loyaltyPointsIsValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid positive integer.");
            } finally {
                // Clear the buffer
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
        } while (!loyaltyPointsIsValid);

        // Create a new Customer, add to the array, then increment the quantity
        Customer newCustomer = new Customer(firstName, lastName, customerCode, loyaltyPoints);
        this.customerRecords[this.customerQty++] = newCustomer;

        // Print success message and state
        System.out.printf("Customer %d was added to the record!\n", customerCode);
        newCustomer.viewState();

    }

    /**
     * Searches for a {@code Customer} in the restaurant's records using the customer code
     * and views its state if it exists.
     *
     * @param sc the scanner to read from
     */
    public void findCustomer(Scanner sc) {
        System.out.println("- Search Customer Record ---------------");

        // If the record is empty, then there is nothing to search.
        if (this.customerQty == 0) {
            System.out.println("Sorry! There are no customer records to search.");
            return;
        }

        // Ask for a customer code
        int customerCode = -1;
        boolean customerCodeIsValid = false;
        do {
            System.out.print("Enter customer code: ");
            try {
                customerCode = sc.nextInt();
                customerCodeIsValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid positive integer code.");
            } finally {
                // Clear the buffer
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
        } while (!customerCodeIsValid);

        // Search the record for the customer code and print its state if it exists
        for (int i = 0; i < customerQty; i++) {
            if (this.customerRecords[i].getCustomerCode() == customerCode) {
                customerRecords[i].viewRecord();
                return;
            }
        }
        System.out.println("Sorry! There is no existing customer record for that code.");
    }

    /**
     * Prints a 3-column view of the restaurant's customer record
     */
    public void viewCustomerRecord() {
        System.out.println("- Customer Record ----------------------");
        // If the record is empty, then there is nothing to print.
        if (this.customerQty == 0) {
            System.out.println("Sorry! There are no customer records to view.");
            return;
        }

        // Initialize the initial length for the columns
        int initCustomerCodeLength = "Customer Code".length() + STRING_PADDING;
        int initNameLength = "Name".length() + STRING_PADDING;
        int initLoyaltyPointsLength = "Loyalty Points".length() + STRING_PADDING;

        // Calculate the maximum name length needed for printing the name column
        int nameLength = Math.max(getMaxNameLengths(), initNameLength);

        // Print the header
        System.out.printf("%-" + initCustomerCodeLength + "s", "Customer Code");
        System.out.printf("%-" + nameLength + "s", "Name");
        System.out.printf("%-" + initLoyaltyPointsLength + "s\n", "Loyalty Points");

        // Print each customer record
        for (int i = 0; i < this.customerQty; i++) {
            if (this.customerRecords[i] != null) {
                System.out.printf("%-" + initCustomerCodeLength + "d", this.customerRecords[i].getCustomerCode());
                System.out.printf("%-" + nameLength + "s", this.customerRecords[i].getFirstName() + " " + this.customerRecords[i].getLastName());
                System.out.printf("%-" + initLoyaltyPointsLength + "d\n", this.customerRecords[i].getLoyaltyPoints());
            }
        }
    }

    /**
     * Deletes a {@code Customer} from the record
     *
     * @param sc the scanner to read from
     */
    public void deleteCustomer(Scanner sc) {
        System.out.println("- Delete Customer Record ---------------");

        // If the restaurant is empty, then there is no customer record to remove
        if (this.customerQty == 0) {
            System.out.println("Sorry! There are no customer records to delete.");
            return;
        }

        // Ask for a customer code
        int customerCode = -1;
        boolean customerCodeIsValid = false;
        do {
            System.out.print("Enter customer code: ");
            try {
                customerCode = sc.nextInt();
                customerCodeIsValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid positive integer code.");
            } finally {
                // Clear the buffer
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }
        } while (!customerCodeIsValid);

        // Check if the record contains the customer with the customer code
        int index = this.contains(customerCode);
        if (index == -1) {
            System.out.println("Sorry! There is no existing customer record for that code.");
            return;
        }

        // Print the state of the Customer
        this.customerRecords[index].viewState();

        // Confirm the deletion
        String confirmation;
        System.out.print("Are you sure you want to delete this customer record? (Y to confirm): ");
        confirmation = sc.nextLine().trim();
        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.printf("Cancelled deletion of customer record %d.\n", customerCode);
            return;
        }

        System.out.printf("Deleted customer record %d.\n", customerCode);
        // Iterate over the record starting from the element to delete
        // Shift elements to the left to delete the record
        for (int i = index; i < customerQty - 1; i++) {
            this.customerRecords[i] = this.customerRecords[i + 1];
        }

        // Decrement the qty
        customerQty--;
    }
}
