package ru.atom.thread.join;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by sergey on 3/14/17.
 */
public class Bomb implements Runnable {
    private static final Logger log = LogManager.getLogger(Bomb.class);

    private int countdownFrom;

    public Bomb(int countdownFrom) {
        this.countdownFrom = countdownFrom;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Bomb");

        while (!Thread.currentThread().isInterrupted()
                && countdownFrom > 0) {
            log.info(countdownFrom);
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
