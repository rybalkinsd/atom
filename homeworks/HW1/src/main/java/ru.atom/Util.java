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
        int maxim = values[0];
        for (int i:values) {
            if (maxim < i) {
                maxim = i;
            }
        }
        return maxim;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {

        long summ = 0;
        for (int i : values) {
            summ = summ + i;
        }
        return summ;
    }


}
