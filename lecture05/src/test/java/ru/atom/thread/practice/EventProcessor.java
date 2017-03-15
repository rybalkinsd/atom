package ru.atom.thread.practice;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer ev : eventProducers)
            new Thread(ev).start();

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long res = 0;
        for (Event e : EventQueue.getInstance()) {
            if (e.getEventType() == Event.EventType.GOOD)
                res++;
        }
        return res;
    }

    public static long countTotalNumberOfBadEvents() {
        long res = 0;
        for (Event e : EventQueue.getInstance())
            if (e.getEventType() == Event.EventType.BAD)
                res++;
        return res;
    }
}
