package ru.atom.thread.practice;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author apomosov
 * @since 15.03.17
 */
@Ignore
public class EventProcessorTest {
    @Test
    public void process() {
        Assert.assertEquals(0, EventProcessor.countTotalNumberOfGoodEvents());
        Assert.assertEquals(0, EventProcessor.countTotalNumberOfBadEvents());

        EventProcessor.produceEvents(Arrays.asList(
                new GoodEventProducer(100_000),
                new GoodEventProducer(50_000),
                new BadEventProducer(100_000),
                new BadEventProducer(50_000)));

        Assert.assertEquals(150_000, EventProcessor.countTotalNumberOfGoodEvents());
        Assert.assertEquals(150_000, EventProcessor.countTotalNumberOfBadEvents());
    }
}
