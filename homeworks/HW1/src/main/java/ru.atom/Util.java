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
        if (values == null || values.length == 0) throw new UnsupportedOperationException();
        else {
            int maxVal = values[0];
            for (int v:values) {
                if (v > maxVal) maxVal = v;
            }
            return  maxVal;
        }

    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        if (values == null || values.length == 0) throw new UnsupportedOperationException();
        else {
            long sumOfValues = 0;
            for (int v:values) {
                sumOfValues += v;
            }
            return  sumOfValues;
        }
    }


}
