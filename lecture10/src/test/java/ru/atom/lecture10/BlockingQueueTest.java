package ru.atom.lecture10;


import org.junit.Assert;
import org.junit.Test;
import ru.atom.lecture10.queue.Message;
import ru.atom.lecture10.queue.MyBlockingQueue;

/**
 * Created by pavel on 26.04.17.
 */
public class BlockingQueueTest {
    MyBlockingQueue<Message> myBlockingQueue = new MyBlockingQueue<>(2);

    @Test
    public void sizeTest() throws InterruptedException {
        myBlockingQueue.put(new Message());
        myBlockingQueue.put(new Message());

        Assert.assertTrue(myBlockingQueue.size() == 2);
    }

    @Test
    public void putTest() throws InterruptedException {
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    myBlockingQueue.put(new Message());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    myBlockingQueue.take();
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
