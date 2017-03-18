package ru.atom.thread.practice;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {

    private static long goodEventsCount = 0;
    private static long badEventsCount = 0;
    private static long totalCount = 0;

    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer producer : eventProducers) {
            Thread thread = new Thread(producer);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countEvents(producer);
        }
    }

    private static void countEvents(EventProducer producer) {
        if (producer instanceof GoodEventProducer) {
            long diff;
            diff = EventQueue.getInstance().size() - totalCount;
            goodEventsCount += diff;
            totalCount = EventQueue.getInstance().size();
        } else if (producer instanceof BadEventProducer) {
            long diff;
            diff = EventQueue.getInstance().size() - totalCount;
            badEventsCount += diff;
            totalCount = EventQueue.getInstance().size();
        } else {
            throw new NotImplementedException();
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return goodEventsCount;
    }

    public static long countTotalNumberOfBadEvents() {
        return badEventsCount;
    }
}
