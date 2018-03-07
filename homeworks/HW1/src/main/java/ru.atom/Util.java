package ru.atom;

public class Util {
    public static int max(int[] values) {
        int mx = 0;
        for (int i : values) {
            if (i > mx) {
                mx = i;
            }
        }
        return mx;
    }

    public static long sum(int[] values) {
        long sm = 0;
        for (int i : values) {
            sm += i;
        }
        return sm;
    }
}
