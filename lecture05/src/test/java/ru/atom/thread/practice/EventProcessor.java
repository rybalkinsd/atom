package ru.atom.thread.practice;


import java.util.List;
import java.util.ArrayList;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        ArrayList<Thread> threads = new ArrayList<>();

        for (EventProducer producer: eventProducers) {
            threads.add(new Thread(producer));
        }

        for (Thread thread: threads) {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("InterruptedException" + e);
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long goodEventsCount = 0;

        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD)
                goodEventsCount++;
        }
        return goodEventsCount;
    }

    public static long countTotalNumberOfBadEvents() {
        long badEventsCount = 0;

        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD)
                badEventsCount++;
        }
        return badEventsCount;
    }
}
