package ru.atom.thread.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {

    public static void produceEvents(List<EventProducer> eventProducers) {
        List<Thread> eventList = new ArrayList<>();
        for (EventProducer producer : eventProducers) {
            Thread thread = new Thread(producer);
            thread.start();
            eventList.add(thread);
        }
        for (Thread thread : eventList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static long countTotalNumberOfGoodEvents() {
        long count = 0L;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) {
                count++;
            }
        }
        return count;
    }

    public static long countTotalNumberOfBadEvents() {
        long count = 0L;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) {
                count++;
            }
        }
        return count;
    }
}

