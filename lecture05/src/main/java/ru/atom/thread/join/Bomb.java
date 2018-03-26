package ru.atom.thread.join;

import org.slf4j.LoggerFactory;
import ru.atom.thread.instantiation.NotifierExtendsThread;

/**
 * Created by sergey on 3/14/17.
 */
public class Bomb implements Runnable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Bomb.class);


    private int countdownFrom;

    public Bomb(int countdownFrom) {
        this.countdownFrom = countdownFrom;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Bomb");

        while (!Thread.currentThread().isInterrupted()
                && countdownFrom > 0) {
            log.info(String.valueOf(countdownFrom));
            countdownFrom--;

            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
                log.info("I'm interrupted");
                return;
            }
        }

        if (countdownFrom == 0) {
            log.info("Booooom");
        }
    }
}
