package ru.atom.lecture12;

import java.util.ArrayList;
import java.util.List;

public class GarbageProducer {
    static List<Object> l;
    
    public static void main(String... args) throws InterruptedException {
        // Thread.sleep(30_000);

        l = new ArrayList<>();
        for (int c = 0; c < 100_000_000; c++) {
            l.add(new Object());
        }
    }
}