package ru.atom;

public class Util {

    /**
     * Returns the greatest of {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the largest of values.
     */
    public static int max(int[] values) {
        int maxValue = Integer.MIN_VALUE;

        for (int i = 0; i < values.length; ++i) {
            maxValue = Math.max(maxValue, values[i]);
        }

        return maxValue;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        long sumOfElements = 0L;

        for (int i = 0; i < values.length; ++i) {
            sumOfElements += values[i];
        }

        return sumOfElements;
    }


}
