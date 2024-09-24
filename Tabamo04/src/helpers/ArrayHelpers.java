package helpers;

import customer.RegularCustomer;
import customer.Renter;

/**
 * A class that contains helper methods for arrays.
 */
public class ArrayHelpers {

    /**
     * Obtains the index of the target integer in the array.
     *
     * @param arr    the array to search
     * @param target the target integer to find
     * @return the index of the target integer in the array, or -1 if not found
     */
    public static int indexOfInt(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Obtains the index of the target {@code RegularCustomer} code in the array.
     *
     * @param arr  the array to search
     * @param code the target code to find
     * @return the index of the target code in the array, or -1 if not found
     */
    public static int indexOfRegularCustomerCode(RegularCustomer[] arr, int code) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                if (arr[i].getCustomerCode() == code) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Obtains the index of the target {@code Renter} code in the array.
     *
     * @param arr  the array to search
     * @param code the target code to find
     * @return the index of the target code in the array, or -1 if not found
     */
    public static int indexOfRenterCode(Renter[] arr, int code) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                if (arr[i].getCustomerCode() == code) {
                    return i;
                }
            }
        }
        return -1;
    }
}
