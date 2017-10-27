package ru.atom.thread.practice;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {

    private static long countGoodEvent = 0;
    private static long countBadEvent = 0;

    public static void produceEvents(List<EventProducer> eventProducers) {
        for(EventProducer eventProducer : eventProducers) {
            Thread thread = new Thread(eventProducer);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Event event = null;
        while (true) {
            event = EventQueue.getInstance().poll();
            if(event == null) {
                return;
            }
            if(event.getEventType().equals(Event.EventType.BAD)) {
                countBadEvent++;
            }
            if(event.getEventType().equals(Event.EventType.GOOD)) {
                countGoodEvent++;
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return countGoodEvent;
    }

    public static long countTotalNumberOfBadEvents() {
        return countBadEvent;
    }
}
