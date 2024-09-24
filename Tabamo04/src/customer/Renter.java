package customer;

import restaurant.Restaurant;

public class Renter extends Customer {

    // Static Attributes
    private static final float minimumDepositAllowed = -1000f;

    // Attributes
    private float deposit;

    // Constructor
    public Renter(String firstName, String lastName, Restaurant restaurant, float deposit) {
        super(firstName, lastName, restaurant);
        this.deposit = deposit;
        this.customerType = Customer.RENTER;
    }

    // Getters
    public float getDeposit() {
        return this.deposit;
    }

    // Methods
    public static void decrementTotalCount() {
        Customer.decrementTotalCount();
        RENTER_TOTAL_REMOVED++;
    }

    /**
     * Obtains the total number of existing {@code Renter} instances.
     *
     * @return the total number of existing {@code Renter} instances
     */
    public static int obtainTotalCount() {
        return RENTER_TOTAL_COUNT - RENTER_TOTAL_REMOVED;
    }

    /**
     * Assigns an autogenerated ID depending on the currently existing {@code Renter} instances.
     *
     * @return the ID for a regular customer
     */
    @Override
    protected int assignId() {
        ++RENTER_TOTAL_COUNT;
        return ++TOTAL_COUNT;
    }

    /**
     * Assigns an autogenerated code depending on the currently existing {@code Renter} instances.
     *
     * @return the customer code for a regular customer
     */
    protected int assignCode() {
        return RENTER_TOTAL_COUNT + Customer.RENTER_CODE_SERIES;
    }

    /**
     * Prints the basic information of a {@code Renter} to the terminal.
     */
    public void viewState() {
        viewBasicInformation();
        System.out.println("Balance:        " + String.format("%.2f", this.deposit));
    }

    /**
     * Allows a {@code Renter} instance to buy from a restaurant, increasing a restaurant's sales.
     *
     * @param amount the amount of money added to the {@code Restaurant} instance's total sales
     */
    public void buy(float amount) {
        if (this.deposit - amount < minimumDepositAllowed) {
            System.out.println("Sorry! The transaction failed because the deposit would be below the minimum allowed.");
        } else {
            pay(amount);
            this.deposit -= amount;
            System.out.printf("Success! %s %s has paid %.2f!", this.firstName, this.lastName, amount);
            System.out.println();
        }
    }
}
