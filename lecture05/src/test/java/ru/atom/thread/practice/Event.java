package ru.atom.thread.practice;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class Event {
    private EventType eventType;

    public Event(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public boolean isGood(){
        return eventType == EventType.GOOD;
    }

    public boolean isBad(){
        return eventType == EventType.BAD;
    }

    enum EventType {
        GOOD, BAD
    }
}
