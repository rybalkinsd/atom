package ru.atom.thread.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class GoodEventProducer implements EventProducer {
    private final int numberOfEvents;
    private static final Logger log = LoggerFactory.getLogger(GoodEventProducer.class);

    public GoodEventProducer(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
        log.info("Number of GOOD events {}", numberOfEvents);
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfEvents; i++) {
            EventQueue.getInstance().offer(new Event(Event.EventType.GOOD));
            log.info("A new GOOD EVENT {} has been added.", i);
        }
    }
}