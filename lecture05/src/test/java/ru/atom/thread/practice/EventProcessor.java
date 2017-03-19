package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    static long good = 0;
    static long bad = 0;

    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer event : eventProducers) {
            if (event.getClass().toString().equals("class ru.atom.thread.practice.BadEventProducer")) {
                event.run();
                bad += EventQueue.getInstance().size();
                EventQueue.getInstance().clear();
            } else {
                event.run();
                good += EventQueue.getInstance().size();
                EventQueue.getInstance().clear();

            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return good;
    }

    public static long countTotalNumberOfBadEvents() {
        return bad;
    }
}
