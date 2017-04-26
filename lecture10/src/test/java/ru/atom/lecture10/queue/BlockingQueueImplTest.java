package ru.atom.lecture10.queue;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class BlockingQueueImplTest {
    private BlockingQueue<Integer> queue = new BlockingQueueImpl<>(4);
    private ExecutorService service = Executors.newFixedThreadPool(10);


    @Test
    public void putWait() throws Exception {
        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                try {
                    queue.put(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.currentThread().sleep(2000L);
        for (int i = 0; i < 20; i++) {
            service.submit(() -> {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.currentThread().sleep(2000L);
        
    }

}