package ru.atom;

public class Util { 
    public static int max(int[] values) {
        int maxim = values[0];
        for (int i = 0; i < values.length ; i = i + 1) {
            if (values[i] > maxim) maxim = values[i];
        }
        return maxim;
        // throw new UnsupportedOperationException();
    }

    public static long sum(int[] values) {
        long summa = 0;
        for (int i = 0; i < values.length ; i = i + 1) {
            summa = summa + values[i];
        }
        return summa;
        //throw new UnsupportedOperationException();
    }
}