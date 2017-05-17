package ru.atom;

import org.junit.Test;

/**
 * Created by BBPax on 17.05.17.
 */
public class TreadTest {
    @Test
    public void insideThreadTest() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while(!Thread.currentThread().isInterrupted()) {
                    System.out.println(i);
                    if (i >= 30) {
                        Thread.currentThread().interrupt();
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    i++;
                }
            }
        });

        t1.start();
    }

}
