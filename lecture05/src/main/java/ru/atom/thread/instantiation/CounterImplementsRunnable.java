package ru.atom.thread.instantiation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by sergey on 3/14/17.
 */
public class CounterImplementsRunnable implements Runnable {
    private static final Logger log = LogManager.getLogger(CounterImplementsRunnable.class);

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            log.info(CounterImplementsRunnable.class.getSimpleName() + " is on fire!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.warn("Was interrupted");
                return;
            }
        }
    }

}
