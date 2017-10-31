package ru.atom.thread.practice;

import jdk.jfr.EventType;

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
        int good = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD)
                good++;
        }
        return good;
    }

    public static long countTotalNumberOfBadEvents() {
        int bad = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD)
                bad++;
        }
        return bad;
    }
}
