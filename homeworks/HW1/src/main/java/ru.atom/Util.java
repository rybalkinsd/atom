package ru.atom;

public class Util {

    /**
      * Returns the greatest of {@code int} values.
      *
      * @param values an argument. Assume values.length > 0.
      * @return the largest of values.
      */

    public static int max(int[] values) {
        for (int i = values.length - 1 ; i > 0 ; i--) {
            for (int j = 0 ; j < i ; j ++) {

                if (values[j] > values[j + 1]) {
                    int tmp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = tmp;
                }
            }
        }
        return (values[0]);
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