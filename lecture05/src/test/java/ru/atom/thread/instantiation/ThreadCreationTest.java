package ru.atom.thread.instantiation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 3/14/17.
 */
public class ThreadCreationTest {
    private static final Logger log = LogManager.getLogger(ThreadCreationTest.class);

    @Test
    public void singleInstance() throws Exception {
        Thread ext = new NotifierExtendsThread();
        ext.setName(NotifierExtendsThread.class.getSimpleName());

        Thread run = new Thread(new NotifierImplementsRunnable());
        run.setName(NotifierImplementsRunnable.class.getSimpleName());

        log.info("All threads created");

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
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Thread ext = new NotifierExtendsThread();
            ext.setName(NotifierExtendsThread.class.getSimpleName() + " " + i);
            threads.add(ext);

            Thread run = new Thread(new NotifierImplementsRunnable());
            run.setName(NotifierImplementsRunnable.class.getSimpleName() + " " + i);
            threads.add(run);
        }
        log.info("All threads created");

        threads.forEach(Thread::start);
        log.info("All threads started");

        Thread.sleep(5_000);

        threads.forEach(Thread::interrupt);
        log.info("All threads interrupted");
    }
}