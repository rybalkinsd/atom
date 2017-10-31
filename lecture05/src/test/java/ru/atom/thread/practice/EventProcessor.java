package ru.atom.thread.practice;

import java.util.List;

public class EventProcessor {

    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer e : eventProducers) {
            e.run();
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long count = 0L;
        for (Event currentevent : EventQueue.getInstance()) {
            if (currentevent.getEventType() == Event.EventType.GOOD) {
                count++;
            }
        }
        return count;
    }

    public static long countTotalNumberOfBadEvents() {
        long count = 0L;
        for (Event currentevent : EventQueue.getInstance()) {
            if (currentevent.getEventType() == Event.EventType.BAD) {
                count++;
            }
        }
        return count;
    }
}