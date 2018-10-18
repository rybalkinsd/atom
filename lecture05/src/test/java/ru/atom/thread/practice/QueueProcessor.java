package ru.atom.thread.practice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueProcessor extends Thread {

    private long countOfGoodEvents;
    private long countOfBadEvents;
    private static final Logger log = LogManager.getLogger(QueueProcessor.class);

    @Override
    public void run() {
        BlockingQueue<Event> bq = EventQueue.getInstance();

        Boolean readyToExit = false;

        while (!(readyToExit && bq.isEmpty())) {

            try {
                Event event = bq.take();

                if (event.getEventType() == Event.EventType.GOOD) {
                    countOfGoodEvents++;
                } else if (event.getEventType() == Event.EventType.BAD) {
                    countOfBadEvents++;
                }

                log.info("countOfGoodEvents {}; countOfBadEvents {};",countOfGoodEvents, countOfBadEvents);

            } catch (InterruptedException e) {
                log.error(e);
                readyToExit = true;
            }

        }
        log.info("i'am finish.");
    }

    public long getCountOfGoodEvents() {
        return countOfGoodEvents;
    }


    public long getCountOfBadEvents() {
        return countOfBadEvents;
    }

}
