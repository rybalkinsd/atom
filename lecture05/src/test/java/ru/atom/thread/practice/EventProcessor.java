package ru.atom.thread.practice;

import java.util.ArrayList;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        List<Thread> threadArrayList = new ArrayList<>();
        for (EventProducer producer : eventProducers) {
            threadArrayList.add(new Thread(producer));
        }
        threadArrayList.forEach(Thread::start);
        for (Thread thread : threadArrayList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return countEventsType(Event.EventType.GOOD);
    }


    public static long countTotalNumberOfBadEvents() {
        return countEventsType(Event.EventType.BAD);
    }

    public static long countEventsType(Event.EventType type) {
        long count = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == type) {
                count++;
            }
        }
        return count;
    }
}
