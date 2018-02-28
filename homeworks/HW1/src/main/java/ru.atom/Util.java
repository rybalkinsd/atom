package ru.atom;

public class Util {

    public static int max(int[] values) {
        int param = values[0];
        for (int i : values) {
            if (i > param) {
                param = i;
            }
        }
        return param;
    }

    public static long sum(int[] values) {
        long param = 0;
        for (int i : values) {
            param += i;
        }
        return param;
    }
}