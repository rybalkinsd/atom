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
        int maxx = values[0];
        int len = values.length;
        for (int i = 1; i < len; ++i) {
            maxx = Math.max(maxx, values[i]);
        }
        return maxx;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        int summ = values[0];
        int len = values.length;
        for (int i = 1; i < len; ++i) {
            summ += values[i];
        }
        return summ;
    }


}
