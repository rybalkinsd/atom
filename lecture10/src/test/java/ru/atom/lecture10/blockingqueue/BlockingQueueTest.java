package ru.atom.lecture10.blockingqueue;

import org.junit.Assert;
import org.junit.Test;
import ru.atom.lecture10.queue.BlockingQueue;
import ru.atom.lecture10.queue.BlockingQueueImpl;

/**
 * Created by ilysk on 26.04.17.
 */
public class BlockingQueueTest {

    @Test
    public void blockingQueueTestAndSlowPut() {
        BlockingQueue<String> blockingQueue = new BlockingQueueImpl(4);

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    try {
                        blockingQueue.put(new String("I am number " + i));
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                for (int j = 0; j <= 25; j++) {
                    try {
                        blockingQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        // Because of "slow" put():
        Assert.assertTrue(blockingQueue.size() == 1);
    }

    @Test
    public void blockingQueueTestAndSlowTake() {
        BlockingQueue<String> blockingQueue = new BlockingQueueImpl(4);

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    try {
                        blockingQueue.put(new String("I am number " + i));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                for (int j = 0; j <= 25; j++) {
                    try {
                        blockingQueue.take();
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        // Because of "slow" take():
        Assert.assertTrue(blockingQueue.size() == 4);
    }
}
