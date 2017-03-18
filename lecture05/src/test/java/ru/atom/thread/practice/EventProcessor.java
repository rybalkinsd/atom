package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    private static BlockingQueue<Event> events = EventQueue.getInstance();

    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer eventProduce : eventProducers) {
            Thread eventThread = new Thread(eventProduce);
            eventThread.start();
            try {
                eventThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return countEventsWithType(Event.EventType.GOOD);
    }

    public static long countTotalNumberOfBadEvents() {
        return countEventsWithType(Event.EventType.BAD);
    }

    private static long countEventsWithType(Event.EventType type) {
        long total = 0L;
        for (Event event : events) {
            if (event.getEventType() == type) {
                total++;
            }
        }
        return total;
    }
}
