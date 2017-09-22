package ru.atom.thread.instantiation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by sergey on 3/14/17.
 */
public class NotifierExtendsThread extends Thread {
    private static final Logger log = LogManager.getLogger(NotifierExtendsThread.class);

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            log.info(NotifierExtendsThread.class.getSimpleName() + " is running!");
            try {
                sleep(500);
            } catch (InterruptedException e) {
                log.warn("Was interrupted");
                return;
            }
        }
    }
}
