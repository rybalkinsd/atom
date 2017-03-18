package ru.atom.thread.practice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    private static final Logger log = LogManager.getLogger(EventProcessor.class);

    public static void produceEvents(List<EventProducer> eventProducers) {
        List<Thread> threads = new ArrayList<>();

        for (EventProducer event : eventProducers) {
            threads.add(new Thread(event));
            log.info("Thread add");
        }

        threads.forEach(Thread::start);

        for (Thread temp : threads) {
            if (!temp.isInterrupted()) {
                try {
                    temp.join();
                } catch (InterruptedException e) {
                    log.warn("Interrupted catch");
                }
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long sum = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) {
                sum++;
            }
        }
        return sum;
    }

    public static long countTotalNumberOfBadEvents() {
        long sum = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) {
                sum++;
            }
        }
        return sum;
    }
}
