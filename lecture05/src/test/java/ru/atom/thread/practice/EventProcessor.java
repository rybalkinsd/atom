package ru.atom.thread.practice;



import java.util.List;

import static ru.atom.thread.practice.Event.EventType.BAD;
import static ru.atom.thread.practice.Event.EventType.GOOD;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {


    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer eventProducer : eventProducers)
            new Thread(eventProducer).start();

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

    }

    public static long countTotalNumberOfGoodEvents() {
        long counterGood = 0L;
        for (Event e : EventQueue.getInstance()) {
            if (e.getEventType() == GOOD) counterGood++;
        }
        return counterGood;
    }

    public static long countTotalNumberOfBadEvents() {
        long counterBad = 0L;
        for (Event e : EventQueue.getInstance()) {
            if (e.getEventType() == BAD) counterBad++;
        }
        return counterBad;
    }
}
