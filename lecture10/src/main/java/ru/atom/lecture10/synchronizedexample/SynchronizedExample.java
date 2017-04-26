package ru.atom.lecture10.synchronizedexample;

/**
 * @author Alpi
 * @since 22.11.16
 */
public class SynchronizedExample {
    private final Object lock = new Object();//Any object here

    public synchronized void criticalMethod() {
        System.out.println("This method is protected by internal monitor of 'this'" +
                "and will be executed only one at a time on 'this'");
    }

    public void criticalBlockInside() {
        System.out.println("Some work here");
        synchronized (lock) {
            System.out.println("This block will by Object lock and executed only one at a time on given lock object");
        }
        System.out.println("Some work here too");
    }
}
