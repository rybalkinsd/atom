package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractList;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        ArrayList<Thread> threads = new ArrayList<>(eventProducers.size());
        for (EventProducer event : eventProducers) {
            Thread thread = new Thread(event);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Is interrupted");
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long result = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) result++;
        }
        return result;
    }

    public static long countTotalNumberOfBadEvents() {
        long result = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) result++;
        }
        return result;
    }

}
