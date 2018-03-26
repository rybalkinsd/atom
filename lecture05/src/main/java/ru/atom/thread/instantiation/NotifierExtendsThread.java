package ru.atom.thread.instantiation;

import org.slf4j.LoggerFactory;
import ru.atom.mm.service.ConnectionProducer;

/**
 * Created by sergey on 3/14/17.
 */
public class NotifierExtendsThread extends Thread {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(NotifierExtendsThread.class);


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
