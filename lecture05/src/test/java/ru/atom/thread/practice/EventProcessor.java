package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;
import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer ev : eventProducers) {
            ev.run();
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        Iterator it = EventQueue.getInstance().iterator();
        Event tmp = null;
        long count = 0;
        while (it.hasNext()) {
            tmp = (Event)it.next();
            if (tmp.getEventType() == Event.EventType.GOOD) {
                ++count;
            }
        }
        return count;
    }

    public static long countTotalNumberOfBadEvents() {
        Iterator it = EventQueue.getInstance().iterator();
        Event tmp = null;
        long count = 0;
        while (it.hasNext()) {
            tmp = (Event)it.next();
            if (tmp.getEventType() == Event.EventType.BAD) {
                ++count;
            }
        }
        return count;
    }
}
