package ru.atom.thread.practice;


import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    private static long totalNumberofGoodEvents = 0L;
    private static long totalNumberofBadEvents = 0L;



    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer i : eventProducers) {
            Thread thread = new Thread(i);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        for (Event i : EventQueue.getInstance()) {
            if (i.getEventType() == Event.EventType.GOOD) {
                totalNumberofGoodEvents ++;
            }
        }
        return totalNumberofGoodEvents;
    }

    public static long countTotalNumberOfBadEvents() {
        for (Event i : EventQueue.getInstance()) {
            if (i.getEventType() == Event.EventType.BAD) {
                totalNumberofBadEvents ++;
            }
        }
        return totalNumberofBadEvents;
    }
}
