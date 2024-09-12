package restaurant;

/**
 * Represents a customer of a restaurant.
 * A {@code Customer} has a name, a customer code, and loyalty points.
 */
public class Customer {
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
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public int getCustomerCode() {
        return customerCode;
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
}
