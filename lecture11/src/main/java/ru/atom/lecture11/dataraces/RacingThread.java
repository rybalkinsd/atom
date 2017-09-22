package ru.atom.lecture11.dataraces;

public final class RacingThread extends Thread {
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
        value++;
    }

    public int getValue() {
        return value;
    }
}
