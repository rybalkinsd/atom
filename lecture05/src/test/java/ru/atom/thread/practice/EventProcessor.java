package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {

        List<Thread> threadList = new ArrayList<>();

        for (EventProducer producer : eventProducers) {
            threadList.add(new Thread(producer));
        }
        threadList.forEach(Thread::start);
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long count = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) {
                count++;
            }
        }
        return count;
    }


    public static long countTotalNumberOfBadEvents() {
        long count = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) {
                count++;
            }
        }
        return count;
    }
}
