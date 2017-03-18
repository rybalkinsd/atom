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

        for (int i = 0; i < eventProducers.size(); i++) {
            Thread newEventProducer = new Thread(eventProducers.get(i));
            threadList.add(newEventProducer);
            newEventProducer.start();
        }

        for (int j = 0; j < threadList.size(); j++) {
            try {
                threadList.get(j).join();
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

        }

    }


    public static long countTotalNumberOfGoodEvents() {
        long result = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) {
                result++;
            }
        }
        return result;
    }

    public static long countTotalNumberOfBadEvents() {
        long result = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) {
                result++;
            }
        }
        return result;
    }
}
