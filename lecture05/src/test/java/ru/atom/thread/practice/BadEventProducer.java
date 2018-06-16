package ru.atom.thread.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class BadEventProducer implements EventProducer {
    private final int numberOfEvents;
    private static final Logger log = LoggerFactory.getLogger(BadEventProducer.class);

    public BadEventProducer(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
        log.info("Number of BAD events {}", numberOfEvents);
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfEvents; i++) {
            EventQueue.getInstance().offer(new Event(Event.EventType.BAD));
            log.info("A new BAD EVENT {} has been added.", i);
        }
    }
}