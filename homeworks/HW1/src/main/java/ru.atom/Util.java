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
        int maxNum = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > maxNum) {
                maxNum = values[i];
            }
        }
        return maxNum;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        long sumArr = 0L;
        for (int i = 0; i < values.length; i++) {
            sumArr += values[i];
        }
        return sumArr;
    }


}