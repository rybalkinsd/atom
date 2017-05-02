package ru.atom.lecture11.dataraces;

/**
 * @author v.chibrikov
 */
public class   DataRaceExample {
    public static void main(String[] args) throws InterruptedException {
        Stopper stopper = new Stopper();
        RacingThread thread1 = new RacingThread(stopper);
        RacingThread thread2 = new RacingThread(stopper);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Expected: " + Stopper.HUNDRED_MILLION);
        System.out.println("Result: " + thread1.getValue());
    }
}
