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
    }

    public int getValue() {
        return value;
    }
}
