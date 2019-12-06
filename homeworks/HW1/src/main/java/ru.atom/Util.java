package ru.atom;

/**
 * In this assignment you need to implement the following util methods. Note:
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
        int max = Integer.MIN_VALUE;
        for (int val : values) {
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        long sum = 0;
        for (int val : values) {
            sum += val;
        }
        return sum;
    }

}
