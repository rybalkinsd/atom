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
        for (EventProducer eventProducer:eventProducers) {
            Thread thread = new Thread(eventProducer);
            log.info(thread.getName() + " created");
            threads.add(thread);
            log.info(thread.getName() + " start");
            thread.start();
            try {
                log.info(thread.getName() + " join");
                thread.join();
            } catch (InterruptedException e) {
                log.info(thread.getName() + " interrupt");
                return;
            }
        }

    }

    public static long countTotalNumberOfGoodEvents() {
        return countTotalNumberOfTypeEvents(Event.EventType.GOOD);
    }

    public static long countTotalNumberOfBadEvents() {
        return countTotalNumberOfTypeEvents(Event.EventType.BAD);
    }

    private static long countTotalNumberOfTypeEvents(Event.EventType type) {
        long count = 0;
        for (Event event: EventQueue.getInstance()) {
            if (event.getEventType() == type) {
                count++;
            }
        }
        return count;
    }
}
