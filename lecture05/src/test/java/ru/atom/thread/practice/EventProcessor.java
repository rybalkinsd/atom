package ru.atom.thread.practice;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

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
        int eventCounter = 0;
        LinkedBlockingQueue<Event> queue = (LinkedBlockingQueue<Event>) EventQueue.getInstance();
        for (Event event : queue) {
            if (event.getEventType() == Event.EventType.GOOD)
                eventCounter++;
        }
        return eventCounter;
    }

    public static long countTotalNumberOfBadEvents() {
        int eventCounter = 0;
        LinkedBlockingQueue<Event> queue = (LinkedBlockingQueue<Event>) EventQueue.getInstance();
        for (Event event : queue) {
            if (event.getEventType() == Event.EventType.BAD)
                eventCounter++;
        }
        return eventCounter;
    }
}
