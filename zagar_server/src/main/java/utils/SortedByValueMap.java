package utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by user on 08.11.16.
 * <p>
 * Helps sort map by value
 */
public class SortedByValueMap {
    private SortedByValueMap() {
    }

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Map<K, V> sortedByValues = new TreeMap<>((K k1, K k2) -> {
            int compare = map.get(k2).compareTo(map.get(k1));
            if (compare == 0) return 1;
            else return compare;
        });
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
