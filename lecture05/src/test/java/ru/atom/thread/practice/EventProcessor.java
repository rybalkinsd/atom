package ru.atom.thread.practice;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer i : eventProducers) {
            i.run();
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long count = 0;
        BlockingQueue queue = EventQueue.getInstance();
        Iterator<Event> iterator = queue.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getEventType() == Event.EventType.GOOD) {
                count++;
            }
        }
        return count;
    }

    public static long countTotalNumberOfBadEvents() {

        long count = 0;
        BlockingQueue queue = EventQueue.getInstance();
        Iterator<Event> iterator = queue.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getEventType() == Event.EventType.BAD) {
                count++;
            }
        }
        return count;
    }
}
