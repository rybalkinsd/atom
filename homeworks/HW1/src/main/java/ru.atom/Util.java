package ru.atom;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.concurrent.atomic.LongAdder;

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
        OptionalInt max = Arrays.stream(values).max();
        if (max.isPresent()) {
            return max.getAsInt();
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
        LongAdder longAdder = new LongAdder();
        Arrays.stream(values).forEach(longAdder::add);
        return longAdder.longValue();
    }


}
