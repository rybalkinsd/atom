package ru.atom.thread.practice;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer eventProducer : eventProducers) {
            eventProducer.run();
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long count = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) {
                count++;
            }
        }
        return count;
    }

    public static long countTotalNumberOfBadEvents() {
        long count = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) {
                count++;
            }
        }
        return count;
    }
}
