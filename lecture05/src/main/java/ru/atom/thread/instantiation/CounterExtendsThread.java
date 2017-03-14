package ru.atom.thread.instantiation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by sergey on 3/14/17.
 */
public class CounterExtendsThread extends Thread {
    private static final Logger log = LogManager.getLogger(CounterExtendsThread.class);

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            log.info(CounterExtendsThread.class.getSimpleName() + " is on fire!");
            try {
                sleep(500);
            } catch (InterruptedException e) {
                log.warn("Was interrupted");
                return;
            }
        }
    }
}
