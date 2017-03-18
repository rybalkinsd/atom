package ru.atom.thread.practice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    private static final Logger log = LogManager.getLogger(EventProcessor.class);

    public static void produceEvents(List<EventProducer> eventProducers) {
        int number = 0;
        for (EventProducer temp : eventProducers) {
            number++;
            Thread thready = new Thread(temp);
            thready.start();
            log.info("{}-th thread was started", number);
            try {
                thready.join();
            } catch (InterruptedException e) {
                log.warn("{}-th thread was interrupted", number);
            }
            log.info("{}-th thread was ended", number);
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return EventQueue.getInstance().stream()
                .filter(s -> s.getEventType() == Event.EventType.GOOD)
                .count();
    }

    public static long countTotalNumberOfBadEvents() {
        return EventQueue.getInstance().stream()
                .filter(s -> s.getEventType() == Event.EventType.BAD)
                .count();
    }
}
