package ru.atom;

import java.lang.Math;

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
        int max = values[0];
        for (int i = 1;i < values.length;++i) {
            max = Math.max(max, values[i]);
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
        long sumOfArr = 0;
        for (int i = 0; i < values.length; ++i) {
            sumOfArr += values[i];
        }
        return sumOfArr;
    }


}
