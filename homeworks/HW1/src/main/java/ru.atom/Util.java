package ru.atom;

public class Util {

    public static int max(int[] values) {
        int max = values[0];
        for (int i = 1; i < values.length; i++)
            if (values[i] > max) 
                max = values[i];
        return max;
    }

    public static long sum(int[] values) {
        int sum = 0;
        for (int i = 0 ; i < values.length; i++)
            sum += values[i];
        return sum;
    }
}
