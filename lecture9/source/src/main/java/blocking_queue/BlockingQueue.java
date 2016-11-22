package blocking_queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class BlockingQueue {
  private Deque<Object> queue = new ArrayDeque<>();
  private int limit = 10;

  public BlockingQueue(int limit) {
    this.limit = limit;
  }


  public synchronized void put(Object item) throws InterruptedException {
    while (this.queue.size() == this.limit) {
      wait();
    }
    if (this.queue.size() == 0) {
      notifyAll();
    }
    this.queue.add(item);
  }


  public synchronized Object take() throws InterruptedException {
    while (this.queue.size() == 0) {
      wait();
    }
    if (this.queue.size() == this.limit) {
      notifyAll();
    }

    return this.queue.remove(0);
  }
}