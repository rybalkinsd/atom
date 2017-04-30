package ru.atom.lecture10.queue;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ксения on 26.04.2017.
 */
public class BlQueueTest {
    BlQueue<Message> blQueue = new BlQueue<>(4);

    @Before
    public void setUp() {
        for (int i = 0; i < 5; ++i) {
            Thread thread = new PutThread(blQueue);
            thread.start();
        }
        for (int i = 0; i < 3; ++i) {
            Thread thread = new TakeThread(blQueue);
            thread.start();
        }

    }

    @Test
    public void getAllTest() throws InterruptedException {
        Thread.currentThread().sleep(2_000);
        assertTrue(blQueue.size() == 2);
    }
}