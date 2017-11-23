package ru.atom.thread.practice;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {

    private static BlockingQueue<Event> queue = EventQueue.getInstance();

    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer eventProducer : eventProducers)
            eventProducer.run();
    }

    public static long countTotalNumberOfGoodEvents() {
        long countGood = 0L;
        for (Event event : queue) {
            if (event.getEventType() == Event.EventType.GOOD)
                countGood++;
        }
        return countGood;
    }

    public static long countTotalNumberOfBadEvents() {
        long countBad = 0L;
        for (Event event : queue) {
            if (event.getEventType() == Event.EventType.BAD)
                countBad++;
        }
        return countBad;
    }
}
