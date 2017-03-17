package ru.atom.thread.practice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    private static final Logger log = LogManager.getLogger(EventProcessor.class);

    public static void produceEvents(List<EventProducer> eventProducers) throws Exception {
        for (int i = 0; i < eventProducers.size(); i++) {
            Thread ext = new Thread(eventProducers.get(i));
            ext.setName(eventProducers.get(i).getClass().getName());
            log.info(ext.getName()  + " is created");
            ext.start();
            log.info(ext.getName() + " started");
            ext.join();
            log.info(ext.getName() + " joined");
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        long totalNumberOfGoodEvents = 0;
        for (Event ev:EventQueue.getInstance()) {
            if (ev.getEventType() == Event.EventType.GOOD) totalNumberOfGoodEvents++;
        }
        return totalNumberOfGoodEvents;
    }

    public static long countTotalNumberOfBadEvents() {
        long totalNumberOfBadEvents = 0;
        for (Event ev:EventQueue.getInstance()) {
            if (ev.getEventType() == Event.EventType.BAD) totalNumberOfBadEvents++;
        }
        return totalNumberOfBadEvents;
    }
}
