package ru.atom;

public class Util {

    
    public static int max(int[] values) {
        int maxInd = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > values[maxInd]) {
                maxInd = i;
            }
        }
        return values[maxInd];
    }

    
    public static long sum(int[] values) {
        long summ = 0;
        for (int i = 0; i < values.length; i++) {
            summ += values[i];
        }
        return summ;
    }


}
