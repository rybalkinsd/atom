import org.junit.Assert;
import org.junit.Test;
import ru.atom.lecture10.queue.BlockingQueue;
import ru.atom.lecture10.queue.BlockingQueueImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BBPax on 26.04.17.
 */
public class BlockedQueueTest {
    private BlockingQueue<String> queue = new BlockingQueueImpl<String>();

    @Test
    public void sizeTest() throws InterruptedException {
        queue.put("first");
        queue.put("second");
        queue.put("third");
        Assert.assertEquals(3,queue.size());
    }

    @Test
    public void queueTest() throws InterruptedException {
        List<String> result = new ArrayList<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.put("String" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    result.add(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        Assert.assertEquals(10,result.size());
    }
}
