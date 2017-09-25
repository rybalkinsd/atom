package ru.atom;

/**
 * In this assignment you need to implement the following util methods.
 * Note:
 *  throw new UnsupportedOperationException(); - is just a stub
 */
public class Util {

    /*
     *
     * Returns the greatest of {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the largest of values.
     */
    public static int max(int[] values) throws IllegalArgumentException {

        if (values.length == 0) {
            throw new IllegalArgumentException();
        }

        int maxVal = values[0];

        for (int i : values) {
            if (i > maxVal) {
                maxVal = i;
            }
        }
        return maxVal;
    }

    /*
     *
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        long sumVal = 0;
        
        for (int i : values) {
            sumVal += i;
        }

        return sumVal;
    }
}
