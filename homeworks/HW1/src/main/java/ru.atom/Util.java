package ru.atom;

public class Util {

    public static int max(int[] values) {
        int max = values[0];
        for (int i : values) {
            if (max < i)
                max = i;
        }
        return max;
    }

    public static long sum(int[] values) {
        long sum = 0;
        for (int i : values) {
            sum += i;
        }
        return sum;
    }
}
