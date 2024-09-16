package restaurant;

/**
 * Represents a customer of a restaurant.
 * A {@code Customer} has a name, a customer code, and loyalty points.
 * <p>
 * Note: This can be converted to a record class because it is a data class (a class that only holds data).
 */
public class Customer {
    // Static Constants
    private final static int STRING_PADDING = 4;

    // Attributes
    private final String firstName;
    private final String lastName;
    private final int customerCode;
    private final int loyaltyPoints;

    /**
     * Creates a new instance of a {@code Customer} with a first name, last name, and customer code.
     *
     * @param firstName     the first name of the customer
     * @param lastName      the last name of the customer
     * @param customerCode  the customer code of the customer for record purposes
     * @param loyaltyPoints the number of loyalty points of the customer
     */
    public Customer(String firstName, String lastName, int customerCode, int loyaltyPoints) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerCode = customerCode;
        this.loyaltyPoints = loyaltyPoints;
    }

    // Getters

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getLoyaltyPoints() {
        return this.loyaltyPoints;
    }

    public int getCustomerCode() {
        return this.customerCode;
    }

    // Methods

    /**
     * Prints the current state of the {@code Customer} to the terminal.
     * <p> The attributes printed are the customer code, first and last name, and loyalty points.
     */
    public void viewState() {
        System.out.println();
        System.out.println("- Customer ---------");
        System.out.println("Customer Code:  " + this.customerCode);
        System.out.println("First Name:     " + this.firstName);
        System.out.println("Last Name:      " + this.lastName);
        System.out.println("Loyalty Points: " + this.loyaltyPoints);
    }

    /**
     * Prints the current record of the {@code Customer} to the terminal.
     * <p> This prints the customer code, first and last name, and loyalty points in 3 columns.
     */
    public void viewRecord() {
        System.out.println();
        // Initialize the initial length for the name column
        int initNameLength = "Name".length() + STRING_PADDING;

        // Get the length of the current customer's name
        int nameLength = this.firstName.length() + this.lastName.length() + STRING_PADDING;

        // Calculate the maximum length needed for printing the columns
        int customerCodeLength = "Customer Code".length() + STRING_PADDING;
        nameLength = Math.max(nameLength, initNameLength);
        int loyaltyPointsLength = "Loyalty Points".length() + STRING_PADDING;

        // Print the header
        System.out.printf("%-" + customerCodeLength + "s", "Customer Code");
        System.out.printf("%-" + nameLength + "s", "Name");
        System.out.printf("%-" + loyaltyPointsLength + "s\n", "Loyalty Points");

        // Print the customer record
        System.out.printf("%-" + customerCodeLength + "d", this.customerCode);
        System.out.printf("%-" + nameLength + "s", this.firstName + " " + this.lastName);
        System.out.printf("%-" + loyaltyPointsLength + "d\n", this.loyaltyPoints);

    }
}
