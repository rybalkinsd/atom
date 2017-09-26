package ru.atom;

import java.util.stream.IntStream;

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

        int maxValue = values[0];

        for (int value: values)
            if (maxValue < value) maxValue = value;

        return maxValue;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {

        int sumValues = 0;

        // in jshell lambda works :(
        //IntStream.range(0, values.length).forEach(i -> sumValues+=values[i]);

        for (int value: values) sumValues += value;
        return sumValues;
    }
}
