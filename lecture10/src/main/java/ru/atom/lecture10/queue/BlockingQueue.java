package ru.atom.lecture10.queue;

/**
 * A Queue that supports operations to wait for to become non-empty when retrieving an element,
 * and wait for space to become available in the queue when storing an element.
 */
public interface BlockingQueue<T> {

    /**
     * Inserts the specified element into this queue, waiting if necessary for space to become available.
     *
     * @param elem - the element to add
     * @throws InterruptedException if interrupted while waiting
     */
    void put(T elem) throws InterruptedException;


    /**
     * Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
     *
     * @return the head of this queue if interrupted while waiting
     * @throws InterruptedException if interrupted while waiting
     */
    T take() throws InterruptedException;

    /**
     * Returns the number of additional elements that this queue can ideally (in the absence of memory
     * or resource constraints) accept without blocking.
     * Note that you cannot always tell if an attempt to insert an element will succeed by inspecting remainingCapacity
     * without synchronization
     * because it may be the case that another thread is about to insert or remove an element.
     */
    int remainingCapacity();

    /**
     * Returns the number of elements in this collection.
     *
     * @return the number of elements in this collection
     */
    int size();
}
