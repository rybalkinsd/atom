package ru.atom.lecture10.pingpong;

public class PingPongThread implements Runnable {
    private final String word;
    private static final Object lock = new Object();


    public PingPongThread(String word) {
        this.word = word;
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (true) {
                System.out.println(word);
                lock.notifyAll();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        new Thread(new PingPongThread("ping")).start();
        new Thread(new PingPongThread("pong")).start();
    }
}
