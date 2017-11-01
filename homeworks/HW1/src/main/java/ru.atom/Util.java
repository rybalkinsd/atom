
package ru.atom;

public class Util {


    public static int max(int[] values) {
        int maximus = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > values[maximus]) {
                maximus = i;
            }
        }
        return values[maximus];
    }


    public static long sum(int[] values) {
        long summa = 0;
        for (int i = 0; i < values.length; i++) {
            summa += values[i];
        }
        return summa;
    }


}