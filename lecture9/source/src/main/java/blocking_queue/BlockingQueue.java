package blocking_queue;

public interface BlockingQueue<E> {
  void put(E elem) throws InterruptedException;

  E poll() throws InterruptedException;
}