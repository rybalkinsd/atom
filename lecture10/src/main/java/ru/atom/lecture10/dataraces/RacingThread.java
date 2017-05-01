package ru.atom.lecture10.dataraces;

public final class RacingThread extends Thread {
    private static Object lock = new Object();
    private final Stopper checker;
    private static int value;

    public RacingThread(Stopper checker) {
        this.checker = checker;
    }

    public void run() {
        while (!checker.stop()) {
            increment();
        }
    }

    public void increment() {
        synchronized (lock) {
            value++;
        }
        //increment is not atomic - it consists of 3 separate processor operations
        //use AtomicInteger for atomicity
    }

    /*public synchronized void increment() {
        value++; //this way every thread gets its own lock ( synchronized (this) )
        //unlike the example above, where the lock is a static object
        //another way is using checker as a lock, as one checker is used in both threads (in this case)
    }*/

    public int getValue() {
        return value;
    }
}
