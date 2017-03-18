package ru.atom.thread.practice;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import ru.atom.thread.practice.Event.EventType;

/**
 * @author apomosov
 * @since 15.03.17
 */
public class EventProcessor {
    
	static private long countGood = 0;
	static private long countBad = 0;
	
	public static void produceEvents(List<EventProducer> eventProducers) {
        for(int i = 0; i < eventProducers.size(); ++i) {
        	EventProducer g = eventProducers.get(i);
        	g.run();
        	
        	BlockingQueue<Event> bq = EventQueue.getInstance();
        	for(;;) {
        		Event ev = bq.poll();
        		if(ev == null) break;
        		if(ev.getEventType() == EventType.GOOD) {
        			countGood++;
        		} else if(ev.getEventType() == EventType.BAD) {
        			countBad++;
        		}
        	}
        }
    }

    public static long countTotalNumberOfGoodEvents() {
        return countGood;
    }

    public static long countTotalNumberOfBadEvents() {
        return countBad;
    }
}
