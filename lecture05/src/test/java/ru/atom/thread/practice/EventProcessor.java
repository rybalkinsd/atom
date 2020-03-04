package ru.atom.thread.practice;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    public static void produceEvents(List<EventProducer> eventProducers) {
        for (EventProducer producer : eventProducers) {
            int index = producer.getClass().getName().lastIndexOf('.') + 1;
            if (producer.getClass().getName().substring(index).equals("GoodEventProducer")) {
                GoodEventProducer currProducer = (GoodEventProducer) producer;
                currProducer.run();
            } else if (producer.getClass().getName().substring(index).equals("BadEventProducer")) {
                BadEventProducer currProducer = (BadEventProducer) producer;
                currProducer.run();
            } else {
                throw new Error("AnimeError");
            }
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long result = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.GOOD) {
                result += 1;
            }
        }
        return result;
    }

    public static long countTotalNumberOfBadEvents() {
        long result = 0;
        for (Event event : EventQueue.getInstance()) {
            if (event.getEventType() == Event.EventType.BAD) {
                result += 1;
            }
        }
        return result;
    }
}
