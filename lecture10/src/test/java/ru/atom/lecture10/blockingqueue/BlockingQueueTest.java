package ru.atom.lecture10.blockingqueue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.lecture10.queue.BlockingQueueOwn;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kinetik on 26.04.17.
 */
public class BlockingQueueTest {

    public ArrayList<Object> firstSet;
    public ArrayList<Object> secondSet;
    public ArrayList<Object> thirdSet;

    @Before
    public void initializer() {
        firstSet = new ArrayList<>();
        secondSet = new ArrayList<>();
        thirdSet = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            firstSet.add(new Object());
        }
        for (int j = 0; j < 10; j++) {
            secondSet.add(new Object());
        }
        for (int k = 0; k < 20; k++) {
            thirdSet.add(new Object());
        }
    }


    @Test
    public void testPut() {
        BlockingQueueOwn<Object> objQueue = new BlockingQueueOwn<>(10);
        Assert.assertEquals(10, objQueue.remainingCapacity());
        Putter putter = new Putter(objQueue, firstSet);
        putter.start();

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(7, objQueue.size());
        Assert.assertEquals(3, objQueue.remainingCapacity());
    }

    @Test
    public void testPutNull() {
        BlockingQueueOwn<Object> objQueue = new BlockingQueueOwn<>(10);
        Assert.assertEquals(10, objQueue.remainingCapacity());
        try {
            objQueue.put(null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(0, objQueue.size());
        Assert.assertEquals(10, objQueue.remainingCapacity());
    }

    @Test
    public void testPutMultipleRight() {
        BlockingQueueOwn<Object> objQueue = new BlockingQueueOwn<>(17);
        Assert.assertEquals(17, objQueue.remainingCapacity());
        Putter putterOne = new Putter(objQueue, firstSet);
        Putter putterTwo = new Putter(objQueue, secondSet);
        putterOne.start();
        putterTwo.start();
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(17, objQueue.size());
        Assert.assertEquals(0, objQueue.remainingCapacity());

    }

    @Test
    public void testPutMultipleMore() {
        BlockingQueueOwn<Object> objQueue = new BlockingQueueOwn<>(10);
        Assert.assertEquals(10, objQueue.remainingCapacity());
        Putter putterOne = new Putter(objQueue, firstSet);
        Putter putterTwo = new Putter(objQueue, thirdSet);
        putterOne.start();
        putterTwo.start();
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(10, objQueue.size());
        Assert.assertEquals(0, objQueue.remainingCapacity());
    }

    @Test
    public void testPutMultipleAll() {
        BlockingQueueOwn<Object> objQueue = new BlockingQueueOwn<>(10);
        Assert.assertEquals(10, objQueue.remainingCapacity());
        Putter putterOne = new Putter(objQueue, firstSet);
        Putter putterTwo = new Putter(objQueue, thirdSet);
        Putter putterThree = new Putter(objQueue, secondSet);
        putterOne.start();
        putterTwo.start();
        putterThree.start();
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(10, objQueue.size());
        Assert.assertEquals(0, objQueue.remainingCapacity());
    }

    @Test
    public void testAll() {
        BlockingQueueOwn<Object> objQueue = new BlockingQueueOwn<>(10);
        Assert.assertEquals(10, objQueue.remainingCapacity());
        Putter putterOne = new Putter(objQueue, firstSet);
        Putter putterTwo = new Putter(objQueue, thirdSet);
        Putter putterThree = new Putter(objQueue, secondSet);
        putterOne.start();
        putterTwo.start();
        putterThree.start();
        Getter getterOne = new Getter(objQueue);
        Getter getterTwo = new Getter(objQueue);
        getterOne.start();
        getterTwo.start();
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(0, objQueue.size());
        Assert.assertEquals(10, objQueue.remainingCapacity());
        Assert.assertEquals(firstSet.size() + thirdSet.size() + secondSet.size(),
                getterOne.getSize() + getterTwo.getSize());
    }
}
