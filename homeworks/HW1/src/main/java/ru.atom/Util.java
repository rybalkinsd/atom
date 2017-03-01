package ru.atom;

public class Util {

    /**
      * Returns the greatest of {@code int} values.
      *
      * @param values an argument. Assume values.length > 0.
      * @return the largest of values.
      */

    public static int max(int[] values) {
        int max = values[0];
        for (int i = 0; i < values.length; i ++) {
            if (values[i] > max) {
                max = values[i];
            }
        }
        return max;
    }
    

    /**
      * Returns the sum of all {@code int} values.
      *
      * @param values an argument. Assume values.length > 0.
      * @return the sum of all values.
      */
   
    public static long sum(int[] values) {
        //throw new UnsupportedOperationException();

        int result = 0;

        for (int i = 0; i < values.length; i ++) {
            result = result + values[i];
        }

        return (result);
    }

}