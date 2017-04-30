package ru.atom.lecture10;

import org.junit.Test;
import ru.atom.lecture10.queue.MyQueue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

class MyQueueTest {
    @Test
    public void testQueue() throws Exception {
        AtomicLong val = new AtomicLong();
        final MyQueue<Integer> queue = new MyQueue<>(100);
        ArrayList<Thread> threads = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            final int ThreadId = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 10000; i++) {
                            queue.put(ThreadId * 10000 + i);
                            val.addAndGet(queue.take());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(val.get(), 4999950000L);
    }
}
