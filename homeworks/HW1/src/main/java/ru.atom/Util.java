package ru.atom;

/**
 * In this assignment you need to implement the following util methods.
 * Note:
 *  throw new UnsupportedOperationException(); - is just a stub
 */
public class Util {



    /**
     * Returns the greatest of {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the largest of values.
     */
    public static int max(int[] values) {
        int max_value = 0 ;

        for (int i = 0; i <= values.length; i++) {
            if (max_value < values[i]) {
                max_value = values[i];
            }
        }

        return max_value;
    }


    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        int sum_values = 0;
        for (int i = 0; i <= values.length; i++) {
            sum_values = sum_values + values[i];
        }
        return sum_values;
    }


}
