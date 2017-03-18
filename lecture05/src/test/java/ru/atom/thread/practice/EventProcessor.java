package ru.atom.thread.practice;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    private static final Logger logger = LogManager.getLogger(EventProcessor.class);

    public static void produceEvents(List<EventProducer> eventProducers) {
        ArrayList<Thread> threads = new ArrayList<>();
        int number = 0;
        for (EventProducer eventProducer : eventProducers) {
            Thread thread = new Thread(eventProducer, "thread " + number++);
            threads.add(thread);

        }
        threads.forEach(Thread::start);
        logger.info("all threads start!");
        for (Thread thread : threads) {
            try {
                thread.join();
                logger.info(thread.getName() + " join!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long result = countTotalNumberEvents(Event.EventType.GOOD);
        logger.info("total number of good events = " + result);

        return result;
    }

    public static long countTotalNumberOfBadEvents() {
        long result = countTotalNumberEvents(Event.EventType.BAD);
        logger.info("total number of bad events = " + result);

        return result;
    }

    public static long countTotalNumberEvents(Event.EventType type) {
        long result = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType().equals(type)) {
                result++;
            }
        }
        return result;
    }
}
