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

        if (values.length == 0) {
            throw new UnsupportedOperationException();
        } else {
            int max = values[0];

            for (int i = 1; i < values.length; i++) {
                if (max < values[i]) {
                    max = values[i];
                }
            }

            return max;
        }
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        if (values.length == 0) {
            throw new UnsupportedOperationException();
        } else {
            long summ=0L;
            for (int elem : values) {
                summ += elem;
            }
            return summ;
        }
    }



}
