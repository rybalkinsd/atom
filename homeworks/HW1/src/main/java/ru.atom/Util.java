package ru.atom;

/**
 * In this assignment you need to implement the following util methods.
 * Note:
 * throw new UnsupportedOperationException(); - is just a stub
 */
public class Util {


    /**
     * Returns the greatest of {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the largest of values.
     */
    public static int max(int[] values) {
        if (values.length <= 0) {
            throw new IllegalArgumentException(); // Task condition? Throw an exception.
        }
        int maxVal = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > maxVal) {
                maxVal = values[i];
            }
        }
        return maxVal;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        //throw new UnsupportedOperationException();
        if (values.length <= 0) {
            throw new IllegalArgumentException(); // Task condition? Throw an exception.
        }
        long sum = 0L;
        for (int i = 0; i < values.length; i++) {
            sum = sum + values[i];
        }
        return sum;
    }


}