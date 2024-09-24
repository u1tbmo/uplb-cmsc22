package helpers;

import customer.Customer;

/**
 * A class that contains helper methods for strings.
 */
public class StringHelpers {
    public static final int PADDING = 2;

    /**
     * Obtains the longest length of each attribute in the array of Customers.
     *
     * @param arr the array of Customers
     * @return the array with the max lengths of each attribute
     */
    public static int[] calculateMaxLengths(Customer[] arr) {
        // Get the max length of the customer code
        int codeMaxLength = 0;
        for (Customer c : arr) {
            if (c != null) {
                int length = String.valueOf(c.getCustomerCode()).length();
                if (length > codeMaxLength) {
                    codeMaxLength = length;
                }
            }
        }
        // Get the max length of the name
        int nameMaxLength = 0;
        for (Customer c : arr) {
            if (c != null) {
                int length = c.getFirstName().length() + c.getLastName().length() + 1;
                if (length > nameMaxLength) {
                    nameMaxLength = length;
                }
            }
        }
        return new int[]{codeMaxLength, nameMaxLength};
    }
}
