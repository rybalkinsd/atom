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

        if(values.length > 0){
            int maxval = values[0];
            for(int i: values) {
                if(i>maxval)
                    maxval=i;
            }
            return maxval;
        }
        else
            throw new UnsupportedOperationException();
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        if(values.length>0){
            long res = 0;
            for(int i: values) {
                res+=i;
            }
            return res;
        }
        else
            throw new UnsupportedOperationException();
    }


}
