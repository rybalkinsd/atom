package ru.atom;
public class Util {


    public static int max(int[] values) {
        int maximum = values[0];
        for (int i = 1; i < values.length; i++) {
            if (maximum < values[i]) maximum = values[i];
        }
        return maximum;
    }

    public static long sum(int[] values) {
        long summa = 0;
        for (int i : values) {
            summa += i;
        }
        return summa;
    }


}
