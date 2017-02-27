package ru.atom;

public class Util {



    /**
     * Returns the greatest of {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the largest of values.
     */
    public static int max(int[] values) {
        int mx = values[0];
        for (int i: values) {
            if (i > mx) {
                mx = i;
            }
        }
        return mx;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        long sm = 0L;
        for (int i: values) {
            sm += i;
        }
        return sm;
    }


}
