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
    public static int max(int[] values) throws NullPointerException {
        int temp = 0;
        if (values.length == 0) {
            throw new NullPointerException("zero input");
        } else {
            for (int i: values) {
                if (temp < i) {
                    temp = i;
                }
            }
        }
        return temp;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) throws NullPointerException {
        long temp = 0L;
        if (values.length == 0) {
            throw new NullPointerException("zero input");
        } else {
            for (int i: values) {
                temp += i;
            }
        }
        return temp;
    }


}
