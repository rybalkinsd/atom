package ru.atom.lecture10.raceconditions;

public class RandomRunExample extends Thread {
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; ++i) {
            Thread thread = new RandomRunExample();
            thread.start();
        }
    }
}
