package ru.atom;

public class Util {

	public static int max(int[] values) {
		int maxx = values[0];
        int len = values.length;
        for (int i = 1; i < len; ++i) {
            maxx = Math.max(maxx, values[i]);
        }
        return maxx;
    }
    public static long sum(int[] values) {
    	long summ = values[0];
    	int len = values.length;
    	for (int i = 1; i < len; ++i) {
    		summ = summ + values[i];
    	}
    	return summ;
    }
}
