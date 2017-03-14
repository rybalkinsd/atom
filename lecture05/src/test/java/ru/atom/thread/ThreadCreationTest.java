package ru.atom.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ru.atom.thread.instantiation.CounterExtendsThread;
import ru.atom.thread.instantiation.CounterImplementsRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 3/14/17.
 */
public class ThreadCreationTest {
    private static final Logger log = LogManager.getLogger(ThreadCreationTest.class);

    @Test
    public void singleInstance() throws Exception {
        Thread ext = new CounterExtendsThread();
        Thread run = new Thread(new CounterImplementsRunnable());
        log.info("All threads inited");

        ext.start();
        run.start();
        log.info("All threads started");

        Thread.sleep(5_000);

        ext.interrupt();
        run.interrupt();
        log.info("All threads interrupted");
    }

    @Test
    public void multipleInstance() throws Exception {
        final int threadOfTypeNum = 10;

        List<Thread> threads = new ArrayList<>(2 * threadOfTypeNum);
        for (int i = 0; i < threadOfTypeNum; i++) {
            threads.add(new CounterExtendsThread());
            threads.add(new Thread(new CounterImplementsRunnable()));
        }
        log.info("All threads inited");

        threads.forEach(Thread::start);
        log.info("All threads started");

        Thread.sleep(5_000);

        threads.forEach(Thread::interrupt);
        log.info("All threads interrupted");
    }
}