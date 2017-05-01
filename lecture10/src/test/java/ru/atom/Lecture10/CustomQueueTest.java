package ru.atom.Lecture10;

import org.junit.Before;
import org.junit.Test;
import ru.atom.lecture10.queue.Message;
import ru.atom.lecture10.queue.MessageBlockingQueue;

import static org.junit.Assert.assertTrue;

/**
 * Created by ikozin on 26.04.17.
 */
public class CustomQueueTest {

    MessageBlockingQueue mbq;

    @Before
    public void init() {
        mbq = new MessageBlockingQueue(4);
    }

    @Test
    public void putTest() throws Exception {
        Thread[] threads = new Thread[2];
        for (int i = 0;i < 2;i++) {
            threads[i] = new Thread(() -> {
                try {
                    mbq.put(new Message());
                    mbq.put(new Message());
                    mbq.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threads[0].start();
        threads[1].start();
        threads[0].join();
        threads[1].join();
        assertTrue(mbq.remainingCapacity() == 2);
    }

    @Test
    public void takeTest() throws Exception {
        int initiallyRemaining = mbq.remainingCapacity();
        for (int i = 0;i < initiallyRemaining;i++) {
            mbq.put(new Message());
        }
        assertTrue(mbq.remainingCapacity() == 0);
        Thread[] threads = new Thread[2];
        for (int i = 0;i < 2;i++) {
            threads[i] = new Thread(() -> {
                try {
                    mbq.take();
                    mbq.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threads[0].start();
        threads[1].start();
        threads[0].join();
        threads[1].join();
        assertTrue(mbq.size() == 0);
    }
}
