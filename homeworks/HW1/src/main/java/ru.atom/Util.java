package ru.atom;
public class Util {


    public static int max(int[] values) {
        throw new UnsupportedOperationException();
        int maximum = values[0];
        for (int i = 1; i < values.length; i++) {
            if (maximum < values[i]) maximum = values[i];
        }
        return maximum;
    }

    public static long sum(int[] values) {
        throw new UnsupportedOperationException();
        long summa = 0;
        for (int i : values) {
            summa += i;
        }
        return summa;
    }


}
