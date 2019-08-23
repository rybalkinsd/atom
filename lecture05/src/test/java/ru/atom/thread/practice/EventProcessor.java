package ru.atom.thread.practice;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        for(EventProducer producer : eventProducers) {
            producer.run();
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long n = 0;
        for(Event event : EventQueue.getInstance()) {
            if(event.getEventType().equals(Event.EventType.GOOD)){
                n++;
            }
        }
        return n;
    }

    public static long countTotalNumberOfBadEvents() {
        long n = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType().equals(Event.EventType.BAD)) {
                n++;
            }
        }
        return n;
    }
}
