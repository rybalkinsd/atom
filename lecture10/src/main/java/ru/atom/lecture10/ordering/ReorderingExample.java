package ru.atom.lecture10.ordering;

public class ReorderingExample {
    boolean first = false;
    boolean second = false;

    void setValues() {
        first = true;
        second = true;
    }

    void checkValues() {
        while (!second) ;
        assert first;
    }

    /**
     * It can possibly fail
     */
    public static void main(String[] args) throws InterruptedException {
        ReorderingExample obj = new ReorderingExample();
        Thread thread1 = new Thread(() -> obj.setValues());
        Thread thread2 = new Thread(() -> obj.checkValues());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}