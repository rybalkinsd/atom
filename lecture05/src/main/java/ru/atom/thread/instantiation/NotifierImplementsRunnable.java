package ru.atom.thread.instantiation;

import org.slf4j.LoggerFactory;

/**
 * Created by sergey on 3/14/17.
 */
public class NotifierImplementsRunnable implements Runnable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(NotifierImplementsRunnable.class);


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            log.info(NotifierImplementsRunnable.class.getSimpleName() + " is running!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.warn("Was interrupted");
                return;
            }
        }
    }

}
