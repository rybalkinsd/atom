package ru.atom.lecture10;

import org.junit.Assert;
import org.junit.Test;
import ru.atom.lecture10.queue.Message;
import ru.atom.lecture10.queue.RealisationBlockingQueue;

/**
 * Created by konstantin on 01.05.17.
 */
public class RealisationBlockingQueueTest {
    RealisationBlockingQueue<Message> blockingQueue = new RealisationBlockingQueue<>(2);

    @Test
    public void sizeTest() throws InterruptedException {
        blockingQueue.put(new Message());
        blockingQueue.put(new Message());

        Assert.assertTrue(blockingQueue.size() == 2);
    }

    @Test
    public void putTest() throws InterruptedException {
        Thread producer = new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    blockingQueue.put(new Message());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    blockingQueue.take();
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
        Thread.currentThread().sleep(2_000);
    }
}
