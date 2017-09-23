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
        int mx = values[0];
        for (int index = 1; index < values.length; ++index) {
            if (mx < values[index]) {
                mx = values[index];
            }
        }
        return mx;
        /*
        хотел бы так я написать
        на джаве код в одну строку
        чтобы студентам показать
        смотрите все, как я могу
         */
        //return Arrays.stream(values).max().getAsInt();
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        long sm = 0;
        for (int value : values) {
            sm += value;
        }
        return sm;
    }


}
