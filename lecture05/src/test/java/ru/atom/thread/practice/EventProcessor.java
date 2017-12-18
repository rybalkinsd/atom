package ru.atom.thread.practice;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer eventProducer : eventProducers) {
            Thread eventThread = new Thread(eventProducer);
            eventThread.start();
            try {
                eventThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long totalNumberOfGoodEvents = 0;
        for (Event event : EventQueue.getInstance())
            if (event.getEventType() == Event.EventType.GOOD) {
                totalNumberOfGoodEvents++;
            }
        return totalNumberOfGoodEvents;
    }

    public static long countTotalNumberOfBadEvents() {
        long totalNumberOfBadEvents = 0;
        for (Event event : EventQueue.getInstance())
            if (event.getEventType() == Event.EventType.BAD) {
                totalNumberOfBadEvents++;
            }
        return totalNumberOfBadEvents;
    }
}
