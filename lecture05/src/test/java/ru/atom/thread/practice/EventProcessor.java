package ru.atom.thread.practice;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    private static int good;
    private static int bad;
    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer e: eventProducers)
        {
            if (e instanceof BadEventProducer) bad += e.getNumber();
            if (e instanceof GoodEventProducer) good += e.getNumber();
            new Thread(e).start();
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return good;
    }

    public static long countTotalNumberOfBadEvents() {
        return bad;
    }
}
