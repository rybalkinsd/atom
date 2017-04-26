import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.atom.lecture10.queue.BlockingQueueImpl;
import ru.atom.lecture10.queue.Message;

/**
 * Created by dmitriy on 26.04.17.
 */
public class BlockingQueueTest {
    BlockingQueueImpl<Message> blockingQueueImpl;

    @Before
    public void setUp() throws Exception {
        blockingQueueImpl = new BlockingQueueImpl<>(4);
    }

    @Test
    public void sizeTest() throws Exception {
        Assert.assertTrue(blockingQueueImpl.size() == 0);
        for (int i = 0; i < 3; i++) {
            blockingQueueImpl.put(new Message());
        }
        Assert.assertTrue(blockingQueueImpl.size() == 3);
    }

    @Test
    public void remainingCapacityTest() throws Exception {
        Assert.assertTrue(blockingQueueImpl.size() == 0);
        for (int i = 0; i < 4; i++) {
            blockingQueueImpl.put(new Message());
            Assert.assertTrue(blockingQueueImpl.remainingCapacity()
                    == 4 - (i + 1));
        }
        Assert.assertTrue(blockingQueueImpl.remainingCapacity() == 0);
    }

    @Test
    public void putTest() throws Exception {
        Thread[] myThread = new Thread[2];
        for (int i = 0; i < 2; i++) {
            myThread[i] = new Thread(() -> {
                try {
                    blockingQueueImpl.put(new Message());
                    blockingQueueImpl.put(new Message());
                    blockingQueueImpl.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        myThread[0].start();
        myThread[1].start();
        myThread[0].join();
        myThread[1].join();
        Assert.assertTrue(blockingQueueImpl.size() == 2);
    }

    @Test
    public void takeTest() throws Exception {
        int sizeBeforeTake = blockingQueueImpl.size();
        Thread[] consumer = new Thread[2];

        for (int i = 0; i < sizeBeforeTake; i++) {
            blockingQueueImpl.take();
        }
        Assert.assertTrue(blockingQueueImpl.size() == 0);
        for (int i = 0; i < 4; i++) {
            blockingQueueImpl.put(new Message());
        }
        for (int i = 0; i < 2; i++) {
            consumer[i] = new Thread(() -> {
                try {
                    blockingQueueImpl.take();
                    blockingQueueImpl.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        consumer[0].start();
        consumer[1].start();
        consumer[0].join();
        consumer[1].join();
        Assert.assertTrue(blockingQueueImpl.size() == 0);
    }
}
