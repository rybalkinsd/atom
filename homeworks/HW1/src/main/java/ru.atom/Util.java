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
        int maximum = values[0];
        for (int var: values) {
            if (var > maximum) {
                maximum = var;
            }
        }
        return maximum;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) { //?!?
        int sum = 0;
        for (int var: values) {
            sum = sum +  var;
        }
        return sum;
    }


}
