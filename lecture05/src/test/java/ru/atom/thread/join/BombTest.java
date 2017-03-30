package ru.atom.thread.join;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by sergey on 3/14/17.
 */
@Ignore
public class BombTest {
    private static final Logger log = LogManager.getLogger(BombTest.class);

    @Test
    public void boom() throws Exception {
        Thread.currentThread().setName("Bomberman");
        Thread bomb = new Thread(new Bomb(10));
        log.info("bomb thread created");

        bomb.start();
        log.info("Bomb planted");

        bomb.join();
        log.info("Bomb exploded before this message");
    }

    @Test
    public void lifeIsTooShortToWait() throws Exception {
        Thread.currentThread().setName("Bomberman");
        Thread bomb = new Thread(new Bomb(100));
        log.info("bomb thread created");

        bomb.start();
        log.info("Bomb planted");

        bomb.join(5_000);
        log.info("Already spent 5 seconds - interrupting");

        bomb.interrupt();

        log.info("Bomb was interrupted");
    }
}