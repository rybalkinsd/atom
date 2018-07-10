package ru.atom;

import javax.swing.text.LabelView;
import java.util.ArrayList;
import java.util.List;

/**
 * In this assignment you need to implement the following util methods.
 * Note:
 * throw new UnsupportedOperationException(); - is just a stub
 */
public class Util {


    /**
     * Returns the greatest of {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the largest of values.
     */
    public static int max(int[] values) {
        int result = 0;
        if (values.length > 0) {
            result = values[0];
            for (int i = 1; i < values.length; i++) {
                if (result < values[i]) {
                    result = values[i];
                }
            }
        }
        return result;
    }

    /**
     * Returns the sum of all {@code int} values.
     *
     * @param values an argument. Assume values.length > 0.
     * @return the sum of all values.
     */
    public static long sum(int[] values) {
        int result = 0;

        for (int i: values) {
            result += i;
        }

        return result;
    }


}
