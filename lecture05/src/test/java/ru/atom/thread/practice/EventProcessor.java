package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {

    public static void produceEvents(List<EventProducer> eventProducers) {
        List<Thread> producerThreads = eventProducers.stream().map(Thread::new).collect(Collectors.toList());
        producerThreads.forEach(Thread::start);

        for (Thread t : producerThreads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static long countTotalNumberOfGoodEvents() {
        long goodEvents = 0;

        for (Event e : EventQueue.getInstance()) {
            if (e.getEventType() == Event.EventType.GOOD) {
                ++goodEvents;
            }
        }

        return goodEvents;
    }

    public static long countTotalNumberOfBadEvents() {
        long badEvents = 0;

        for (Event e : EventQueue.getInstance()) {
            if (e.getEventType() == Event.EventType.BAD) {
                ++badEvents;
            }
        }

        return badEvents;
    }
}
