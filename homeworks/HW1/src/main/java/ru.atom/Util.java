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
        
        int N = values.length;
        if (N == 0) {
            throw new UnsupportedOperationException();            
        }
        int MaxValue = values[0];
        for (int i = 0; i < N; i++) {
            if (values[i] > MaxValue) {
                MaxValue = values[i];
            }
        }
        return MaxValue;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {

        int N = values.length;
        if (N == 0) {
            throw new UnsupportedOperationException();            
        }
        long sum = 0;
        for (int i = 0; i < N; i++) {
            sum += values[i];
        }
        return sum;
    }


}
