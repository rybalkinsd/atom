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
        int max = values[0];
        int length = values.length;
        for (int i = 1; i < length; i++)
            if (max < values[i])
                max = values[i];
        return max;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        long sum = values[0];
        int length = values.length;
        for (int i = 1; i < length; i++)
            sum += values[i];
        return sum;
    }


}
