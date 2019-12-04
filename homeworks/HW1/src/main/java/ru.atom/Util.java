package ru.atom;

import java.util.Arrays;
import java.util.Collections;

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
        if (values.length > 0) {
            int max = values[0];
            for (int i = 0; i < values.length; ++i) {
                if (max < values[i]) {
                    max = values[i];
                }
            }
            return max;
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        if (values.length > 0) {
            long sum = 0;
            for (int a : values) {
                sum += a;
            }
            return sum;
        }
        throw new UnsupportedOperationException();
    }


}
